import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api/transfers",
  headers: {
    "Content-Type": "application/json",
  },
});

export default {
  getStatement() {
    return apiClient.get("");
  },

  schedule(transferData) {
    return apiClient.post("", transferData);
  },
};
