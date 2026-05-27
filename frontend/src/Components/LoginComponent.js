import { useState } from "react";
import {useNavigate, Link } from 'react-router-dom';
import { Card } from 'primereact/card';
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import 'primeicons/primeicons.css';


const LoginComponent = () => {
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [clicked, setClicked] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setClicked(true);
        setError("")
        try {
            const response = await fetch(`http://localhost:8082/api/login?username=${userName}&password=${password}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ userName, password })
            })
            if (!response.ok) {
                setError(error.message);
            } else {
                 const data = await response.json();

                localStorage.setItem('user', JSON.stringify(data));
                navigate('/home');
            }
        } catch (error) {
            setError(error.message)
        } finally {
        setClicked(false);
        };
    }

    return (
        <Card title="To Do Application" subTitle="Login Page" className="p-card">
            <Card className="p-card-content">
                <form onSubmit={handleLogin}>
                    <div>
                        <span className="p-float-label">
                            <InputText 
                                id="username" 
                                value={userName} 
                                onChange={(e) => setUserName(e.target.value)} 
                                className="p-username"/>
                            <label htmlFor="username">Username</label>
                        </span>
                        <span className="p-float-label">
                            <Password 
                                id="password"
                                value={password} 
                                onChange={(e) => setPassword(e.target.value)}
                                toggleMask
                                feedback={false}
                                />
                            <label htmlFor="password">Password</label>
                        </span>
                    </div>
                    <Button
                        rounded
                        icon="pi pi-signin"
                        className="signin"
                    >
                        {clicked ? 'Loging In.' : 'Login'}
                    </Button>
                </form>

                <div className="error-message">{error}</div>
                <Divider/>
                <div>
                    <span> Not registered? <Link to="/register"> Register here.</Link></span>
                </div>
            </Card>
        </Card>
    );
};

export default LoginComponent;