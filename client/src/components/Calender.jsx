import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import RentalService from "../services/RentalService";
import MotorcycleService from "../services/MotorcycleService";

export default function Calender() {
    // const actualYear = 2023;
    // const locale = 'es' // Spanish

    // const months = [...Array(12).keys()]
    // const intl =  new Intl.DateTimeFormat(locale, {month: 'long'})

    // const calender = months.map(monthKey=> {
    //     const monthName = intl.format(new Date(actualYear, monthKey))

    //     const daysOftMonth = new Date(actualYear, 1, 0).getDate()

    //     return {
    //         monthName,
    //         daysOftMonth: 31,
    //         startsOn: 0
    //     } 
    // })
    // console.log(calender)

    const [motorcycle, setMotorcycle] = useState([]);
    const [dateRental, setDateRental] = useState('');
    const [dateReturn, setDateReturn] = useState('');
    const [totalPrice, setTotalPrice] = useState(0);
    const [userId, setUserId] = useState(localStorage.userId);
    const [motorcycleId, setMotorcycleId] = useState(15);
    const {id} = useParams();

    useEffect(()=> {
        MotorcycleService.getMotorcycle(id,localStorage.token).then((response)=> {
            setMotorcycleId(response.data.id);
            setMotorcycle(response.data);
            setTotalPrice(response.data.price);
        }).catch(error=> {
            console.log(error);
        })
    },[])

    useEffect(()=> {
        distanceDateRental(dateRental,dateReturn);
    },[dateRental,dateReturn])

    const addRental = (e) => {
        const config = {
            headers: {
                'Authorization': localStorage.token
            }
        }
        e.preventDefault();
        const rentalData = {dateRental, dateReturn, totalPrice, user:{ id: userId }, motorcycle:{ id: motorcycleId }}
        // console.log(rentalData);
        RentalService.addRental(rentalData, config).then((response)=> {
            console.log(response.data);
        }).catch(error=> {
            console.log(error);
        })
    }

    const distanceDateRental = (startDate,endDate) => {
        if(startDate.trim() && endDate.trim()) {
            const start = new Date(startDate);
            const end = new Date(endDate);
    
            const miliSecondsPerDay = 24 * 60 * 60 * 1000; // milisegundos en un dia
    
            const difMiliseconds = Math.abs(end - start);
            let days = Math.floor(difMiliseconds / miliSecondsPerDay)
    
            setTotalPrice(motorcycle.price*days);
            console.log(days);
        }
    }


    return (
        <>
            <div className="calender">
                <h1>Rental</h1>
                <h2>{motorcycle.motorcycleName}</h2>
                <img src={motorcycle.image} alt={"img-"+motorcycle.name}/>
                <p>total price: ${totalPrice}</p>
                <div>
                    <label for="start">Start date:</label>
                    <input 
                        type="date" 
                        id="start" 
                        name="trip-start" 
                        min="2023-08-23" //cambiar para que el min sea el dia actual.
                        onChange={(e)=> setDateRental((e.target.value))}
                    />
                </div>
                <div>
                    <label for="end">End date:</label>
                    <input 
                        type="date" 
                        id="start" 
                        name="trip-start" 
                        min="2023-08-23" //cambiar para que el min sea el dia actual y tambien sea mayor al Start value.
                        onChange={(e)=> setDateReturn((e.target.value))}
                    />
                </div>
                
                <div className="buttons">
                    <Link to='/'>
                        <button>Cancel</button>
                    </Link>
                    <button onClick={(e)=> addRental(e)}>Alquilar</button>
                </div>
                
                {/* <ol>
                    <li className="day-name">Lunes</li>
                    <li className="day-name">Martes</li>
                    <li className="day-name">Miercoles</li>
                    <li className="day-name">Jueves</li>
                    <li className="day-name">Viernes</li>
                    <li className="day-name">Sabado</li>
                    <li className="day-name">Lunes</li>
                    <li class="first-day">1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                    <li>8</li>
                    <li>9</li>
                    <li>10</li>
                    <li>11</li>
                    <li>12</li>
                    <li>13</li>
                    <li>14</li>
                    <li>15</li>
                    <li>16</li>
                    <li>17</li>
                    <li>18</li>
                    <li>19</li>
                    <li>20</li>
                    <li>21</li>
                    <li>22</li>
                    <li>23</li>
                    <li>24</li>
                    <li>25</li>
                    <li>26</li>
                    <li>27</li>
                    <li>28</li>
                    <li>29</li>
                    <li>30</li>
                    <li>31</li>
                </ol> */}
            </div>
        </>
    )
}