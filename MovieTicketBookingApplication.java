package com.moviebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.moviebooking")
public class MovieTicketBookingApplication {

    public static void main(String[] args) {

        SpringApplication.run( MovieTicketBookingApplication.class, args);
    }
}
