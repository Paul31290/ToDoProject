import { Card } from "primereact/card";
import React from "react";
import { useState, useEffect  } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from 'primereact/button';
import NotLoggedInComponent from "./NotLoggedInComponent";
import { Checkbox } from "primereact/checkbox";
import { InputText } from "primereact/inputtext";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import 'primeicons/primeicons.css';


const ToDoComponent = () => {
    const navigate = useNavigate();

    const user = JSON.parse(localStorage.getItem('user'));
    const userId = user?.userId;

    const [todos, setTodos] = useState([]);
    const [error, setError] = useState('');

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [done, setDone] = useState(false);

    const [clicked, setClicked] = useState(false);

    useEffect(() => {
        if (!userId) {
            return;
        }
        getToDoList();
    }, [userId]);

    const getToDoList = async () => {
        try {
            const response = await fetch(`http://localhost:8082/api/todos/user/${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            if (!response.ok){
                setError(error);
            }
            const data = await response.json();
            setTodos(data);
        } catch (err) {
            console.log(err);
        }
    }

    const addToDo = async (e) => {
        e.preventDefault();
        setClicked(true);
        try {
            const response = await fetch(`http://localhost:8082/api/todos/user/${userId}?title=${title}&description=${description}`, {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json' 
                },
                body: JSON.stringify({ title, description, done}),
            });
            if (!response.ok){
                setError(error);
            }
            setTitle('');
            setDescription('');
            setDone(false)
        } catch (err) {
            console.error(err);
        } finally {
            setClicked(false);
            getToDoList();
        }
    };

    const deleteToDo = async (todoId) => {
        try {
            const response = await fetch(`http://localhost:8082/api/todos/${todoId}`, {
                method: 'DELETE',
                headers: { 
                    'Content-Type': 'application/json' 
                },
            });
            if (!response.ok){
                setError(error);
            }
            getToDoList();
            setDone(true)
        } catch (err) {
            console.error(err);
        }
    };

    const checkToDo = async (todoId) =>{
        try {
            const response = await fetch(`http://localhost:8082/api/todos/${todoId}/checked`, {
                method: 'PUT',
                headers: { 
                    'Content-Type': 'application/json' 
                },
            });
            if (!response.ok){
                setError(error);
            }
            const data = await response.json();
            if(!data.done){
                setDone(false);
            } else {
                setDone(true);
            }   
        } catch (err) {
            console.error(err);
        }
    }

    const updateToDo = (todoId) => {
        console.log(todoId);
		navigate(`/update/${todoId}`);
    };

    const logout = () => {
		localStorage.removeItem('user');
		navigate("/");
    };

    const completeBody = (todo) => {
        return <Checkbox 
            onChange={
            () => checkToDo(todo.todoId)} 
            checked={done}
        >
        </Checkbox>
    }

    const editButtonBody = (todo) => {
        return <Button
            onClick={() => updateToDo(todo.todoId)}
            icon="pi pi-pencil"
            className="p-button-info"
            label="Edit"
        />
    }

    const removeButtonBody = (todo) => {
        return <Button
            onClick={() => deleteToDo(todo.todoId)}
            icon="pi pi-trash"
            className="p-button-danger"
            label="Remove"
        />
    }


    if (!user) {
        return (
            <NotLoggedInComponent/>
        );
    }

    return (
        <Card title="To Do Application" subTitle="To Do List" className="p-card">
            <Card className="p-card-content">
                <form
                    onSubmit={addToDo}
                >
                    <span className="p-float-label">
                        <InputText 
                            id="title" 
                            value={title} 
                            onChange={(e) => setTitle(e.target.value)}
                            required
                            disabled={clicked} 
                            className="p-title"/>
                        <label htmlFor="title">Title</label>
                    </span>
                    <span className="p-float-label">
                        <InputText 
                            id="description" 
                            value={description} 
                            onChange={(e) => setDescription(e.target.value)}
                            disabled={clicked} 
                            className="p-description"/>
                        <label htmlFor="description">Description</label>
                    </span>
                    <Button
                        rounded
                        icon="pi pi-plus-circle"
                        className="adding-button"
                        disabled={clicked}
                        > {clicked ? 'Adding': 'Add Task'}
                    </Button>

                </form>
                <Card className="table-view">
                    <DataTable value={todos} paginator rows={10} dataKey="todoId" emptyMessage="No todos found.">
                        <Column field="title" header="Title" style={{ minWidth: '12rem' }} />
                        <Column field="description" header="description" style={{ minWidth: '12rem' }}/>
                        <Column header="Complete" dataType="boolean" style={{ minWidth: '1rem' }} body={completeBody} />
                        <Column field="edit" header="Edit" style={{ minWidth: '12rem' }} body={editButtonBody} />
                        <Column field="remove" header="Remove" style={{ minWidth: '6rem' }} body={removeButtonBody} />
                    </DataTable>
                </Card>
            </Card>
            <Button
                rounded
                onClick={() => logout()}
                icon="pi pi-sign-out" 
                className="p-button-danger"
                label="Logout"
            />
        </Card>
    );
};

export default ToDoComponent;