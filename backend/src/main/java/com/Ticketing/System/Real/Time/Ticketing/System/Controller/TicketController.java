package com.Ticketing.System.Real.Time.Ticketing.System.Controller;

import com.Ticketing.System.Real.Time.Ticketing.System.dto.ConfigurationDto;
import com.Ticketing.System.Real.Time.Ticketing.System.dto.TicketDto;
import com.Ticketing.System.Real.Time.Ticketing.System.entity.Ticket;
import com.Ticketing.System.Real.Time.Ticketing.System.service.TicketPool;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketPool ticketPool;

    public TicketController(TicketPool ticketPool) {

        this.ticketPool = ticketPool;
    }

    @PostMapping("/configure")
    public String configureSystem(@RequestBody ConfigurationDto configurationDto) {
        ticketPool.configure(configurationDto);
        return "System configured successfully with the provided parameters!";
    }
    @PostMapping("/start")
    public String startSystem() {
        ticketPool.startSystem();
        return "Ticketing system started!";
    }
    @PostMapping("/stop")
    public String stopSystem() {
        ticketPool.stopSystem();
        return "Ticketing system stopped!";
    }


    @GetMapping("/available")
    public int availableTickets() {

        return ticketPool.getAvailableTickets();
    }
}
