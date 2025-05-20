import React from "react";
import ConfigureSystem from "./components/ConfigureSystem";
import StartStopSystem from "./components/StartStopSystem";
import AvailableTickets from "./components/AvailableTickets";
import "./App.css"; // Import the CSS file for styling

function App() {
  return (
    <div>
      <h1 style={{ textAlign: "center", marginBottom: "20px" }}>
        Real-Time Event Ticketing System
      </h1>
      <div className="container">
        {/* Left Column: Configure System */}
        <div className="left-column">
          <ConfigureSystem />
        </div>

        {/* Middle Column: Start/Stop Buttons */}
        <div className="middle-column">
          <StartStopSystem />
        </div>

        {/* Right Column: Available Tickets */}
        <div className="right-column">
          <AvailableTickets />
        </div>
      </div>
    </div>
  );
}

export default App;





