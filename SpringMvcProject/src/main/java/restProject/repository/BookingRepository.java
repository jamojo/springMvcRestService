package restProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restProject.domain.Booking;
import restProject.domain.DayOfCalendar;

import java.time.LocalDate;
import java.util.List;


public interface BookingRepository extends JpaRepository<DayOfCalendar, Integer> {

    DayOfCalendar findByDate(LocalDate date);
    List<DayOfCalendar> findAllByOrderByDateAsc();

}
