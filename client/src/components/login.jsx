
export default function Login() {
    return (
        <main className="container-login">
            <img src="https://imgs.search.brave.com/KeB3aC8wirj-GKvkPIoCbyC-G4KDQ8MJl6P1VoH_vUk/rs:fit:860:0:0/g:ce/aHR0cHM6Ly9wc21m/aXJlc3Rvcm0uYmxv/Yi5jb3JlLndpbmRv/d3MubmV0LzU4Mjcz/YzVhLTM2MzQtNGNi/Zi1iZmIxLTllZTM2/MGUzODk5MS9yZW50/YWxzMi5qcGc"></img>
            <div className="form">
                <h1>sign in</h1>
                <form action="/">
                    <label form="email">Email</label>
                    <input type="email" placeholder="Example@email.com" required></input>

                    <label form="password">Pasword</label>
                    <input type="password" placeholder="Enter password" required></input>
                    
                    <input className="btn-login" type="submit" value="Submit"></input>
                </form>
            </div>
        </main>
    )
}