import axios from "axios";
import { useState, useEffect } from "react";
import { Link, useNavigate} from "react-router-dom";



function SearchLayout() {
    const navigate = useNavigate();
  const [allFlights, setAllFlights] = useState([]);  
  const [flights, setFlights] = useState([]);       
  const [airlineName, setAirlineName] = useState("");
  const [price, setPrice] = useState("");
  const [source, setSource] = useState("");
  const [destination, setDestination] = useState("");

  useEffect(() => {
    const fetchAllFlights = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/flight");
        setAllFlights(response.data.data || response.data);
        setFlights(response.data.data || response.data);
      } catch (error) {
        console.error("Failed to load flights", error);
      }
    };
    fetchAllFlights();
  }, []);

  useEffect(() => {
    let filtered = [...allFlights];
    if (airlineName) {
      filtered = filtered.filter(flight =>
        flight.airlineName.toLowerCase().includes(airlineName.toLowerCase())
      );
    }
    if (price) {
      filtered = filtered.filter(flight => flight.price <= parseInt(price));
    }
    if (source) {
      filtered = filtered.filter(flight => flight.source.toLowerCase() === source.toLowerCase());
    }
    if (destination) {
      filtered = filtered.filter(flight => flight.destination.toLowerCase() === destination.toLowerCase());
    }
    setFlights(filtered);
  }, [airlineName, price, source, destination, allFlights]);

  const clearFilters = () => {
    setAirlineName(""); setPrice(""); setSource(""); setDestination("");
    setFlights(allFlights);
  };

  return (
    <div style={styles.container}>
      {/* Header */}
      <div style={styles.header}>
        <h1 style={styles.title}>✈️ Flight Search</h1>
        <div style={styles.filterCount}>
          Showing {flights.length} of {allFlights.length} flights
        </div>
      </div>

      {/* Filters */}
      <div style={styles.filters}>
        <div style={styles.filterRow}>
          <input
            type="text"
            placeholder="Airline Name"
            value={airlineName}
            onChange={(e) => setAirlineName(e.target.value)}
            style={styles.input}
          />
          <input
            type="number"
            placeholder="Max Price (₹)"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            style={styles.input}
          />
          <input
            type="text"
            placeholder="From"
            value={source}
            onChange={(e) => setSource(e.target.value)}
            style={styles.input}
          />
          <input
            type="text"
            placeholder="To"
            value={destination}
            onChange={(e) => setDestination(e.target.value)}
            style={styles.input}
          />
        </div>
        <button onClick={clearFilters} style={styles.clearBtn}>
          Clear Filters
        </button>
        <button onClick={()=>navigate("/")} style={styles.clearBtn}>Back to Home</button>
      </div>

      {/* Results Table */}
      {flights.length > 0 ? (
        <div style={styles.tableContainer}>
          <table style={styles.table}>
            <thead>
              <tr style={styles.tableHead}>
                <th style={styles.th}>#</th>
                <th style={styles.th}>Airline</th>
                <th style={styles.th}>Route</th>
                <th style={styles.th}>Departure</th>
                <th style={styles.th}>Arrival</th>
                <th style={styles.th}>Duration</th>
                <th style={styles.th}>Price</th>
                <th style={styles.th}>Seats</th>
                <th style={styles.th}>Action</th>
              </tr>
            </thead>
            <tbody>
              {flights.map((flight) => {
                const departure = new Date(flight.departureTime).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
                const arrival = new Date(flight.arrivalTime).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
                const duration = "2h 15m"; // Simplified
                return (
                  <tr key={flight.id} style={styles.tableRow}>
                    <td style={styles.td}>{flight.id}</td>
                    <td style={styles.td}>
                      <div style={styles.airline}>{flight.airlineName}</div>
                    </td>
                    <td style={styles.td}>
                      <div style={styles.route}>
                        <div style={styles.source}>{flight.source}</div>
                        <div style={styles.arrow}>→</div>
                        <div style={styles.dest}>{flight.destination}</div>
                      </div>
                    </td>
                    <td style={styles.td}>{departure}</td>
                    <td style={styles.td}>{arrival}</td>
                    <td style={styles.td}>{duration}</td>
                    <td style={styles.td}>
                      <div style={styles.price}>₹{flight.price.toLocaleString()}</div>
                    </td>
                    <td style={styles.td}>
                      <span style={styles.seats}>{flight.totalSeats}</span>
                    </td>
                    <td style={styles.td}>
                      <Link to="/booking" state={{flight}} style={styles.bookBtn}>
                        Book Now
                      </Link>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      ) : (
        <div style={styles.emptyState}>
          <div style={styles.emptyIcon}>✈️</div>
          <h3>No flights found</h3>
          <p>Try adjusting your search criteria</p>
          <button onClick={clearFilters} style={styles.showAllBtn}>Show All Flights</button>
        </div>
      )}
    </div>
  );
}

const styles = {
  container: {
    maxWidth: '1400px',
    margin: '0 auto',
    padding: '30px 20px',
    background: '#f8f9fa',
    minHeight: '100vh'
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '30px',
    paddingBottom: '20px',
    borderBottom: '3px solid #007bff'
  },
  title: {
    fontSize: '2.2rem',
    fontWeight: '700',
    color: '#1a1a1a',
    margin: 0
  },
  filterCount: {
    fontSize: '1.1rem',
    color: '#6c757d',
    fontWeight: '500'
  },
  filters: {
    background: 'white',
    padding: '25px',
    borderRadius: '12px',
    boxShadow: '0 4px 20px rgba(0,0,0,0.08)',
    marginBottom: '30px'
  },
  filterRow: {
    display: 'flex',
    gap: '15px',
    flexWrap: 'wrap',
    marginBottom: '20px'
  },
  input: {
    flex: 1,
    padding: '12px 16px',
    border: '2px solid #e9ecef',
    borderRadius: '8px',
    fontSize: '15px',
    minWidth: '160px',
    transition: 'border-color 0.2s',
    background: '#fff'
  },
  clearBtn: {
    background: '#6c757d',
    color: 'white',
    border: 'none',
    padding: '12px 24px',
    borderRadius: '8px',
    fontSize: '15px',
    fontWeight: '600',
    cursor: 'pointer',
    boxShadow: '0 2px 8px rgba(108,117,125,0.3)'
  },
  tableContainer: {
    background: 'white',
    borderRadius: '12px',
    boxShadow: '0 8px 30px rgba(0,0,0,0.12)',
    overflow: 'hidden'
  },
  table: {
    width: '100%',
    borderCollapse: 'collapse'
  },
  tableHead: {
    background: 'linear-gradient(135deg, #007bff, #0056b3)'
  },
  th: {
    padding: '18px 12px',
    textAlign: 'left',
    color: 'white',
    fontWeight: '600',
    fontSize: '14px',
    textTransform: 'uppercase',
    letterSpacing: '0.5px'
  },
  tableRow: {
    borderBottom: '1px solid #f1f3f4',
    transition: 'background 0.2s',
    cursor: 'pointer'
  },
  td: {
    padding: '20px 12px',
    verticalAlign: 'middle'
  },
  airline: {
    fontWeight: '600',
    fontSize: '15px',
    color: '#1a1a1a'
  },
  route: {
    display: 'flex',
    alignItems: 'center',
    gap: '12px',
    fontSize: '14px'
  },
  source: {
    fontWeight: '700',
    color: '#007bff'
  },
  dest: {
    fontWeight: '500',
    color: '#1a1a1a'
  },
  arrow: {
    fontSize: '18px',
    color: '#6c757d',
    fontWeight: 'bold'
  },
  price: {
    fontSize: '16px',
    fontWeight: '800',
    color: '#28a745'
  },
  seats: {
    background: '#e3f2fd',
    color: '#1976d2',
    padding: '4px 10px',
    borderRadius: '12px',
    fontWeight: '600',
    fontSize: '13px'
  },
  bookBtn: {
    background: 'linear-gradient(45deg, #28a745, #20c997)',
    color: 'white',
    padding: '12px 20px',
    borderRadius: '25px',
    textDecoration: 'none',
    fontWeight: '700',
    fontSize: '14px',
    display: 'inline-block',
    boxShadow: '0 4px 15px rgba(40,167,69,0.4)',
    transition: 'all 0.3s ease'
  },
  emptyState: {
    textAlign: 'center',
    padding: '80px 40px',
    background: 'white',
    borderRadius: '16px',
    boxShadow: '0 8px 30px rgba(0,0,0,0.12)'
  },
  emptyIcon: {
    fontSize: '4rem',
    marginBottom: '20px',
    opacity: 0.5
  },
  showAllBtn: {
    background: '#007bff',
    color: 'white',
    border: 'none',
    padding: '14px 28px',
    borderRadius: '8px',
    fontSize: '16px',
    fontWeight: '600',
    cursor: 'pointer',
    boxShadow: '0 4px 15px rgba(0,123,255,0.4)'
  }
};

export default SearchLayout;
