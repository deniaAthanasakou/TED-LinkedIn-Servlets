package JavaFiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import database.entities.Job;

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
	
	//euclidean distance
	private static KObject getDistanceBetween(Job element1, Job element2) {
		String str1 = element1.getExperience();
		String str2 = element2.getExperience();
		double item;
		if(str1.equalsIgnoreCase(str2)) {
			item = 0;
		}else {
			item = 1;
		}
		item = Math.pow(item, 2);
		double distance = Math.abs(Math.sqrt(item));
		
		return new KObject(element1, (float) distance); 
	}
	
	
	//get knn neighbors, 0 for jobs, 1 for posts
	public static List<Object> getData(List<Job> testSet, List<Job> trainSet, byte type){
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
		for(int i=0;i<finalList.size();i++) {
			System.out.println("finalList: " + i + " item: " + ((Job)finalList.get(i).getObject()).getTitle() + " sum: " + finalList.get(i).getDistance());
		}
		
		//sort finalList
		Collections.sort(finalList, new Comparator<KObject>(){
		     public int compare(KObject o1, KObject o2){
		         if(o1.getDistance() == o2.getDistance())
		             return 0;
		         return o1.getDistance() < o2.getDistance() ? -1 : 1;
		     }
		});
		
		List<Object> objects = new ArrayList<Object>();
		for(int i=0;i<finalList.size();i++) {
			objects.add(finalList.get(i).getObject());
			System.out.println("finalList sorted: " + i + " item: " + ((Job)finalList.get(i).getObject()).getTitle() + " sum: " + finalList.get(i).getDistance());
		}
		return objects;
	}
}
