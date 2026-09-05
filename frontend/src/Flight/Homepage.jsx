import { Link } from "react-router-dom";

function Homepage() {
  return (
    <div style={homepageStyles.container}>
      {/* Header */}
      <header style={homepageStyles.header}>
        <h2 style={homepageStyles.headerTitle}>Flight Booking System</h2>
      </header>

      {/* Hero Section */}
      <section style={homepageStyles.hero}>
        <div style={homepageStyles.heroContent}>
          <h1 style={homepageStyles.heroTitle}>
            Book Your Next Flight
            <span style={homepageStyles.highlight}> Easily</span>
          </h1>
          <p style={homepageStyles.heroSubtitle}>
            Find the best flights across 100+ airlines. 
            Fast, secure, and hassle-free booking.
          </p>
          <div style={homepageStyles.ctaButtons}>
            <Link to="/search" style={homepageStyles.primaryCTA}>
              ✈️ Search Flights Now
            </Link>
            <Link to="/details" style={homepageStyles.secondaryCTA}>
              📋 My Bookings
            </Link>
          </div>
        </div>
        <div style={homepageStyles.heroImage}>
          ✈️
        </div>
      </section>

      {/* Quick Stats */}
      <section style={homepageStyles.stats}>
        <div style={homepageStyles.statItem}>
          <div style={homepageStyles.statNumber}>100+</div>
          <div style={homepageStyles.statLabel}>Airlines</div>
        </div>
        <div style={homepageStyles.statItem}>
          <div style={homepageStyles.statNumber}>50K+</div>
          <div style={homepageStyles.statLabel}>Flights Daily</div>
        </div>
        <div style={homepageStyles.statItem}>
          <div style={homepageStyles.statNumber}>99.9%</div>
          <div style={homepageStyles.statLabel}>Uptime</div>
        </div>
      </section>
    </div>
  );
}

const homepageStyles = {
  container: {
    minHeight: '100vh',
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    background: 'linear-gradient(135deg, #1e3c72 0%, #2a5298 50%, #0f3460 100%)',
    color: 'white',
    padding: '0',
    position: 'relative',
    overflow: 'hidden'
  },
  
  // Header
  header: {
    background: 'rgba(255,255,255,0.1)',
    backdropFilter: 'blur(10px)',
    padding: '15px 20px',
    position: 'sticky',
    top: 0,
    zIndex: 100
  },
  headerTitle: {
    textAlign: 'center',
    fontSize: '1.8rem',
    margin: 0,
    fontWeight: '600',
    textShadow: '0 2px 4px rgba(0,0,0,0.3)'
  },

  // Hero Section
  hero: {
    minHeight: '85vh',
    display: 'flex',
    alignItems: 'center',
    maxWidth: '1400px',
    margin: '0 auto',
    padding: '0 40px',
    position: 'relative'
  },
  heroContent: {
    flex: 1,
    maxWidth: '650px',
    zIndex: 2
  },
  heroTitle: {
    fontSize: '3.2rem',
    fontWeight: '800',
    marginBottom: '20px',
    lineHeight: '1.1',
    letterSpacing: '-0.02em'
  },
  highlight: {
    background: 'linear-gradient(45deg, #FFD700, #FFA500)',
    WebkitBackgroundClip: 'text',
    WebkitTextFillColor: 'transparent',
    display: 'block',
    fontWeight: '900'
  },
  heroSubtitle: {
    fontSize: '1.25rem',
    marginBottom: '35px',
    opacity: 0.95,
    lineHeight: '1.6',
    maxWidth: '500px'
  },
  ctaButtons: {
    display: 'flex',
    gap: '15px',
    flexWrap: 'wrap',
    marginBottom: '20px'
  },
  primaryCTA: {
    display: 'inline-flex',
    alignItems: 'center',
    background: 'linear-gradient(45deg, #FFD700, #FFA500)',
    color: '#1a1a1a',
    padding: '16px 32px',
    fontSize: '1.15rem',
    fontWeight: '700',
    borderRadius: '50px',
    textDecoration: 'none',
    boxShadow: '0 8px 25px rgba(255,215,0,0.4)',
    transition: 'all 0.3s cubic-bezier(0.4, 0, 0.2, 1)',
    border: '2px solid transparent',
    position: 'relative',
    overflow: 'hidden'
  },
  secondaryCTA: {
    display: 'inline-flex',
    alignItems: 'center',
    background: 'rgba(255,255,255,0.2)',
    backdropFilter: 'blur(10px)',
    color: 'white',
    padding: '16px 32px',
    fontSize: '1.15rem',
    fontWeight: '600',
    borderRadius: '50px',
    textDecoration: 'none',
    border: '2px solid rgba(255,255,255,0.3)',
    transition: 'all 0.3s ease',
  
  },

  // Hero Image
  heroImage: {
    fontSize: '18rem',
    opacity: 0.15,
    flexShrink: 0,
    position: 'absolute',
    right: '10%',
    top: '50%',
    transform: 'translateY(-50%)',
    zIndex: 1
  },

  // Stats Section
  stats: {
    display: 'flex',
    justifyContent: 'center',
    gap: '60px',
    padding: '60px 40px',
    background: 'rgba(255,255,255,0.05)',
    backdropFilter: 'blur(20px)',
    marginTop: 'auto'
  },
  statItem: {
    textAlign: 'center'
  },
  statNumber: {
    fontSize: '2.8rem',
    fontWeight: '800',
    background: 'linear-gradient(45deg, #FFD700, #FFA500)',
    WebkitBackgroundClip: 'text',
    WebkitTextFillColor: 'transparent',
    marginBottom: '8px',
    letterSpacing: '-0.03em'
  },
  statLabel: {
    fontSize: '1rem',
    opacity: 0.9,
    fontWeight: '500',
    textTransform: 'uppercase',
    letterSpacing: '1px'
  }
};

export default Homepage;
