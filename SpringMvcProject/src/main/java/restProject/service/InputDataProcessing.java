package restProject.service;


import restProject.domain.DayOfCalendar;
import restProject.domain.InputData;

import java.util.List;



public interface InputDataProcessing {

    List<DayOfCalendar> processData(List<InputData> inputDataList);

}
