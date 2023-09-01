import { useEffect, useState } from "react";
import Header from "./Header";
import MotorcycleService from "../services/MotorcycleService";
import { Link } from "react-router-dom";

export default function Rental({motorcycles , changeFilters}) {
    return (
        <>
            <Header changeFilters={changeFilters}></Header>
            <ol className="motorcycle-list">
                {motorcycles.map((motorcycle, index)=> (
                    <li key={index}>
                        <img src={motorcycle.image} alt={"img-"+motorcycle.name}/>
                        <hr/>
                        <div className='description'>
                            <h3>{motorcycle.motorcycleName}</h3>
                            <div className='price'>
                                <h4>${motorcycle.price}/day</h4>
                                <Link to={localStorage.token ? `/rental/${motorcycle.id}` : `/login`}>
                                    <button>Alquilar</button>
                                </Link>
                            </div>
                        </div>
                    </li>
                ))}
            </ol>
        </>
    )
}