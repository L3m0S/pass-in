package lemos.com.pass.in.controllers;

import lemos.com.pass.in.domain.checkin.CheckIn;
import lemos.com.pass.in.dto.attendee.AttendeeBadgeResponseDTO;
import lemos.com.pass.in.services.AttendeeService;
import lemos.com.pass.in.services.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;
    private final CheckInService checkInService;

    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeBadgeResponseDTO badge = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok().body(badge);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity checkInAttendee(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        this.checkInService.attendeeCheckIn(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        return ResponseEntity.created(uri).build();
    }
}
