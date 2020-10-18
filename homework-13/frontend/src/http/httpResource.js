import axios from 'axios'

const instance = axios.create({
  // baseURL: process.env.VUE_APP_RESOURCE_SERVER,
  withCredentials: true,
  timeout: 10000
})

const requestHandler = request => {
  console.log("Request Interceptor", request);
  return request;
};

const errorHandler = error => {
  console.log("Error Interceptor", error);
  if (error.response) {
    if (error.response.status === 401) {
      performLogout();
    }
  }
  return Promise.reject({...error});
};

const successHandler = response => {
  console.log("Response Interceptor", response);
  return response;
};

instance.interceptors.request.use(request => requestHandler(request));

instance.interceptors.response.use(
  response => successHandler(response),
  error => errorHandler(error)
);

export default instance;
