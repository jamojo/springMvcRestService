package restProject.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restProject.domain.Booking;
import restProject.domain.DayOfCalendar;
import restProject.repository.BookingRepository;

import java.util.List;



@Service
@Transactional
public class BookingsServiceImp implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    public void addBookings(List<DayOfCalendar> dayOfCalendarList) {
        for(DayOfCalendar dayOfCalendar: dayOfCalendarList){
            DayOfCalendar storedDayOfCalendar = bookingRepository.findByDate(dayOfCalendar.getDate());
            if(storedDayOfCalendar == null)
                bookingRepository.save(dayOfCalendar);
            else
                saveNewBookings(dayOfCalendar, storedDayOfCalendar);
        }
    }

    private void saveNewBookings(DayOfCalendar newDayOfCalendar, DayOfCalendar storedDayOfCalendar){
        for(Booking newBooking : newDayOfCalendar.getBooking()){
            if(!TimeOverlapping.checkIsOverlapWithExistingBookings(newBooking, storedDayOfCalendar.getBooking())){
                newBooking.setDayOfCalendar(storedDayOfCalendar);
                storedDayOfCalendar.getBooking().add(newBooking);
            }
        }
        bookingRepository.save(storedDayOfCalendar);
    }

    public List<DayOfCalendar> getAllBookings() {
        List<DayOfCalendar> dayOfCalendarList = bookingRepository.findAllByOrderByDateAsc();
        return sortDayOfCalendarBookings(dayOfCalendarList);
    }

    private List<DayOfCalendar> sortDayOfCalendarBookings(List<DayOfCalendar> dayOfCalendarList){
        for(DayOfCalendar dayOfCalendar : dayOfCalendarList){
            dayOfCalendar.getBooking().sort(
                    (Booking o1, Booking o2) -> o1.getMeetingStartTime().compareTo(o2.getMeetingStartTime())
            );
        }
        return dayOfCalendarList;
    }

}
