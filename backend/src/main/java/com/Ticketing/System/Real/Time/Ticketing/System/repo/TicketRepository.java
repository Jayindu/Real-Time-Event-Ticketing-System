package com.Ticketing.System.Real.Time.Ticketing.System.repo;

import com.Ticketing.System.Real.Time.Ticketing.System.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}