package lemos.com.pass.in.services;

import lemos.com.pass.in.domain.attendee.Attendee;
import lemos.com.pass.in.domain.checkin.CheckIn;
import lemos.com.pass.in.domain.checkin.expceptions.AlreadyCheckedInException;
import lemos.com.pass.in.repositories.AttendeeRepository;
import lemos.com.pass.in.repositories.CheckInRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final AttendeeService attendeeService;

    public CheckIn attendeeCheckIn(String attendeeId) {
        Attendee attendee = attendeeService.getAttendeeById(attendeeId);
        this.checkAlreadyCheckedIn(attendeeId);

        CheckIn attendeeCheckIn = new CheckIn();
        attendeeCheckIn.setAttendee(attendee);
        attendeeCheckIn.setCreatedAt(LocalDateTime.now());

        this.checkInRepository.save(attendeeCheckIn);
        return attendeeCheckIn;
    }

    private void checkAlreadyCheckedIn(String attendeeId) {
        Optional<CheckIn> alreadyCheckedIn = this.checkInRepository.findByAttendeeId(attendeeId);
        if (alreadyCheckedIn.isPresent()) {
            throw new AlreadyCheckedInException("Attendee already checked-in!");
        }
    }
}
