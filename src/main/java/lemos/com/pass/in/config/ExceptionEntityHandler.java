package lemos.com.pass.in.config;

import lemos.com.pass.in.domain.attendee.exceptions.AttendeeAlreadyRegisteredOnEvent;
import lemos.com.pass.in.domain.attendee.exceptions.AttendeeNotFoundException;
import lemos.com.pass.in.domain.checkin.expceptions.AlreadyCheckedInException;
import lemos.com.pass.in.domain.event.exceptions.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyRegisteredOnEvent.class)
    public ResponseEntity handleAttendeeAlreadyRegisteredOnEvent(AttendeeAlreadyRegisteredOnEvent exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(AlreadyCheckedInException.class)
    public ResponseEntity handleAlreadyCheckedInException(AlreadyCheckedInException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
