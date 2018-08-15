package JavaFiles;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class VariousFunctions {
	
	public static boolean isValidEmailAddress(String email) {
        return email.matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]+");
	}
}
