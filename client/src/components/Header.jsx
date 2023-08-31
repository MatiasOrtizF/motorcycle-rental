import { useState } from "react";
import { Link, json } from "react-router-dom";

export default function Header({changeFilters}) {
    const [minPrice, setMinPrice] = useState(0)

    const hadleChangeMinPrice = (event) => {
        setMinPrice(event.target.value)
        changeFilters(prevState=> ({
            ...prevState,
            minPrice: event.target.value
        }))
    }

    const handleChangeGps = (event) => {
        let booleanValue = "all";
        if(event.target.value != "all") {
            booleanValue = JSON.parse(event.target.value)
        }
        changeFilters(prevState=> ({
            ...prevState,
            gps: booleanValue
        }))
    }

    return (
        <header>
            <h1>Alquiler de Motos</h1>
            <select id="gps" onChange={handleChangeGps}>
                <option value="all">All</option>
                <option value="true">GPS</option>
                <option value="false">No GPS</option>
            </select>
            <label for="price">Precio a partir de: </label>
            <input type="range" id="volumen" min="0" max="205" step="1" onChange={hadleChangeMinPrice}/>
            <span>{minPrice}</span>
            <Link to='/login'>
                <button>Sing in</button>
            </Link>
        </header>
    )
}