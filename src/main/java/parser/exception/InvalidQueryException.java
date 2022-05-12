package parser.exception;

public class InvalidQueryException extends Exception{

	private String errorMsg;

	public InvalidQueryException(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
	
	
}
