import axios from "axios";

// Create an axios instance
const api = axios.create({
  baseURL: "http://localhost:8080", // ✅ backend base URL
});

// Add interceptor to attach token automatically
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});


// Response interceptor → handle expired token
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && (error.response.status === 401 || error.response.status === 403)) {
      
      localStorage.removeItem("token");

      
      window.location.href = "/";
    }
    return Promise.reject(error);
  }
);

export default api;
