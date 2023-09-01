import axios from "axios";

const MOTORCYCLE_BASE = "http://localhost:8080/motorcycle";

class MotorcycleService {
    getAllMotorcycle() {
        return axios.get(MOTORCYCLE_BASE);
    }
    getMotorcycle(motorcycleId, token) {
        const config = {
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json'
            }
        };
        return axios.get(MOTORCYCLE_BASE + '/' + motorcycleId, config);
    }
}

export default new MotorcycleService();
