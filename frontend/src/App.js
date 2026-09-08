import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";

import Homepage from "./Flight/Homepage";
import SearchLayout from "./Flight/SearchLayout";
import FlightBooking from "./Booking/FlightBooking";
import ViewBookings from "./Booking/BookingDetails";
import Login from "./Flight/Login";
import PrivateRoute from "./Flight/PrivateRoute";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* ✅ Default route shows Login */}
        <Route path="/" element={<Login />} />

        {/* ✅ Protected routes */}
        <Route path="/home" element={<PrivateRoute><Homepage /></PrivateRoute>} />
        <Route path="/search" element={<PrivateRoute><SearchLayout /></PrivateRoute>} />
        <Route path="/booking" element={<PrivateRoute><FlightBooking /></PrivateRoute>} />
        <Route path="/details" element={<PrivateRoute><ViewBookings /></PrivateRoute>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
