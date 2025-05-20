import React, { useState } from "react";
import axios from "../axios";

const StartStopSystem = () => {
  const [running, setRunning] = useState(false);

  const handleStart = async () => {
    try {
      await axios.post("/start");
      setRunning(true);
      alert("Ticketing system started!");
    } catch (error) {
      alert("Failed to start the system.");
    }
  };

  const handleStop = async () => {
    try {
      await axios.post("/stop");
      setRunning(false);
      alert("Ticketing system stopped!");
    } catch (error) {
      alert("Failed to stop the system.");
    }
  };

  return (
    <div>
      <h2>Control Ticketing System</h2>
      <button onClick={handleStart} disabled={running}>
        Start
      </button>
      <button onClick={handleStop} disabled={!running}>
        Stop
      </button>
    </div>
  );
};

export default StartStopSystem;
