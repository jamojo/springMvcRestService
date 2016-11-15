package restProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import restProject.service.BookingService;
import restProject.service.InputDataProcessing;
import restProject.domain.InputData;

import java.util.List;


@RestController
public class MainController {

    @Autowired
    BookingService bookingService;

    @Autowired
    InputDataProcessing inputDataProcessing;

    @GetMapping(value = "/getAllBookings")
    public List getAllBookings(){
        return bookingService.getAllBookings();
    }

    @PostMapping(value = "/addBookings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addBookings(@RequestBody List<InputData> bookings){
        bookingService.addBookings(inputDataProcessing.processData(bookings));
    }

}
