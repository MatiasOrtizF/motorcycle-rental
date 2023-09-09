import { useEffect, useState } from "react"
import RentalService from "../services/RentalService";

export default function MyRentals() {
    const [myRentals, setMyRentals] = useState([]);

    useEffect(()=> {
        RentalService.getUserRental(localStorage.userId).then(response=> {
            console.log(response.data)
            setMyRentals(response.data);
        }).catch(error=> {
            console.log(error);
        })
    },[])

    return (
        <>
            <h1>My Rentals</h1>
            <ol className="motorcycle-list">
                {myRentals.map((myRental, index)=> (
                    <li key={index}>
                        <img src={myRental.motorcycle.image} alt="img"/>
                        <hr/>
                        <div className='description'>
                            <h3>{myRental.motorcycle.motorcycleName}</h3>
                            <h4>Total Price: <span className="total-price">${myRental.totalPrice}</span></h4>
                            <div className="rental-date">
                                <h4>Rental Day: {myRental.dateRental.slice(0,10)}</h4>
                                <h4>Return Day: {myRental.dateReturn.slice(0,10)}</h4>
                            </div>
                            <div className="buttons-rental">
                                <button>Cancel Rental</button>
                                <button>Change Rental</button>
                            </div>
                        </div>
                    </li>
                ))}
            </ol>
        </>
    )
}