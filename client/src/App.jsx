import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { useState , useEffect } from 'react'
import './app.css'
import Rental from './components/rental'
import Login from './components/login'
import Calender from './components/Calender'
import MotorcycleService from "./services/MotorcycleService";
import MyRentals from './components/MyRentals'

function App() {
  const [motorcycles, setMotorcycles] = useState([]);
  const [filters, setFilters] = useState({
    gps: 'all',
    minPrice: 0,
  })

  const filtersMotorcycles = (motorcycle) => {
    return motorcycle.filter(motorcycle=> {
      return(
        motorcycle.price >= filters.minPrice && 
        (
          filters.gps === 'all' ||
          motorcycle.gps === filters.gps
        )
      )
    })
  }

  useEffect(()=> {
    MotorcycleService.getAllMotorcycle().then(response=> {
        setMotorcycles(response.data);
    }).catch(error=> {
        console.log(error);
    })
  },[])

  const filteredMotorcycles = filtersMotorcycles(motorcycles)

  return (
    <BrowserRouter>
      <main>
        <Routes>
          <Route exact path="/" element={<Rental motorcycles={filteredMotorcycles} changeFilters={setFilters} />}></Route>
          <Route path='/login' element={<Login/>} ></Route>
          <Route path='/rental/:id' element={<Calender/>} ></Route>
          <Route path='/my-rentals' element={<MyRentals/>} ></Route>
        </Routes> 
      </main>  
    </BrowserRouter>
  )
}

export default App
