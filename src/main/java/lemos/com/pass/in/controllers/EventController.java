package lemos.com.pass.in.controllers;

import lemos.com.pass.in.dto.attendee.AttendeeIdDTO;
import lemos.com.pass.in.dto.attendee.AttendeeRequestDTO;
import lemos.com.pass.in.dto.attendee.AttendeesListResponseDTO;
import lemos.com.pass.in.dto.event.EventIdDTO;
import lemos.com.pass.in.dto.event.EventRequestDTO;
import lemos.com.pass.in.dto.event.EventResponseDTO;
import lemos.com.pass.in.services.AttendeeService;
import lemos.com.pass.in.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
        EventResponseDTO event = this.eventService.getEventDTOById(id);
        return ResponseEntity.ok().body(event);
    };

    @PostMapping("")
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO createdEventId = this.eventService.createEvent(body);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(createdEventId.id()).toUri();
        return ResponseEntity.created(uri).body(createdEventId);
    };

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id) {
        AttendeesListResponseDTO attendeesList = attendeeService.getEventAttendees(id);
        return ResponseEntity.ok().body(attendeesList);
    };

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerAttendeeOnEvent(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, body);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    };
}