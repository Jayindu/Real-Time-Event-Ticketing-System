import java.math.BigDecimal;

public class Vendor implements Runnable {
    private int totalTickets; // Tickets willing to sell
    private int ticketReleaseRate; // Frequency of releasing


    private TicketPool ticketPool; // Shared resource between Vendors and Customers

    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        for (int i = 0; i < totalTickets; i++) {
            Ticket ticket = new Ticket();
            ticketPool.addTicket(ticket);
            try {
                Thread.sleep(ticketReleaseRate *1000); // To calculate to MS
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ticketPool.stopProduction();

    }
}
