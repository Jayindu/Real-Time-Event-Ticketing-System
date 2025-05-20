import React, { useState } from "react";
import axios from "../axios";

const ConfigureSystem = () => {
  const [config, setConfig] = useState({
    totalTickets: "",
    maxTicketCapacity: "",
    ticketReleaseRate: "",
    customerRetrievalRate: "",
    numberOfVendors: "",
    numberOfCustomers: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setConfig({ ...config, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        totalTickets: parseInt(config.totalTickets, 10),
        maxTicketCapacity: parseInt(config.maxTicketCapacity, 10),
        ticketReleaseRate: parseInt(config.ticketReleaseRate, 10),
        customerRetrievalRate: parseInt(config.customerRetrievalRate, 10),
        numberOfVendors: parseInt(config.numberOfVendors, 10),
        numberOfCustomers: parseInt(config.numberOfCustomers, 10),
      };

      await axios.post("/configure", payload);
      alert("System configured successfully!");
    } catch (error) {
      alert("Failed to configure the system.");
    }
  };

  return (
    <div>
      <h2>Configure Ticketing System</h2>
      <form onSubmit={handleSubmit}>
        <label>Total Tickets:</label>
        <input
          type="number"
          name="totalTickets"
          value={config.totalTickets}
          onChange={handleChange}
          required
        />
        <br />
        <label>Max Ticket Capacity:</label>
        <input
          type="number"
          name="maxTicketCapacity"
          value={config.maxTicketCapacity}
          onChange={handleChange}
          required
        />
        <br />
        <label>Ticket Release Rate (seconds):</label>
        <input
          type="number"
          name="ticketReleaseRate"
          value={config.ticketReleaseRate}
          onChange={handleChange}
          required
        />
        <br />
        <label>Customer Retrieval Rate (seconds):</label>
        <input
          type="number"
          name="customerRetrievalRate"
          value={config.customerRetrievalRate}
          onChange={handleChange}
          required
        />
        <br />
        <label>Number of Vendors:</label>
        <input
          type="number"
          name="numberOfVendors"
          value={config.numberOfVendors}
          onChange={handleChange}
          required
        />
        <br />
        <label>Number of Customers:</label>
        <input
          type="number"
          name="numberOfCustomers"
          value={config.numberOfCustomers}
          onChange={handleChange}
          required
        />
        <br />
        <button type="submit">Configure</button>
      </form>
    </div>
  );
};

export default ConfigureSystem;

