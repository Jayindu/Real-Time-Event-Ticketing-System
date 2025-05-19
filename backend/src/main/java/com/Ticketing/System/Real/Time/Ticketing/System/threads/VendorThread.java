package com.Ticketing.System.Real.Time.Ticketing.System.threads;

import com.Ticketing.System.Real.Time.Ticketing.System.dto.TicketDto;
import com.Ticketing.System.Real.Time.Ticketing.System.entity.Ticket;
import com.Ticketing.System.Real.Time.Ticketing.System.service.TicketPool;
import com.Ticketing.System.Real.Time.Ticketing.System.service.impl.TicketPoolImpl;

public class VendorThread implements Runnable {

    private final TicketPool ticketPool;
    private final int totalTickets;   // Number of tickets this vendor is responsible for
    private final int releaseRate;   // Rate of ticket release in seconds
    private final TicketPoolImpl poolImpl;
    public VendorThread(TicketPool ticketPool, int totalTickets, int releaseRate, TicketPoolImpl poolImpl) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.releaseRate = releaseRate;
        this.poolImpl = poolImpl;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < totalTickets && poolImpl.isRunning(); i++) {
                if (!poolImpl.isRunning() || Thread.interrupted()) {
                    break;
                }
                Ticket ticket = new Ticket();
                synchronized (ticketPool) {
                    ticketPool.addTickets(new TicketDto(ticket.getEventName(), ticket.getPrice(), 1));
                }
                System.out.println(Thread.currentThread().getName() + " added a ticket.");
                Thread.sleep(releaseRate * 1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " has stopped gracefully.");
        }
    }
}
