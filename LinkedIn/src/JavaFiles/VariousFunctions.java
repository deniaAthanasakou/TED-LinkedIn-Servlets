package JavaFiles;


public class VariousFunctions {
	
	public static boolean isValidEmailAddress(String email) {
        return email.matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]+");
	}



	public boolean isBlank(String str) {
		
		System.out.println("in isBlank");
		
	    if (str == null || str.equals("")) {
	    	System.out.println("first if");
	    	
	        return true;
	    }
	    for (int i = 0; i < str.length(); i++) {
	        if ((Character.isWhitespace(str.charAt(i)) == false)) {
	        	
	        	System.out.println("false");
	            return false;
	        }
	    }
	    
	    System.out.println("end true");
	    return true;
	}

}