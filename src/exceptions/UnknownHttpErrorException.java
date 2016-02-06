package exceptions;

import org.springframework.http.HttpStatus;

public class UnknownHttpErrorException extends RuntimeException {

	private HttpStatus statusCode;

	public UnknownHttpErrorException(HttpStatus statusCode)
	{
		this.statusCode = statusCode;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

}
