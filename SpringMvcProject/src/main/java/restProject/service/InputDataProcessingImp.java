package restProject.service;


import org.springframework.stereotype.Service;
import restProject.domain.Booking;
import restProject.domain.DayOfCalendar;
import restProject.domain.InputData;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;



@Service
public class InputDataProcessingImp implements InputDataProcessing {

    private LocalTime startOfWorkDay;
    private LocalTime endOfWorkDay;
    private List<DayOfCalendar> dayOfCalendarList;

    public InputDataProcessingImp(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("workhours");
        startOfWorkDay = LocalTime.parse(resourceBundle.getString("start.time"));
        endOfWorkDay = LocalTime.parse(resourceBundle.getString("end.time"));
    }

    public List<DayOfCalendar> processData(List<InputData> inputDataList){
        dayOfCalendarList = new LinkedList<>();
        inputDataList.sort((InputData o1, InputData o2) -> o1.getRequestSubmissionTime().compareTo(o2.getRequestSubmissionTime()));
        inputDataList = processInputData(inputDataList);
        for(InputData inputData : inputDataList) {
            LocalDate meetingStartDate = inputData.getMeetingStartTime().toLocalDate();
            Booking newBooking = inputDataToBooking(inputData);
            dayOfCalendarList = addBookingToDayOfCalendar(meetingStartDate, newBooking, dayOfCalendarList);
        }
        return dayOfCalendarList;
    }

    private List<InputData> processInputData(List<InputData> inputDataList){
        List<InputData> list = new LinkedList<>();
        for (InputData inputData : inputDataList){
            if(inputData.getMeetingDuration() < 24) {
                LocalTime meetingStartTime = inputData.getMeetingStartTime().toLocalTime();
                LocalTime meetingEndTime = meetingStartTime.plusHours(inputData.getMeetingDuration());
                if (isWorkTime(meetingStartTime, meetingEndTime) && meetingStartTime.isBefore(meetingEndTime))
                    list.add(inputData);
            }
        }
        return list;
    }

    private boolean isWorkTime(LocalTime start, LocalTime end){
        if(TimeOverlapping.isOverlapping(LocalTime.MIN, startOfWorkDay, start, end) || TimeOverlapping.isOverlapping(endOfWorkDay,LocalTime.MAX, start, end))
            return false;
        return true;
    }

    private Booking inputDataToBooking(InputData inputData){
        LocalTime meetingStartTime = inputData.getMeetingStartTime().toLocalTime();
        LocalTime meetingEndTime = meetingStartTime.plusHours(inputData.getMeetingDuration());
        return new Booking(meetingStartTime, meetingEndTime, inputData.getEmployeeId());
    }

    private List<DayOfCalendar> addBookingToDayOfCalendar(LocalDate meetingStartTime, Booking newBooking, List<DayOfCalendar> dayOfCalendarList){
        for(DayOfCalendar dayOfCalendar: dayOfCalendarList){
            if(dayOfCalendar.getDate().equals(meetingStartTime)){
                if(!TimeOverlapping.checkIsOverlapWithExistingBookings(newBooking, dayOfCalendar.getBooking())){
                    newBooking.setDayOfCalendar(dayOfCalendar);
                    dayOfCalendar.getBooking().add(newBooking);
                    return dayOfCalendarList;
                }
                return dayOfCalendarList;
            }
        }
        DayOfCalendar dayOfCalendar = new DayOfCalendar(meetingStartTime);
        newBooking.setDayOfCalendar(dayOfCalendar);
        dayOfCalendar.getBooking().add(newBooking);
        dayOfCalendarList.add(dayOfCalendar);
        return dayOfCalendarList;
    }

}
