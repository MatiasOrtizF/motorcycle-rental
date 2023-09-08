import axios from "axios";

const RENTAL_BASE = "http://localhost:8080/rental";

class RentalService {
    addRental(rentalData) {
        return axios.post(RENTAL_BASE, rentalData);
    }
    getUserRental(userId) {
        return axios.get(RENTAL_BASE + "/" + userId);
    }
}

export default new RentalService();