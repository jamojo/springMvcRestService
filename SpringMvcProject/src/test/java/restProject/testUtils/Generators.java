package restProject.testUtils;


import restProject.domain.Booking;
import restProject.domain.DayOfCalendar;
import restProject.domain.InputData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Generators {

    public static LocalDateTime localDateTime = LocalDateTime.of(2012,12,12,12,12);


    public static List<InputData> createTestInputDataList(int count){
        List<InputData> inputDataList = new LinkedList<>();
        for(int i = 1; i <= count; i++){
            inputDataList.add(new InputData(localDateTime, i, localDateTime, i));
        }
        return inputDataList;
    }


    public static List<DayOfCalendar> getTestDayOfCalendarList(){
        List<DayOfCalendar> list = new LinkedList<>();
        list.add(new DayOfCalendar(LocalDate.now()));
        return list;
    }

    public static List<DayOfCalendar> getTestDayOfCalendarList(DayOfCalendar dayOfCalendar){
        List<DayOfCalendar> list = new LinkedList<>();
        list.add(dayOfCalendar);
        return list;
    }

    public static Booking getBooking(){
        return new Booking(LocalTime.MIN, LocalTime.MIN, 1);
    }

}
