package database;

public class EmployeeDBException extends Exception {

	private static final long serialVersionUID = 4922795077754323787L;

	public EmployeeDBException(String message) {
		super(message);
	}

	public EmployeeDBException(String message, Throwable cause) {
		super(message, cause);
	}
}
