import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import './App.css';
import { lazy, Suspense } from 'react';

function App() {

  const Login = lazy(() => import('./Components/LoginComponent'));
  const Register = lazy(() => import('./Components/RegisterComponent'));
  const ToDo = lazy(() => import('./Components/ToDoComponent'));
  const UpdateToDo = lazy(() => import('./Components/UpdateToDoComponent'));

  return (
    <Router>
      <div>
        <Suspense fallback={<div>Loading...</div>}>
          <Routes>
            <Route path='/' element={<Login/>} />
            <Route path='/login' element={<Login/>} />
            <Route path='/register' element={<Register/>} />
            <Route path='/home' element={<ToDo/>}/>
            <Route path='/update/:todoId/' element={<UpdateToDo/>}/>
          </Routes>
        </Suspense>
      </div>
    </Router>
  );
}

export default App;
