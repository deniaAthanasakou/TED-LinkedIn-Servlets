package JavaFiles;

import java.io.File;

import java.io.IOException;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import database.entities.Post;
import database.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VariousFunctions {
	
	public static boolean isValidEmailAddress(String email) {
        return email.matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]+");
	}

	public boolean isBlank(String str) {
				
	    if (str == null || str.equals("")) {	    	
	        return true;
	    }
	    for (int i = 0; i < str.length(); i++) {
	        if ((Character.isWhitespace(str.charAt(i)) == false)) {
	        	return false;
	        }
	    }
	    
	    return true;
	}
	
	public static String getDateInterval(Date initDate) {
		Date d2 = new Date();

		String finalDate = null;
		try {
			//in milliseconds
			long diff = d2.getTime() - initDate.getTime();
			
			long minutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
			if(minutes >= 60) {
				long hours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
				if(hours >= 24) {
					long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
					if(days >= 7) {
						long weeks = days / 7;
						if(weeks >= 4) {
							YearMonth m1 = YearMonth.from(initDate.toInstant());
						    YearMonth m2 = YearMonth.from(initDate.toInstant());
						    long months = m1.until(m2, ChronoUnit.MONTHS) + 1;
						    finalDate = String.valueOf(months) + "mo";
						}else {
							finalDate = String.valueOf(weeks) + "w";
						}
					}else {
						finalDate = String.valueOf(days) + "d";
					}
				}else {
					finalDate = String.valueOf(hours) + "h";
				}
			}else {
				finalDate = String.valueOf(minutes) + "m";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalDate;
	}
	
	public static void setFilePathsFromFolders(String folderPath, final Post post) {
		final File folder = new File(folderPath);
		for (final File folderEntry : folder.listFiles()) {
	        if (folderEntry.isDirectory()) {
	            if(folderEntry.getName().equals("images") && post.getHasImages()==1) {
	            	List<String> images = new ArrayList<String>();
	            	for (final File fileEntry : folderEntry.listFiles()) {
	            		images.add(fileEntry.getPath());
	            	}
	            	post.setListImages(images);
	            }
	            if(folderEntry.getName().equals("video") && post.getHasVideos()==1) {
	            	List<String> videos = new ArrayList<String>();
	            	for (final File fileEntry : folderEntry.listFiles()) {
	            		videos.add(fileEntry.getPath());
	            	}
	            	post.setListVideos(videos);
	            }
	            if(folderEntry.getName().equals("audio") && post.getHasAudio()==1) {
	            	List<String> audios = new ArrayList<String>();
	            	List<String> audiosNames = new ArrayList<String>();
	            	for (final File fileEntry : folderEntry.listFiles()) {
	            		audios.add(fileEntry.getPath());
	            		audiosNames.add(fileEntry.getName());
	            	}
	            	post.setListAudios(audios);
	            	post.setListAudiosNames(audiosNames);
	            }
	        }
	    }
	}
	
	public static String generateXML(List<User> users) throws IOException {
	    XmlMapper xmlMapper = new XmlMapper();
	    xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
	    return xmlMapper.writeValueAsString(users);
	}
	
	public static String arrayStrToStr(String str) {
		return str.replace("[","").replace("]", "").trim();
	}
	
	public static List<String> strToArray(String str){
		return Arrays.asList(str.split("\\s*,\\s*"));
	}
	
	public static Map<Integer,Integer> sortMap(Map<Integer,Integer> map) {
		List<Integer> mapKeys = new ArrayList<>(map.keySet());
        List<Integer> mapValues = new ArrayList<>(map.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();

        for (Integer val : mapValues) {
            Iterator<Integer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                Integer comp1 = map.get(key);

                if (comp1.equals(val)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
	}
	
	public static void closeConnection(Connection connection) {
		try {
            if (connection != null) {
            	connection.close();
            }
        } catch (SQLException error) {
        	System.err.println(error.getMessage());
        }
	}
	
	public static void closeStmt(PreparedStatement ps) {
		try {
            if (ps != null) {
            	ps.close();
            }
        } catch (SQLException error) {
        	System.err.println(error.getMessage());
        }
	}
	
	public static void closeResultSet(ResultSet rs) {
		try {
            if (rs != null) {
            	rs.close();
            }
        } catch (SQLException error) {
        	System.err.println(error.getMessage());
        }
	}

	
}










