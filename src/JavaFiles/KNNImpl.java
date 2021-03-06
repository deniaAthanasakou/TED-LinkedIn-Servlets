package JavaFiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import database.dao.comment.CommentDAO;
import database.dao.comment.CommentDAOImpl;
import database.dao.like.LikeDAO;
import database.dao.like.LikeDAOImpl;
import database.entities.Job;
import database.entities.Post;
import database.entities.User;

public class KNNImpl {
	
	private static int determineK(int testSetSize) {
		double root = Math.sqrt( testSetSize );
	    double rawK = root / 2 ;
	    int num = Math.round( ( float )rawK ) ;
	    if ( num%2 != 0 ){
	        return num;
	    }
	    else {
	        return num - 1;
	    }
	}
	
	//euclidean distance, element1: test item, element2: train item
	private static KObject getDistanceBetween(Job element1, Job element2) {
		double distance = 0;
		//get distance for experiences
		String str1 = element1.getExperience();	
		String str2 = element2.getExperience();
		double item;
		if(str1.equalsIgnoreCase(str2)) {
			item = 0;
		}else {
			item = 1;
		}
		item = Math.pow(item, 2);
		distance += item;
		//get distance for job functions
		List<String> element1JobFuncs = VariousFunctions.strToArray(element1.getJobFunction());
		List<String> element2JobFuncs = VariousFunctions.strToArray(element2.getJobFunction());
		for(String function : element2JobFuncs) { //for each function of train item
			if(element1JobFuncs.contains(function)) {	//if it exists in test point
				item = 0;
			}else {
				item = 1;
			}
			item = Math.pow(item, 2);
			distance += item;
		}
		//get distance for skills
		List<String> element1Skills = VariousFunctions.strToArray(element1.getSkills());
		List<String> element2Skills = VariousFunctions.strToArray(element2.getSkills());
		for(String skill2 : element2Skills) {		//for each skill of train item
			item = 1;
			for(String skill1 : element1Skills) {		//for each skill of test item
				if(skill1.equalsIgnoreCase(skill2)) {
					item = 0;
					break;
				}
			}
			item = Math.pow(item, 2);
			distance += item;
		}
		distance = Math.abs(Math.sqrt(distance));
		return new KObject(element1, (float) distance); 
	}
	
	
	//get knn neighbors, 0 for jobs, 1 for posts
	public static List<Job> getDataJobs(List<Job> testSet, List<Job> trainSet){
		if(trainSet.size() > 0) {
			int kNearest = determineK(testSet.size());
			List<KObject> finalList = new ArrayList<KObject>();
			for(Job testItem: testSet) {
				//get all distances for trainSet for specific test item
				List<KObject> distances = new ArrayList<KObject>();
				for (int index = 0; index < trainSet.size(); index++) {
				    KObject distance = getDistanceBetween(testItem, trainSet.get(index));
				    distances.add(distance);
				}
				
				//sort distances
				Collections.sort(distances, new Comparator<KObject>(){
				     public int compare(KObject o1, KObject o2){
				         if(o1.getDistance() == o2.getDistance())
				             return 0;
				         return o1.getDistance() < o2.getDistance() ? -1 : 1;
				     }
				});
							
				//get K shortest distances
				List<KObject> KDistances = distances.subList(0,kNearest);
				
				//get sum of k distances
				float sum = 0;
				for(int i=0;i<KDistances.size();i++) {
					sum += KDistances.get(i).getDistance();
				}			
				finalList.add(new KObject(testItem,sum));
			}
			
			
			//sort finalList
			Collections.sort(finalList, new Comparator<KObject>(){
			     public int compare(KObject o1, KObject o2){
			         if(o1.getDistance() == o2.getDistance())
			             return 0;
			         return o1.getDistance() < o2.getDistance() ? -1 : 1;
			     }
			});
			
			List<Job> jobs = new ArrayList<Job>();
			for(int i=0;i<finalList.size();i++) {
				jobs.add((Job) finalList.get(i).getObject());
			}
			return jobs;
		}
		return testSet;
	}
	
	private static List<KObject> getKNNListUsers(List<Post> posts){
		List<KObject> usersWithLikesComm = new ArrayList<KObject>();
		LikeDAO likeDao = new LikeDAOImpl(true);
		CommentDAO commentDao = new CommentDAOImpl(true);
		for(Post post : posts) {
			User currentUser = post.getUser();
			int sumLikesUser = likeDao.getLikes(currentUser.getId()).size();
			int sumCommentsUser = commentDao.findCommentsOfUser(currentUser.getId()).size();
			double distance = ((sumLikesUser + sumCommentsUser) * 0.7);
			usersWithLikesComm.add(new KObject(currentUser,(float) distance));
		}
		//sort KObjects based on likes+comments DESC
		Collections.sort(usersWithLikesComm, new Comparator<KObject>(){
		     public int compare(KObject o1, KObject o2){
		         if(o1.getDistance() == o2.getDistance())
		             return 0;
		         return o1.getDistance() > o2.getDistance() ? -1 : 1;
		     }
		});
		//get K Objects
		int kNearest = 0;
		if(usersWithLikesComm.size()>0) {
			kNearest = determineK(usersWithLikesComm.size());
		}
		return usersWithLikesComm.subList(0,kNearest);
	}
	
	public static List<Post> getDataPosts(List<Post> posts){
		LikeDAO likeDao = new LikeDAOImpl(true);
		CommentDAO commentDao = new CommentDAOImpl(true);
		List<KObject> listUsers = getKNNListUsers(posts);
		List<KObject> distances = new ArrayList<KObject>();
		if(listUsers.size() > 0) {
			for(Post post: posts) {
				int sum = 0;
				for(KObject object: listUsers) {
					User user = (User) object.getObject();
					//find like or comment of user to specific post
					//check liked
					int liked = likeDao.checkLiked(user.getId(), post.getId());
					int commented = commentDao.checkCommented(user.getId(), post.getId());
					if(liked==1 || commented==1) {
						sum += 0;
					}else {
						sum++;
					}
				}
				//get days apart
				Date d2 = new Date();
				Date initDate = post.getDatePosted();
				long days = TimeUnit.DAYS.convert((d2.getTime() - initDate.getTime()), TimeUnit.MILLISECONDS);
				double dateWeight = (days*0.3);
				dateWeight = Math.pow(dateWeight,2);
				float distance = (float) Math.abs(Math.sqrt(sum + dateWeight));
				distances.add(new KObject(post,distance));
			}
			//sort KObjects
			Collections.sort(distances, new Comparator<KObject>(){
			     public int compare(KObject o1, KObject o2){
			         if(o1.getDistance() == o2.getDistance())
			             return 0;
			         return o1.getDistance() < o2.getDistance() ? -1 : 1;
			     }
			});
			
			List<Post> postsSorted = new ArrayList<Post>();
			for(int i=0;i<distances.size();i++) {
				postsSorted.add((Post) distances.get(i).getObject());
			}
			return postsSorted;
		}
		return posts;
	}
}
