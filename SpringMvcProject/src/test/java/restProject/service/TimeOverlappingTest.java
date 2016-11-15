package restProject.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import restProject.domain.Booking;
import restProject.domain.DayOfCalendar;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static restProject.service.TimeOverlapping.checkIsOverlapWithExistingBookings;
import static restProject.service.TimeOverlapping.isOverlapping;




@RunWith(MockitoJUnitRunner.class)
public class TimeOverlappingTest {

    @Test
    public void isOverlappingTest(){

        LocalTime start1 = LocalTime.of(9,0);
        LocalTime end1 = LocalTime.of(12,20);

        LocalTime start2 = LocalTime.of(12,00);
        LocalTime end2 = LocalTime.of(14,23);

        //9:00-12:20 and 12:00-14:23
        assertThat(isOverlapping(start1,end1,start2,end2), is(true));

        start2 = LocalTime.of(8,00);
        end2 = LocalTime.of(9,0);

        //9:00-12:20 and 08:00-09:00
        assertThat(isOverlapping(start1,end1,start2,end2), is(false));

        start2 = LocalTime.of(12,20);
        end2 = LocalTime.of(15,23);

        //9:00-12:20 and 12:20-15:23
        assertThat(isOverlapping(start1,end1,start2,end2), is(false));

        start2 = LocalTime.of(7,15);
        end2 = LocalTime.of(10,30);

        //9:00-12:20 and 07:15-10:30
        assertThat(isOverlapping(start1,end1,start2,end2), is(true));

        start2 = LocalTime.of(9,30);

        //9:00-12:20 and 09:30-10:30
        assertThat(isOverlapping(start1,end1,start2,end2), is(true));

    }

    @Test
    public void isOverlappingWithExistingTest(){
        DayOfCalendar dayOfCalendar = new DayOfCalendar(LocalDate.now());
        Booking templateBooking = new Booking(LocalTime.now(), LocalTime.now().plusHours(2), 1);
        dayOfCalendar.getBooking().add(templateBooking);
        boolean result = checkIsOverlapWithExistingBookings(new Booking(LocalTime.now().plusHours(1), LocalTime.now().plusHours(3), 1), dayOfCalendar.getBooking());
        assertThat(result, is(true));
        result = checkIsOverlapWithExistingBookings(new Booking(LocalTime.now().plusHours(3), LocalTime.now().plusHours(6), 1), dayOfCalendar.getBooking());
        assertThat(result, is(false));
    }

}
