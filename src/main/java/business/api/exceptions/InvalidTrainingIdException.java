package business.api.exceptions;

public class InvalidTrainingIdException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Campo de Training vacio o inexistente";

    public static final int CODE = 1;

    public InvalidTrainingIdException() {
        this("");
    }

    public InvalidTrainingIdException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
