import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

function ViewBookings() {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  // Filter state: user can enter bookingId or phone
  const [search, setSearch] = useState({
    bookingId: "",
    phone: "",
  });

  const [filteredBookings, setFilteredBookings] = useState([]); // Only matching records
  const [hasSearched, setHasSearched] = useState(false); // Only show results after search


  // Fetch all bookings (once, after first load)
  const fetchBookings = async () => {
    try {
      setLoading(true);
      const response = await axios.get("http://localhost:8080/api/booking");
      const data = response.data.data || response.data;
      setBookings(data);
      setError("");
    } catch (err) {
      console.error("Error fetching bookings:", err);
      setError("Failed to load bookings");
    } finally {
      setLoading(false);
    }
  };
 const cancelBooking = async (bookingId) => {
    if (window.confirm("Are you sure you want to cancel this booking?")) {
      try {
        await axios.delete(`http://localhost:8080/api/booking/${bookingId}`);
        alert("Booking cancelled successfully!");
        fetchBookings(); // Reload all bookings and re‑filter
      } catch (err) {
        alert("Failed to cancel booking");
      }
    }
  };

  useEffect(() => {
    fetchBookings();
  }, []);


  // Apply filter when bookings or search fields change
  useEffect(() => {
    if (!hasSearched) return;

    let filtered = bookings;

    if (search.bookingId) {
      filtered = filtered.filter(
        (b) => String(b.id).trim() === search.bookingId.trim()
      );
    }

    if (search.phone) {
      filtered = filtered.filter(
        (b) =>
          b.passengers?.some(
            (p) => String(p.contactNumber).includes(search.phone.trim())
          )
      );
    }

    setFilteredBookings(filtered);
  }, [bookings, search, hasSearched]);


  const handleSearch = () => {
  const idFilled = search.bookingId.trim() !== "";
  const phoneFilled = search.phone.trim() !== "";

  if (!idFilled && !phoneFilled) {
    alert("Please enter either Booking ID or Phone Number");
    return;
  }

    setHasSearched(true);
  };

  const handleReset = () => {
    setSearch({ bookingId: "", phone: "" });
    setHasSearched(false);
  };


  
  if (error) {
    return (
      <div style={styles.center}>
        <h2>{error}</h2>
        <button onClick={fetchBookings} style={styles.refreshBtn}>
          Retry
        </button>
      </div>
    );
  }

  // Before user searches, show only the filter
  if (!hasSearched) {
    return (
      <div style={{ padding: "20px", maxWidth: "800px", margin: "0 auto" }}>
        <div style={{ textAlign: "center", marginBottom: "30px" }}>
          <h1>📋 View Your Bookings</h1>
          <p>Search by Booking ID or Passenger Phone Number</p>
        </div>

        <div style={{ display: "flex", gap: "12px", marginBottom: "20px", flexWrap: "wrap" }}>
          <input
            type="text"
            placeholder="Enter Booking ID"
            value={search.bookingId}
            onChange={(e) =>
              setSearch((prev) => ({ ...prev, bookingId: e.target.value, phone: prev.phone }))
            }
            style={{
              padding: "10px",
              borderRadius: "6px",
              border: "1px solid #ddd",
              fontSize: "14px",
              flex: "1",
              minWidth: "180px",
            }}
          />
          <input
            type="text"
            placeholder="Enter Phone Number"
            value={search.phone}
            onChange={(e) =>
              setSearch((prev) => ({ ...prev, phone: e.target.value, bookingId: prev.bookingId }))
            }
            style={{
              padding: "10px",
              borderRadius: "6px",
              border: "1px solid #ddd",
              fontSize: "14px",
              flex: "1",
              minWidth: "180px",
            }}
          />
        </div>

        <div style={{ display: "flex", gap: "10px", justifyContent: "center" }}>
          <button onClick={handleSearch} style={styles.refreshBtn}>
            Search
          </button>
          <button onClick={handleReset} style={styles.searchBtn}>
            Clear
          </button>
        </div>
      </div>
    );
  }


  // After search, show filtered results
  return (
    <div style={{ padding: "20px", maxWidth: "1400px", margin: "0 auto" }}>
      <div style={styles.header}>
        <h1 style={{ margin: 0 }}>📋 My Bookings</h1>
        <div>
          <button onClick={handleReset} style={styles.refreshBtn}>
            🔄 New Search
          </button>
          <button onClick={fetchBookings} style={styles.refreshBtn}>
            Refresh All
          </button>
          <Link to="/search" style={styles.searchBtn}>
            🔍 New Flight Search
          </Link>
        </div>
      </div>

      {filteredBookings.length === 0 ? (
        <div style={styles.emptyState}>
          <h3>No bookings found for the given criteria</h3>
          <button onClick={handleReset} style={styles.searchBtn}>
            Try Another Search
          </button>
        </div>
      ) : (
        <>
          <div style={styles.tableContainer}>
            <table style={styles.table} cellPadding="0" cellSpacing="0">
              <thead>
                <tr style={styles.tableHeader}>
                  <th style={styles.th}>Booking ID</th>
                  <th style={styles.th}>Flight</th>
                  <th style={styles.th}>Route</th>
                  <th style={styles.th}>Date</th>
                  <th style={styles.th}>Passengers</th>
                  <th style={styles.th}>Payment</th>
                  <th style={styles.th}>Amount</th>
                  <th style={styles.th}>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredBookings.map((booking) => (
                  <tr key={booking.id} style={styles.tableRow}>
                    <td style={styles.td}>
                      <strong>#{booking.id}</strong>
                      <br />
                      <small style={styles.status}>{booking.status}</small>
                    </td>
                    <td style={styles.td}>
                      <strong>{booking.flight?.airlineName}</strong>
                      <br />
                      <small>
                        {booking.flight?.departureTime} - {booking.flight?.arrivalTime}
                      </small>
                    </td>
                    <td style={styles.td}>
                      <strong>{booking.flight?.source}</strong>
                      <br />
                      <small>→ {booking.flight?.destination}</small>
                    </td>
                    <td style={styles.td}>
                      {new Date(booking.payment?.paymentDate).toLocaleDateString()}
                    </td>
                    <td style={styles.td}>
                      {booking.passengers?.length || 0}
                      <br />
                      <small>
                        {booking.passengers?.map((p) => p.name).join(", ")}
                      </small>
                    </td>
                    <td style={styles.td}>
                      <small>{booking.payment?.mode}</small>
                    </td>
                    <td style={styles.td}>
                      <strong>₹{booking.payment?.amount || 0}</strong>
                    </td>
                    <td style={styles.td}>
                      <div style={styles.actionButtons}>
                        <button
                          onClick={() => cancelBooking(booking.id)}
                          style={styles.cancelBtn}
                          title="Cancel Booking"
                        >
                          ❌
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div style={styles.summary}>
            <strong>
              Showing {filteredBookings.length} of {bookings.length} total bookings
            </strong>
          </div>

          <div style={styles.footer}>
            <Link to="/" style={styles.homeLink}>
              🏠 Back to Home
            </Link>
          </div>
        </>
      )}
    </div>
  );


 
}


// --- styles (same as you had) ---
const styles = {
  header: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: "20px",
    paddingBottom: "15px",
    borderBottom: "2px solid #007bff",
  },
  tableContainer: {
    background: "white",
    borderRadius: "12px",
    boxShadow: "0 4px 20px rgba(0,0,0,0.1)",
    overflow: "hidden",
    marginBottom: "20px",
  },
  table: {
    width: "100%",
    borderCollapse: "collapse",
    fontSize: "14px",
  },
  tableHeader: {
    background: "#2c3e50",
    color: "white",
  },
  th: {
    padding: "10px 10px",
    textAlign: "left",
    fontWeight: "600",
    fontSize: "14px",
    borderBottom: "2px solid #34495e",
  },
  tableRow: {
    borderBottom: "1px solid #eee",
    transition: "background 0.2s",
    cursor: "pointer",
  },
  tableRowHover: {
    background: "#f8f9fa",
  },
  td: {
    padding: "16px 12px",
    verticalAlign: "top",
  },
  status: {
    padding: "3px 8px",
    borderRadius: "12px",
    background: "#d4edda",
    color: "#155724",
    fontSize: "11px",
    fontWeight: "bold",
  },
  actionButtons: {
    display: "flex",
    gap: "8px",
  },
  downloadBtn: {
    padding: "8px 12px",
    background: "#17a2b8",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    fontSize: "14px",
    width: "42px",
    height: "42px",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
  cancelBtn: {
    padding: "8px 12px",
    background: "#dc3545",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    fontSize: "14px",
    width: "42px",
    height: "42px",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
  summary: {
    textAlign: "center",
    padding: "15px",
    background: "#f8f9fa",
    borderRadius: "8px",
    color: "#6c757d",
    fontSize: "16px",
    fontWeight: "500",
  },
  center: {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    minHeight: "400px",
    color: "#6c757d",
  },
  refreshBtn: {
    padding: "10px 20px",
    background: "#007bff",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    fontWeight: "600",
    marginRight: "10px",
  },
  searchBtn: {
    padding: "10px 20px",
    background: "#28a745",
    color: "white",
    textDecoration: "none",
    borderRadius: "6px",
    fontWeight: "600",
  },
  emptyState: {
    textAlign: "center",
    padding: "60px 40px",
    background: "#f8f9fa",
    borderRadius: "12px",
    color: "#6c757d",
    border: "2px dashed #dee2e6",
  },
  footer: {
    textAlign: "center",
    padding: "30px 0",
  },
  homeLink: {
    color: "#007bff",
    textDecoration: "none",
    fontSize: "18px",
    fontWeight: "bold",
  },
};

export default ViewBookings;
