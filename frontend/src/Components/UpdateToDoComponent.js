import { Card } from "primereact/card";
import React from "react";
import { useState, useEffect} from "react";
import { useNavigate } from "react-router-dom";
import NotLoggedInComponent from "./NotLoggedInComponent";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import 'primeicons/primeicons.css';
import { useParams } from 'react-router-dom'

const UpdateToDoComponent = () => {
    const { todoId } = useParams();
    const navigate = useNavigate();
    
    const user = JSON.parse(localStorage.getItem('user'));
    const userId = user?.userId;

    const [todos, setTodos] = useState([]);
    const [error, setError] = useState('');

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');

    const [clicked, setClicked] = useState(false);
    const [success, SetSuccess] = useState(false);

    const getToDoList = async () => {

        try {
            const response = await fetch(`http://localhost:8082/api/todos/user/${userId}/todo/${todoId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            if (!response.ok){
                setError(error);
            }
            const data = await response.json();
            console.log(data)
            setTodos(data);
            setTitle(data.title);
            setDescription(data.description);
        } catch (err) {
            console.log(err);
        }
    }

    useEffect(() => {
        if (!userId) {
            return;
        }
        getToDoList();
    }, [userId]);

    const editTodo = async () => {
        SetSuccess(false);
        setClicked(true);
        try {
            const response = await fetch(`http://localhost:8082/api/todos/${todoId}`, {
                method: 'PUT',
                headers: { 
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    title,
                    description,
                })
            });
            if (!response.ok){
                SetSuccess(false);
                setError(error);
            }

            setTitle('');
            setDescription('');
            SetSuccess(true);
        } catch (err) {
            console.log(err);
        } finally {
            setClicked(false);
        }
    };

    const home = () =>{
        navigate('/home');
    }

    const isSuccess = () =>{
        if (success){
            return <span className="success-update">To Do updated succesfully!</span>;
        }
    }

    if (!user) {
        return (
            <NotLoggedInComponent/>
        );
    }

    return (
        <Card title="To Do Application" subTitle= "Update To Do"  className="p-card">
            <Card className="p-card-content">
                <form
                    onSubmit={editTodo}
                >
                    <span className="p-float-label">
                        <InputText 
                            id="title" 
                            onChange={(e) => setTitle(e.target.value)}
                            required
                            defaultValue={title}
                            disabled={clicked} 
                            className="p-title"/>
                        <label htmlFor="title">Title</label>
                    </span>
                    <span className="p-float-label">
                        <InputText 
                            id="description" 
                            onChange={(e) => setDescription(e.target.value)}
                            defaultValue={description}
                            disabled={clicked}
                            className="p-description"/>
                        <label htmlFor="description">Description</label>
                    </span>
                    <Button
                        rounded
                        icon="pi pi-pencil"
                        className="updatingS-button"
                        disabled={clicked}
                        > {clicked ? 'Updating' :'Update To Do'}
                    </Button>

                </form>
                <Button
                    rounded
                    onClick={() => home()} 
                    icon="pi pi-home"
                    className="p-button-danger"
                    label="Home Page"
                />
            </Card>
            <div> { isSuccess()} </div>
        </Card>
    );
}

export default UpdateToDoComponent;