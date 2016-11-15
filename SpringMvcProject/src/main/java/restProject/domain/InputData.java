package restProject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;


public class InputData {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime requestSubmissionTime;

    private int employeeId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm")
    private LocalDateTime meetingStartTime;
    private int meetingDuration;

    public InputData(){}

    public InputData(LocalDateTime requestSubmissionTime, int employeeId, LocalDateTime meetingStartTime, int meetingDuration){
        this.requestSubmissionTime = requestSubmissionTime;
        this.employeeId = employeeId;
        this.meetingStartTime = meetingStartTime;
        this.meetingDuration = meetingDuration;
    }

    public LocalDateTime getRequestSubmissionTime() {
        return requestSubmissionTime;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDateTime getMeetingStartTime() {
        return meetingStartTime;
    }

    public int getMeetingDuration() {
        return meetingDuration;
    }

    public void setRequestSubmissionTime(LocalDateTime requestSubmissionTime) {
        this.requestSubmissionTime = requestSubmissionTime;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setMeetingStartTime(LocalDateTime meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public void setMeetingDuration(int meetingDuration) {
        this.meetingDuration = meetingDuration;
    }

}
