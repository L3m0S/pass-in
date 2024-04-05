package lemos.com.pass.in.domain.attendee.exceptions;

public class AttendeeAlreadyRegisteredOnEvent extends  RuntimeException{
    public AttendeeAlreadyRegisteredOnEvent(String message) {
        super(message);
    }
}
