package restProject.service;


import restProject.domain.Booking;

import java.time.LocalTime;
import java.util.List;

public class TimeOverlapping {

    static boolean checkIsOverlapWithExistingBookings(Booking newBooking, List<Booking> existingBookings){
        for(Booking booking : existingBookings){
            boolean bool = isOverlapping(newBooking.getMeetingStartTime(), newBooking.getMeetingEndTime(),booking.getMeetingStartTime(), booking.getMeetingEndTime());
            if(bool)
                return true;
        }
        return false;
    }

    static boolean isOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

}
