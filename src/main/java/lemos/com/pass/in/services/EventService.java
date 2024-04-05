package lemos.com.pass.in.services;

import lemos.com.pass.in.domain.attendee.Attendee;
import lemos.com.pass.in.domain.event.Event;
import lemos.com.pass.in.domain.event.exceptions.EventFullException;
import lemos.com.pass.in.domain.event.exceptions.EventNotFoundException;
import lemos.com.pass.in.dto.attendee.AttendeeIdDTO;
import lemos.com.pass.in.dto.attendee.AttendeeRequestDTO;
import lemos.com.pass.in.dto.event.EventIdDTO;
import lemos.com.pass.in.dto.event.EventRequestDTO;
import lemos.com.pass.in.dto.event.EventResponseDTO;
import lemos.com.pass.in.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

     public EventResponseDTO getEventDTOById(String eventId) {
         Event event = this.getEventById(eventId);

         List<Attendee> attendeeList = this.attendeeService.findByEventId(eventId);
         return new EventResponseDTO(event, attendeeList.size());
     };

     public EventIdDTO createEvent(EventRequestDTO eventDTO) {

         Event newEvent = new Event();
         newEvent.setTitle(eventDTO.title());
         newEvent.setDetails(eventDTO.details());
         newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
         newEvent.setSlug(this.createSlug(eventDTO.title()));

         this.eventRepository.save(newEvent);

         return new EventIdDTO(newEvent.getId());
     };

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendee) {
        this.attendeeService.verifyAttendeeSubscription(attendee.email(), eventId);

        Event event = this.getEventById(eventId);

        List<Attendee> attendeeList = this.attendeeService.findByEventId(eventId);

        if (attendeeList.size() >= event.getMaximumAttendees() ) {
            throw new EventFullException("Event reached the maximum attendees limit!");
        }

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendee.name());
        newAttendee.setEmail(attendee.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    public Event getEventById(String eventId) {
        return this.eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found!"));
    }

     private String createSlug(String text) {
         String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);

         return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                 .replaceAll("[^\\w\\s]", "")
                 .replaceAll("\\s+", "-");
     };
}
