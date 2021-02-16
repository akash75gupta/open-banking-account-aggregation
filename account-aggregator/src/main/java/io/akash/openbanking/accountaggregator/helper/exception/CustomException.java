package io.akash.openbanking.accountaggregator.helper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Purpose:-
 * All custom defined exceptions should extends this class. 
 * The purpose of this class is to segregate all the custom exceptions and distinguish them from
 * other exceptions(compiler exceptions, third party dependency exceptions etc).
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-24
 * 
 **/

public class CustomException extends ResponseStatusException{

	private static final long serialVersionUID = 2446329664881996428L;

	public CustomException(HttpStatus status, String reason, Throwable cause) {
		super(status, reason, cause);
	}
	
	public CustomException(HttpStatus status, String reason) {
		super(status, reason);
	}

}
