import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: '',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Response interceptor — unwrap data
axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    const message =
      error?.response?.data?.msg ||
      error?.response?.data?.message ||
      'Something went wrong';
    return Promise.reject({ message, raw: error });
  }
);

export default axiosInstance;
