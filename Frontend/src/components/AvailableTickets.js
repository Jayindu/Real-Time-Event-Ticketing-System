import React, { useState } from "react";
import axios from "../axios";

const AvailableTickets = () => {
  const [available, setAvailable] = useState(null);

  const fetchAvailableTickets = async () => {
    try {
      const response = await axios.get("/available");
      setAvailable(response.data);
    } catch (error) {
      alert("Failed to fetch available tickets.");
    }
  };

  return (
    <div>
      <h2>Available Tickets</h2>
      <button onClick={fetchAvailableTickets}>Check Availability</button>
      {available !== null && <p>Available Tickets: {available}</p>}
    </div>
  );
};

export default AvailableTickets;
