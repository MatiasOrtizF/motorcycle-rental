import axios from "axios";

const MOTORCYCLE_BASE = "http://localhost:8080/motorcycle";

class MotorcycleService {
    getAllMotorcycle() {
        return axios.get(MOTORCYCLE_BASE);
    }
    getMotorcycle(motorcycleId) {
        return axios.get(MOTORCYCLE_BASE + '/' + motorcycleId);
    }
}

export default new MotorcycleService();
