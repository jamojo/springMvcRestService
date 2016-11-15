package restProject.service;


import restProject.domain.DayOfCalendar;

import java.util.List;



public interface BookingService {

    void addBookings(List<DayOfCalendar> list);
    List<DayOfCalendar> getAllBookings();

}
