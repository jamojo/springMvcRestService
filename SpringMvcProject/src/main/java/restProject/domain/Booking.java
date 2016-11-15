package restProject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import javax.persistence.*;
import java.time.LocalTime;


@Entity
@Table(name = "bookings")
public class Booking {


    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime meetingStartTime;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime meetingEndTime;
    private int employeeId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dayOfCalendar")
    private DayOfCalendar dayOfCalendar;


    public Booking(){}

    public Booking(LocalTime meetingStartTime, LocalTime meetingEndTime, int employeeId){
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public LocalTime getMeetingStartTime() {
        return meetingStartTime;
    }

    public LocalTime getMeetingEndTime() {
        return meetingEndTime;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public DayOfCalendar getDayOfCalendar() {
        return dayOfCalendar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMeetingStartTime(LocalTime meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public void setMeetingEndTime(LocalTime meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setDayOfCalendar(DayOfCalendar dayOfCalendar) {
        this.dayOfCalendar = dayOfCalendar;
    }

}
