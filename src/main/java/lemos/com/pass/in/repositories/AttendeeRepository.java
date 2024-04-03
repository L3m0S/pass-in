package lemos.com.pass.in.repositories;

import lemos.com.pass.in.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository  extends JpaRepository<Attendee, String> {
}
