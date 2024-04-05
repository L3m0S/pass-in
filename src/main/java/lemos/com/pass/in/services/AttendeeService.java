package lemos.com.pass.in.services;

import lemos.com.pass.in.domain.attendee.Attendee;
import lemos.com.pass.in.domain.attendee.exceptions.AttendeeAlreadyRegisteredOnEvent;
import lemos.com.pass.in.domain.attendee.exceptions.AttendeeNotFoundException;
import lemos.com.pass.in.domain.checkin.CheckIn;
import lemos.com.pass.in.dto.attendee.AttendeeBadgeResponseDTO;
import lemos.com.pass.in.dto.attendee.AttendeeDTO;
import lemos.com.pass.in.dto.attendee.AttendeesListResponseDTO;
import lemos.com.pass.in.dto.attendee.AttendeeBadgeDTO;
import lemos.com.pass.in.repositories.AttendeeRepository;
import lemos.com.pass.in.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInRepository checkInRepository;

    public List<Attendee> findByEventId(String eventId) {
        List<Attendee> attendeesList = this.attendeeRepository.findByEventId(eventId);

        return attendeesList;
    }

    public AttendeesListResponseDTO getEventAttendees(String eventId) {
        List<Attendee> attendeeList = findByEventId(eventId);

        List<AttendeeDTO> attendeeDTOS = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDTO(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDTOS);
    }

    public Attendee registerAttendee(Attendee attendee) {
        this.attendeeRepository.save(attendee);
        return attendee;
    }

    public void verifyAttendeeSubscription(String email, String eventId) {
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if (isAttendeeRegistered.isPresent()) {
            throw new AttendeeAlreadyRegisteredOnEvent("Attendee is already subscribed to this event!");
        }
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.attendeeRepository.findById(attendeeId).orElseThrow(() -> {
            throw new AttendeeNotFoundException("Attendee with given ID not found!");
        });

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendee.getId()).toUri().toString();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId());
        return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
    }

    public Attendee getAttendeeById(String attendeeId) {
        return attendeeRepository.findById(attendeeId).orElseThrow(() -> {
            throw new AttendeeNotFoundException("Attendee with given ID not found!");
        });
    }
}
