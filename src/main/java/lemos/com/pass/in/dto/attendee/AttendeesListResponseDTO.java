package lemos.com.pass.in.dto.attendee;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public record AttendeesListResponseDTO(List<AttendeeDTO> attendees) {

}
