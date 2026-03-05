package com.example.bookingservice.controller;

import com.example.bookingservice.dto.BookingRequest;
import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookingController {

private final BookingService bookingService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(consumes = "application/json",produces ="application/json" , path = "/booking")
    public BookingResponse createBooking(@RequestBody BookingRequest request){

        return bookingService.createBooking(request);
    }

}
