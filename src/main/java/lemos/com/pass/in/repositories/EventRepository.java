package lemos.com.pass.in.repositories;

import lemos.com.pass.in.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {


}
