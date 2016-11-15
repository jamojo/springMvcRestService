package restProject.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import restProject.domain.Booking;
import restProject.domain.DayOfCalendar;
import restProject.repository.BookingRepository;
import restProject.testUtils.Generators;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/application-context.xml")
@Transactional
public class BookingServiceTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @Before
    public  void setup(){
    }

    @Test
    public void testAddBookings(){
        List<DayOfCalendar> dayOfCalendarList = Generators.getTestDayOfCalendarList();
        bookingService.addBookings(dayOfCalendarList);
        List<DayOfCalendar> result = bookingRepository.findAll();
        assertThat(result.containsAll(dayOfCalendarList), is(true));
    }

    @Test
    public void testAddBookingToExistingDOC(){
        List<DayOfCalendar> existingDOCList = bookingRepository.findAll();
        LocalDate existingDOC = existingDOCList.get(0).getDate();

        DayOfCalendar savingDOC = new DayOfCalendar(existingDOC);
        Booking savingBooking = Generators.getBooking();

        saveDOC(savingBooking,savingDOC);

        DayOfCalendar existingDayOfCalendar = bookingRepository.findByDate(existingDOC);
        assertThat(findIfBookingAdded(savingBooking, existingDayOfCalendar), is(true));

    }

    @Test
    public void testAddBookingsOverriding(){

        List<DayOfCalendar> existingDOCList = bookingRepository.findAll();
        LocalDate existingDOC = existingDOCList.get(0).getDate();
        Booking existingBooking = existingDOCList.get(0).getBooking().get(0);

        DayOfCalendar dayOfCalendar = new DayOfCalendar(existingDOC);
        Booking savingBooking = new Booking(existingBooking.getMeetingStartTime().plusMinutes(59) , existingBooking.getMeetingEndTime().plusHours(2), 1);

        saveDOC(savingBooking, dayOfCalendar);

        DayOfCalendar existingDayOfCalendar = bookingRepository.findByDate(existingDOC);
        assertThat(findIfBookingAdded(savingBooking, existingDayOfCalendar), is(false));

    }

    private boolean findIfBookingAdded(Booking savingBooking, DayOfCalendar existingDayOfCalendar){
        for(Booking booking: existingDayOfCalendar.getBooking()){
            if(booking.getEmployeeId() == savingBooking.getEmployeeId() && booking.getMeetingStartTime().equals(savingBooking.getMeetingStartTime()) &&
                    booking.getMeetingEndTime().equals(savingBooking.getMeetingEndTime()))
                return true;
        }
        return false;
    }

    private void saveDOC(Booking savingBooking, DayOfCalendar savingDOC){
        savingBooking.setDayOfCalendar(savingDOC);
        savingDOC.getBooking().add(savingBooking);
        List<DayOfCalendar> newList = Generators.getTestDayOfCalendarList(savingDOC);
        bookingService.addBookings(newList);
    }

    @Test
    public void testGetAllBookings(){

        List<DayOfCalendar> dayOfCalendarList = Generators.getTestDayOfCalendarList();
        bookingRepository.save(dayOfCalendarList);

        List<DayOfCalendar> returnValue = bookingService.getAllBookings();
        assertThat(returnValue.containsAll(dayOfCalendarList), is(true));

    }

    @Test
    public void testGetAllBookingsIsSortedByDay(){

        List<DayOfCalendar> returnValue = bookingService.getAllBookings();
        DayOfCalendar tempValue = new DayOfCalendar(LocalDate.MIN);
        for(DayOfCalendar dayOfCalendar : returnValue){
            assertThat(tempValue.getDate().isBefore(dayOfCalendar.getDate()), is(true));
            tempValue.setDate(dayOfCalendar.getDate());
        }

    }

    @Test
    public void testGetAllBookingsIsSortedBookings(){

        List<DayOfCalendar> returnValue = bookingService.getAllBookings();
        for(DayOfCalendar dayOfCalendar : returnValue){
            Booking tempValue = new Booking();
            tempValue.setMeetingStartTime(LocalTime.MIN);
            for(Booking booking : dayOfCalendar.getBooking()){
                assertThat(tempValue.getMeetingStartTime().isBefore(booking.getMeetingStartTime()), is(true));
                tempValue.setMeetingStartTime(booking.getMeetingStartTime());
            }
        }

    }

}
