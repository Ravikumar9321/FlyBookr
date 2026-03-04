import { useLocation, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";

function FlightBooking() {
  // Styles defined FIRST - 50% WIDTH VERSION
  const styles = {
    container: {
      padding: "24px 20px",  // Reduced padding for narrower form
      maxWidth: "550px",     // 50% of original (1100px → 550px)
      margin: "0 auto",
      fontFamily: "'Inter', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
      background: "linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)",
      minHeight: "100vh",
      lineHeight: 1.6
    },
    title: {
      fontSize: "26px",      // Slightly smaller for narrow layout
      fontWeight: "800",
      color: "#1a202c",
      marginBottom: "28px",
      textAlign: "center"
    },
    loading: {
      textAlign: "center",
      padding: "100px 20px",
      fontSize: "18px",
      color: "#666"
    },
    flightCard: {
      background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
      color: "white",
      padding: "24px",       // Reduced padding
      borderRadius: "16px",
      marginBottom: "28px",
      boxShadow: "0 20px 60px rgba(102, 126, 234, 0.4)",
      textAlign: "center",
      position: "relative",
      overflow: "hidden"
    },
    flightIcon: {
      fontSize: "44px",      // Slightly smaller
      marginBottom: "12px",
      opacity: 0.9
    },
    flightAirline: {
      fontSize: "22px",
      fontWeight: "700",
      margin: "0 0 8px 0"
    },
    flightRoute: {
      fontSize: "18px",
      margin: "10px 0",
      fontWeight: "500"
    },
    flightTime: {
      fontSize: "15px",
      opacity: 0.95,
      margin: "10px 0"
    },
    flightPricing: {
      display: "flex",
      justifyContent: "center",
      gap: "16px",           // Reduced gap
      margin: "16px 0",
      flexWrap: "wrap"
    },
    pricePerSeat: {
      fontSize: "20px",
      fontWeight: "700"
    },
    totalPrice: {
      fontSize: "20px",
      fontWeight: "700",
      background: "rgba(255,255,255,0.2)",
      padding: "6px 14px",
      borderRadius: "20px"
    },
    seatsAvailable: {
      fontSize: "14px",
      opacity: 0.9,
      margin: "6px 0 0 0"
    },
    passengerSection: { marginBottom: "28px" },
    sectionHeader: {
      display: "flex",
      justifyContent: "space-between",
      alignItems: "center",
      marginBottom: "20px",
      paddingBottom: "14px",
      borderBottom: "3px solid #e2e8f0"
    },
    passengerCard: {
      background: "white",
      border: "2px solid #e2e8f0",
      borderRadius: "16px",
      padding: "20px",       // Reduced padding
      marginBottom: "18px",
      boxShadow: "0 8px 32px rgba(0,0,0,0.08)",
      transition: "all 0.3s cubic-bezier(0.4, 0, 0.2, 1)"
    },
    passengerCardHover: {
      boxShadow: "0 16px 48px rgba(0,0,0,0.15)",
      transform: "translateY(-4px)",
      borderColor: "#667eea"
    },
    cardHeader: {
      display: "flex",
      justifyContent: "space-between",
      alignItems: "center",
      marginBottom: "18px"
    },
    passengerRow: {
      display: "grid",
      gridTemplateColumns: "1fr 1fr",  // 2-column grid for 50% width
      gap: "12px",
      marginBottom: "14px"
    },
    input: {
      padding: "12px 16px",  // Slightly smaller
      border: "2px solid #e2e8f0",
      borderRadius: "10px",
      fontSize: "14px",
      transition: "all 0.3s ease",
      background: "white",
      outline: "none"
    },
    addBtn: {
      padding: "10px 20px",
      background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
      color: "white",
      border: "none",
      borderRadius: "10px",
      cursor: "pointer",
      fontSize: "14px",
      fontWeight: "600",
      transition: "all 0.3s ease",
      boxShadow: "0 4px 16px rgba(102, 126, 234, 0.3)"
    },
    addBtnDisabled: {
      background: "#a0aec0",
      cursor: "not-allowed",
      boxShadow: "none"
    },
    removeBtn: {
      padding: "8px 14px",
      background: "#ef4444",
      color: "white",
      border: "none",
      borderRadius: "8px",
      cursor: "pointer",
      fontSize: "13px",
      fontWeight: "600",
      transition: "all 0.2s ease"
    },
    paymentSection: {
      background: "white",
      padding: "20px",
      borderRadius: "16px",
      marginBottom: "24px",
      boxShadow: "0 8px 32px rgba(0,0,0,0.08)",
      border: "1px solid #f1f5f9"
    },
    paymentOptions: {
      marginTop: "12px"
    },
    selectInput: {
      padding: "12px 16px",
      border: "2px solid #e2e8f0",
      borderRadius: "10px",
      fontSize: "14px",
      transition: "all 0.3s ease",
      background: "white",
      outline: "none",
      width: "100%",
      appearance: "none",
      backgroundImage: "url(\"data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6,9 12,15 18,9'%3e%3c/polyline%3e%3c/svg%3e\")",
      backgroundPosition: "right 12px center",
      backgroundRepeat: "no-repeat",
      backgroundSize: "18px",
      paddingRight: "44px"
    },
    submitBtn: {
      padding: "16px 28px",
      background: "linear-gradient(135deg, #10b981 0%, #059669 100%)",
      color: "white",
      border: "none",
      borderRadius: "12px",
      fontSize: "16px",
      fontWeight: "700",
      cursor: "pointer",
      width: "100%",
      marginBottom: "18px",
      boxShadow: "0 12px 40px rgba(16, 185, 129, 0.4)",
      transition: "all 0.3s ease"
    },
    submitBtnDisabled: {
      background: "#6b7280",
      cursor: "not-allowed",
      boxShadow: "none"
    },
    backBtn: {
      padding: "14px 28px",
      background: "#6b7280",
      color: "white",
      border: "none",
      borderRadius: "10px",
      cursor: "pointer",
      fontSize: "15px",
      fontWeight: "600",
      width: "100%",
      transition: "all 0.3s ease",
      boxShadow: "0 4px 16px rgba(107, 114, 128, 0.3)"
    }
  };

  const location = useLocation();
  const navigate = useNavigate();
  const flight = location.state?.flight;
  
  const [totalPassengers, setTotalPassengers] = useState(1);
  const [passengers, setPassengers] = useState([
    { name: "", age: "", gender: "", seatNumber: "", contactNumber: "" }
  ]);
  const [loading, setLoading] = useState(false);
  const [paymentMode, setPaymentMode] = useState("UPI");
  const [hoveredCard, setHoveredCard] = useState(null);

  // ... rest of your functions remain exactly the same ...
  useEffect(() => {
    if (!flight) {
      alert("No flight data. Redirecting to search...");
      navigate("/search");
    }
  }, [flight, navigate]);

  const addPassenger = () => {
    if (totalPassengers < flight?.totalSeats) {
      setPassengers([...passengers, { name: "", age: "", gender: "", seatNumber: "", contactNumber: "" }]);
      setTotalPassengers(totalPassengers + 1);
    } else {
      alert(`Maximum ${flight?.totalSeats} seats available`);
    }
  };

  const removePassenger = (index) => {
    if (totalPassengers > 1) {
      const newPassengers = passengers.filter((_, i) => i !== index);
      setPassengers(newPassengers);
      setTotalPassengers(totalPassengers - 1);
    }
  };

  const handlePassengerChange = (index, field, value) => {
    const newPassengers = passengers.map((p, i) =>
      i === index ? { ...p, [field]: value } : p
    );
    setPassengers(newPassengers);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const hasEmptyFields = passengers.some(p => 
      !p.name || !p.age || !p.gender || !p.seatNumber || !p.contactNumber
    );
    
    if (hasEmptyFields) {
      alert("Please fill all passenger details");
      return;
    }

    setLoading(true);
    try {
      const bookingData = {
        status: "CONFIRMED",
        flight: { id: flight.id },
        passengers: passengers.map(p => ({
          name: p.name,
          age: parseInt(p.age),
          gender: p.gender,
          seatNumber: p.seatNumber,
          contactNumber: p.contactNumber
        })),
        payment: {
          mode: paymentMode,
          status: "CONFIRMED",
          amount: flight.price * totalPassengers
        }
      };

      console.log("Sending booking:", JSON.stringify(bookingData, null, 2));

      const response = await axios.post("http://localhost:8080/api/booking", bookingData);
      alert(`Booking confirmed for ${totalPassengers} passengers! ID: ${response.data.data.id}`);
      navigate("/");
    } catch (error) {
      console.error("Booking error:", error.response?.data);
      alert("Booking failed: " + (error.response?.data?.message || error.message));
    } finally {
      setLoading(false);
    }
  };

  if (!flight) {
    return <div style={styles.loading}>Loading flight details...</div>;
  }

  return (
    <div style={styles.container}>
      <h1 style={styles.title}>Book Flight</h1>
      
      <div style={styles.flightCard}>
        <div style={styles.flightIcon}>✈️</div>
        <h2 style={styles.flightAirline}>{flight.airlineName}</h2>
        <p style={styles.flightRoute}><strong>{flight.source}</strong> → <strong>{flight.destination}</strong></p>
        <p style={styles.flightTime}>🕒 {flight.departureTime} - {flight.arrivalTime}</p>
        <div style={styles.flightPricing}>
          <span style={styles.pricePerSeat}>₹{flight.price}/seat</span>
          <span style={styles.totalPrice}>Total: ₹{flight.price * totalPassengers}</span>
        </div>
        <p style={styles.seatsAvailable}>Seats Available: {flight.totalSeats}</p>
      </div>

      <div style={styles.passengerSection}>
        <div style={styles.sectionHeader}>
          <h3>Passenger Details ({totalPassengers})</h3>
          <button 
            type="button" 
            onClick={addPassenger} 
            disabled={totalPassengers >= flight.totalSeats} 
            style={{
              ...styles.addBtn,
              ...(totalPassengers >= flight.totalSeats ? styles.addBtnDisabled : {})
            }}
          >
            ➕ Add Passenger
          </button>
        </div>

        {passengers.map((passenger, index) => (
          <div 
            key={index} 
            style={{
              ...styles.passengerCard,
              ...(hoveredCard === index ? styles.passengerCardHover : {})
            }}
            onMouseEnter={() => setHoveredCard(index)}
            onMouseLeave={() => setHoveredCard(null)}
          >
            <div style={styles.cardHeader}>
              <h4>Passenger {index + 1}</h4>
              {totalPassengers > 1 && (
                <button type="button" onClick={() => removePassenger(index)} style={styles.removeBtn}>
                  ❌ Remove
                </button>
              )}
            </div>
            <div style={styles.passengerRow}>
              <input
                placeholder="Full Name *"
                value={passenger.name}
                onChange={(e) => handlePassengerChange(index, "name", e.target.value)}
                style={styles.input}
                required
              />
              <input
                type="number"
                placeholder="Age *"
                value={passenger.age}
                min="1"
                max="100"
                onChange={(e) => handlePassengerChange(index, "age", e.target.value)}
                style={styles.input}
                required
              />
            </div>
            <div style={styles.passengerRow}>
              <input
                type="text"
                placeholder="Seat Number (e.g., 19C) *"
                value={passenger.seatNumber}
                onChange={(e) => handlePassengerChange(index, "seatNumber", e.target.value)}
                style={styles.input}
                required
              />
              <select
                value={passenger.gender}
                onChange={(e) => handlePassengerChange(index, "gender", e.target.value)}
                style={styles.selectInput}
                required
              >
                <option value="">Gender *</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
              </select>
            </div>
            <div style={styles.passengerRow}>
              <input
                type="tel"
                placeholder="Contact Number *"
                value={passenger.contactNumber}
                onChange={(e) => handlePassengerChange(index, "contactNumber", e.target.value)}
                style={styles.input}
                required
              />
              <div /> {/* Empty div to balance 2-column grid */}
            </div>
          </div>
        ))}
      </div>

      <div style={styles.paymentSection}>
        <h3>Payment (₹{flight.price * totalPassengers})</h3>
        <div style={styles.paymentOptions}>
          <select 
            value={paymentMode} 
            onChange={(e) => setPaymentMode(e.target.value)} 
            style={styles.selectInput}
          >
            <option value="UPI">UPI</option>
            <option value="CARD">Credit/Debit Card</option>
            <option value="NETBANKING">Net Banking</option>
          </select>
        </div>
      </div>

      <button 
        onClick={handleSubmit} 
        disabled={loading} 
        style={{
          ...styles.submitBtn,
          ...(loading ? styles.submitBtnDisabled : {})
        }}
      >
        {loading ? "Processing..." : `Confirm Booking (₹${flight.price * totalPassengers})`}
      </button>

      <button onClick={() => navigate("/search")} style={styles.backBtn}>
        ← Back to Search
      </button>
    </div>
  );
}

export default FlightBooking;
