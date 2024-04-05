package lemos.com.pass.in.domain.checkin.expceptions;

public class AlreadyCheckedInException extends RuntimeException{
    public AlreadyCheckedInException(String message) {
        super(message);
    }
}
