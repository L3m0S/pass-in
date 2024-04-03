package lemos.com.pass.in.dto.event;

import lemos.com.pass.in.domain.event.Event;

public class EventResponseDTO {

    EventDTO event;

    public EventResponseDTO(Event event, Integer numberOfAttendees) {
        this.event = new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getDetails(),
                event.getSlug(),
                event.getMaximumAttendees(),
                numberOfAttendees
        );
    };
}
