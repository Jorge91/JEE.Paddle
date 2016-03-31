package business.api.exceptions;

public class ErrorDeletingStudentException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Campo de Training vacio o inexistente";

    public static final int CODE = 1;

    public ErrorDeletingStudentException() {
        this("");
    }

    public ErrorDeletingStudentException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
