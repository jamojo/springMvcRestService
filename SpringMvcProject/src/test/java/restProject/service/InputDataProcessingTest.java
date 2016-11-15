package restProject.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import restProject.domain.DayOfCalendar;
import restProject.domain.InputData;
import restProject.testUtils.Generators;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class InputDataProcessingTest {


    InputDataProcessing inputDataProcessing;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        inputDataProcessing = new InputDataProcessingImp();
    }


    @Test
    public void processInputDataTestStartTimeAfterEndTime(){

        InputData testInputData = new InputData();
        testInputData.setMeetingStartTime(Generators.localDateTime);
        testInputData.setMeetingDuration(-1);
        List<DayOfCalendar> resultTest = getResultList(testInputData);
        assertThat(resultTest.size(), is(0));

    }

    @Test
    public void processInputDataTestIsOutOfWorkDay(){

        InputData testInputData = new InputData();
        testInputData.setMeetingStartTime(LocalDateTime.MAX);
        List<DayOfCalendar> resultTest = getResultList(testInputData);
        assertThat(resultTest.size(), is(0));

    }

    private List<DayOfCalendar> getResultList(InputData testInputData){
        List<InputData> list = new LinkedList<>();
        list.add(testInputData);
        return inputDataProcessing.processData(list);
    }

    @Test
    public void processInputDataTestIsOverlapping(){

        List<InputData> testInputDataList = Generators.createTestInputDataList(5);
        List<DayOfCalendar> result = inputDataProcessing.processData(testInputDataList);
        assertThat(result.size(), is(1));

    }

}
