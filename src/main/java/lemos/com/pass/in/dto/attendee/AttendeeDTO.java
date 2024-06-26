package lemos.com.pass.in.dto.attendee;

import java.time.LocalDateTime;

public record AttendeeDTO(
        String id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime checkedInAt
)
{

}
