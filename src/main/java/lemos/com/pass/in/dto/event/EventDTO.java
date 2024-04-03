package lemos.com.pass.in.dto.event;

public record EventDTO(
        String id,
        String title,
        String details,
        String slug,
        Integer maximumAttendees,
        Integer attendeesAmout
)
{

}
