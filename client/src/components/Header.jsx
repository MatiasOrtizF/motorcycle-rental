import { useState } from "react";
import { Link, json } from "react-router-dom";

export default function Header({changeFilters}) {
    const [minPrice, setMinPrice] = useState(0)
    const [openMenu, setOpenMenu] = useState(false);

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

    const logOut = () => {
        localStorage.removeItem('token');
    }

    return (
        <header>
            <h1>Alquiler de Motos</h1>
            <div>
                <select id="gps" onChange={handleChangeGps}>
                    <option value="all">All</option>
                    <option value="true">GPS</option>
                    <option value="false">No GPS</option>
                </select>
            </div>
            <div className="filter-price">
                <label for="price">Precio a partir de: </label>
                <input type="range" id="price" min="0" max="205" step="1" onChange={hadleChangeMinPrice}/>
                <span>${minPrice}</span>
            </div>
            {localStorage.token ?
                <div className="user-controller">
                    <button onClick={()=>setOpenMenu(!openMenu)}>
                        <img src="src/assets/user-icon.png"/>
                    </button>
                    {openMenu &&
                        <ol>
                            <li>Matias Ortiz</li>
                            <li>{localStorage.email}</li>
                            <Link to='/my-rentals'>
                                <li><button>My Rentals</button></li>
                            </Link>
                            <Link to='login'>
                                <li><button onClick={logOut}>Log Out</button></li>
                            </Link>
                        </ol>
                    }
                </div>
                // <span>{localStorage.email}</span>
                    :
                <Link to='/login'>
                    <button className="sing-in">Sing in</button>
                </Link>
            }
        </header>
    )
}