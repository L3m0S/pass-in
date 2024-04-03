package lemos.com.pass.in.controllers;

import lemos.com.pass.in.dto.event.EventIdDTO;
import lemos.com.pass.in.dto.event.EventRequestDTO;
import lemos.com.pass.in.dto.event.EventResponseDTO;
import lemos.com.pass.in.services.EventService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    @Autowired
    private final EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
        EventResponseDTO event = this.eventService.getEventById(id);
        return ResponseEntity.ok().body(event);
    };

    @PostMapping("")
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO createdEventId = this.eventService.createEvent(body);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(createdEventId.id()).toUri();
        return ResponseEntity.created(uri).body(createdEventId);
    };
}