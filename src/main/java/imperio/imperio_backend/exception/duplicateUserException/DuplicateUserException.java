package imperio.imperio_backend.exception.duplicateUserException;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String message){
        super(message);
    }
}
