import { useState } from "react";
import {useNavigate, Link } from 'react-router-dom'
import { Card } from "primereact/card";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { Divider } from "primereact/divider";
import { Button } from "primereact/button";
import 'primeicons/primeicons.css';

const RegisterComponent = () => {
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [clicked, setClicked] = useState(false);
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        setClicked(true);
        setError('');

        try{
            const response = await fetch(`http://localhost:8082/api/register?username=${userName}&password=${password}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ userName, password })
            });

            if (response.ok){
                navigate('/login')

            }
        } catch (err) {
            setError(err.message);
            console.log(err);

        } finally {
            setClicked(false);
        }
    };

    return (
        <Card title="To Do Application" subTitle="Registration Page" className="p-card">
            <Card className="p-card-content">
                <form onSubmit={handleRegister}>
                    <div>
                        <div className="flex flex-column gap-2">
                            <label htmlFor="username">Username</label>
                            <InputText 
                                id="username" 
                                aria-describedby="username-help" 
                                value={userName}
                                onChange={(e) => setUserName(e.target.value)}
                            />
                            <small id="username-help">
                                Enter your username to register.
                            </small>
                        </div>
                    </div>
                    <div>
                        <label htmlFor="password">Password</label>
                        <Password 
                            id="password"
                            value={password} 
                            onChange={(e) => setPassword(e.target.value)} 
                            toggleMask />
                    </div>
                    <Button
                        rounded
                        icon="pi pi-user"
                    >
                    {clicked ? 'Creating Account' : 'Sign Up'}
                    </Button>
                </form>

                <div className="error-message">{error}</div>
                <Divider/>
                <div>
                    <span> Already registered? <Link to="/login"> Login here.</Link></span>
                </div>
            </Card>
        </Card>
    );
};

export default RegisterComponent;