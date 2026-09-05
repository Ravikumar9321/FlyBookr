import { BrowserRouter, Routes, Route} from "react-router-dom";  // Fixed import
import "./App.css";

import Homepage from "./Flight/Homepage";

import SearchLayout from "./Flight/SearchLayout";
import FlightBooking from "./Booking/FlightBooking";
import ViewBookings from "./Booking/BookingDetails";

function App() {
  return (
    <>
      <BrowserRouter>
        <div>
       
          <Routes>
            <Route path="/" element={<Homepage />} />
            
            <Route path="/search" element={<SearchLayout />}>
              
            </Route>
            
            <Route path="/booking" element={<FlightBooking />} />
            <Route path="/details" element={<ViewBookings />} />
          </Routes>
        </div>
      </BrowserRouter>
    </>
  );
}

export default App;
