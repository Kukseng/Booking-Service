package com.example.bookingservice.service;

import com.example.bookingservice.dto.BookingRequest;
import com.example.bookingservice.dto.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);

}
