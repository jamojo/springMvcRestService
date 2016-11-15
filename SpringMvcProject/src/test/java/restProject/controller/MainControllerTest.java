package restProject.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import restProject.domain.DayOfCalendar;
import restProject.domain.InputData;
import restProject.service.BookingService;
import restProject.service.InputDataProcessing;
import restProject.testUtils.Generators;
import restProject.testUtils.TestUtil;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private InputDataProcessing inputDataProcessing;

    @InjectMocks
    private MainController mainController;

    private MockMvc mockMvc;

    @Before
    public  void setup(){
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    public void testGetAllBookings() throws Exception{

        List<DayOfCalendar> resultFromDB = Generators.getTestDayOfCalendarList();

        when(bookingService.getAllBookings()).thenReturn(resultFromDB);

        mockMvc.perform(get("/getAllBookings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(resultFromDB.size())));

        verify(bookingService, times(1)).getAllBookings();
        verifyNoMoreInteractions(bookingService);

    }

    @Test
    public void testAddBookings() throws Exception {

        List<InputData> inputDataList = Generators.createTestInputDataList(4);

        mockMvc.perform(post("/addBookings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJson(inputDataList))
        )
                .andExpect(status().isOk());

        ArgumentCaptor<List> inputDataArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(inputDataProcessing, times(1)).processData(inputDataArgumentCaptor.capture());
        verifyNoMoreInteractions(inputDataProcessing);
        verify(bookingService, times(1)).addBookings(inputDataProcessing.processData(inputDataArgumentCaptor.capture()));
        verifyNoMoreInteractions(bookingService);

        assertList(inputDataList, inputDataArgumentCaptor.getValue());

    }

    private void assertList(List<InputData> inputDataList, List<InputData> inputDataArgument){
        assertThat(inputDataArgument.size(), is(inputDataList.size()));
        for(int i = 0; i < inputDataArgument.size(); i++){
            assertThat(inputDataArgument.get(i).getEmployeeId(), is(inputDataList.get(i).getEmployeeId()));
            assertThat(inputDataArgument.get(i).getMeetingDuration(), is(inputDataList.get(i).getMeetingDuration()));
            assertThat(inputDataArgument.get(i).getRequestSubmissionTime(), is(inputDataList.get(i).getRequestSubmissionTime()));
            assertThat(inputDataArgument.get(i).getMeetingStartTime(), is(inputDataList.get(i).getMeetingStartTime()));
        }
    }

}
