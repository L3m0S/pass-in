package lemos.com.pass.in.services;

import lemos.com.pass.in.domain.attendee.Attendee;
import lemos.com.pass.in.domain.event.Event;
import lemos.com.pass.in.dto.event.EventIdDTO;
import lemos.com.pass.in.dto.event.EventRequestDTO;
import lemos.com.pass.in.dto.event.EventResponseDTO;
import lemos.com.pass.in.repositories.AttendeeRepository;
import lemos.com.pass.in.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    @Autowired
    private final EventRepository eventRepository;

    @Autowired
    private final AttendeeRepository attendeeRepository;

     public EventResponseDTO getEventById(String eventId) {
         Event event = this.eventRepository.findById(eventId)
                 .orElseThrow(() -> new RuntimeException("Event not found!"));

         List<Attendee> attendeeList = this.attendeeRepository.findByEventId(eventId);
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

     private String createSlug(String text) {
         String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);

         return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                 .replaceAll("[^\\w\\s]", "")
                 .replaceAll("\\s+", "-");
     };
}
