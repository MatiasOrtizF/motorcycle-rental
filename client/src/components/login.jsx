import { useState } from "react";
import LoginService from "../services/LoginService";

export default function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const login = (e) => {
        e.preventDefault();
        const userData = {email, password}
        LoginService.login(userData).then((response)=> {
            if(response.data != "FAIL") {
                localStorage.token = response.data.token;
                localStorage.userId = response.data.user.id;
                localStorage.userName = response.data.user.name;
                localStorage.userLastName = response.data.user.lastName;
                localStorage.userEmail = email;
                window.location.href = '/';
                console.log(response.data.user);
            }else {
                alert("Password or email is incorrect");
            }
        }).catch(error=> {
            console.log(error);
        })
    }

    return (
        <main className="container-login">
            <div className="img-login">
                <img src="https://imgs.search.brave.com/KeB3aC8wirj-GKvkPIoCbyC-G4KDQ8MJl6P1VoH_vUk/rs:fit:860:0:0/g:ce/aHR0cHM6Ly9wc21m/aXJlc3Rvcm0uYmxv/Yi5jb3JlLndpbmRv/d3MubmV0LzU4Mjcz/YzVhLTM2MzQtNGNi/Zi1iZmIxLTllZTM2/MGUzODk5MS9yZW50/YWxzMi5qcGc"></img>
            </div>
            <div className="form">
                <h1>sign in</h1>
                <form action="/">
                    <label form="email">Email</label>
                    <input onChange={(e)=> setEmail(e.target.value)} type="email" placeholder="Example@email.com" required></input>

                    <label form="password">Pasword</label>
                    <input onChange={(e)=> setPassword(e.target.value)} type="password" placeholder="Enter password" required></input>
                    
                    <button onClick={(e)=> login(e)}>Submit</button>
                </form>
            </div>
        </main>
    )
}