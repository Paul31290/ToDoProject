import logo from './logo.svg';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import './App.css';
import LoginComponent from './Components/LoginComponent';
import RegisterComponent from './Components/RegisterComponent';
import ToDoComponent from './Components/ToDoComponent';

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route path='/' element={<LoginComponent/>} />
          <Route path='/login' element={<LoginComponent/>} />
          <Route path='/register' element={<RegisterComponent/>} />
          <Route path='/home' element={<ToDoComponent/>}/>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
