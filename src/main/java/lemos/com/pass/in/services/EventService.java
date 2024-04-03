package lemos.com.pass.in.services;

import lemos.com.pass.in.domain.attendee.Attendee;
import lemos.com.pass.in.domain.event.Event;
import lemos.com.pass.in.dto.event.EventResponseDTO;
import lemos.com.pass.in.repositories.AttendeeRepository;
import lemos.com.pass.in.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     }
}
