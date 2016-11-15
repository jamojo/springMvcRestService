package restProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restProject.domain.DayOfCalendar;

import java.time.LocalDate;


public interface BookingRepository extends JpaRepository<DayOfCalendar, Integer> {

    DayOfCalendar findByDate(LocalDate date);

}
