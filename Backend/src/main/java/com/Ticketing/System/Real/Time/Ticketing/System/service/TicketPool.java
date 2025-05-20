package com.Ticketing.System.Real.Time.Ticketing.System.service;

import com.Ticketing.System.Real.Time.Ticketing.System.dto.ConfigurationDto;
import com.Ticketing.System.Real.Time.Ticketing.System.dto.TicketDto;
import com.Ticketing.System.Real.Time.Ticketing.System.entity.Ticket;

public interface TicketPool {
    void addTickets(TicketDto ticketDto) throws InterruptedException;
    Ticket buyTicket() throws InterruptedException;
    int getAvailableTickets();

void configure(ConfigurationDto configurationDto);


    void startSystem();

    void stopSystem();
}
