package moodishsecurity.securityfilter.exceptions;

/**
 * Security Filter specific Exception Class
 * 
 * @author Default/Group 10
 * @version 1
 */
public class SecurityFilterClientBlockException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public SecurityFilterClientBlockException() {
		super();
	}
	
	public SecurityFilterClientBlockException(String message) {
        super(message);
    }
		
	public SecurityFilterClientBlockException(String message, Throwable ex) {
		super(message, ex);
	}
	
	public SecurityFilterClientBlockException(Throwable ex) {
		super(ex);
	}
}
