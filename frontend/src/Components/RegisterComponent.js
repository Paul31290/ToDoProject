import { useState } from "react";
import {useNavigate, Link } from 'react-router-dom'
import AuthService from "../AuthService";

const RegisterComponent = () => {
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        try{
            const response = await AuthService.register({userName, password});
            setMessage(response.data);
            if (response.data === 'User is registered in the application') {
                navigate('/login');
            }
        } catch (error) {
            setMessage('Registration failed');
        }
    };

    return (
        <div>
            <div>
                <div>
                    <div>
                        <div>Registration</div>
                        <div>
                            {message && <div>{message}</div>}
                            <form onSubmit={handleRegister}>
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
                                <button type="submit">Register</button>
                            </form>
                            <div>
                                <span> Already registered? <Link to="/login"> Login here.</Link></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RegisterComponent();