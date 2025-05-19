package com.Ticketing.System.Real.Time.Ticketing.System.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


public class TicketDto {
    private String eventName;
    private BigDecimal price;
    private int count;

    public TicketDto(String eventName, BigDecimal price, int count) {
        this.eventName = eventName;
        this.price = price;
        this.count = count;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}

