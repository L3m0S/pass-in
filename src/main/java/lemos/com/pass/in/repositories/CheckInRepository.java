package lemos.com.pass.in.repositories;

import lemos.com.pass.in.domain.checkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository  extends JpaRepository<CheckIn, Integer> {
}
