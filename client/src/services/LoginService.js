import axios from "axios";

const RENTAL_BASE = "http://localhost:8080/login";

class LoginService {
    login(userData) {
        return axios.post(RENTAL_BASE, userData);
    }
}

export default new LoginService();