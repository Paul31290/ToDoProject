import { useState } from "react";
import {useNavigate, Link } from 'react-router-dom'
import AuthService from "../AuthService";

const RegisterComponent = () => {
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try{
            const response = await AuthService.login({userName, password});
            setMessage(response.data);
            if (response.data === 'Login is succesful') {
                navigate('/todo');
            }
        } catch (error) {
            setMessage('Invalid credentials');
        }
    };

    return (
        <div>
            <div>
                <div>
                    <div>
                        <div>Login</div>
                        <div>
                            {message && <div>{message}</div>}
                            <form onSubmit={handleLogin}>
                                <div>
                                    <label>Username</label>
                                    <input
                                        type="text"
                                        value={userName}
                                        onChange={(e) => setUserName(e.target.value)}
                                    />
                                </div>
                                <div>
                                    <label>Password</label>
                                    <input
                                        type="password"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                    />
                                </div>
                                <button type="submit">Login</button>
                            </form>
                            <div>
                                <span> Not registered? <Link to="/register"> Register here.</Link></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RegisterComponent();