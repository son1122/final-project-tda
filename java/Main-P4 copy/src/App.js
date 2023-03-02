import logo from './logo.svg';
import './App.css';
import Navbar from "./component/nav";
import Body from "./component/Body";
import {Route, Routes, useNavigate} from "react-router-dom";
import NotFound from "./component/NotFound/NotFound";
import MainApp from "./component/MainApp";
import Signup from "./component/Signup/Signup";
import Login from "./component/Login/Login";
import {useEffect, useState} from "react";
import axios from "axios";

function App() {



    const [login, setLogin] = useState(true);

    const navigate = useNavigate();

    useEffect(() => {


    }, [login]);
    return (
        <div className="App">

            <Routes>
                <Route path="/" element={<MainApp login={login}/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/signup" element={<Signup/>}/>
                {/*<Route path="/" element={<MainApp/>} />*/}
                <Route path="*" element={<NotFound/>}/>
            </Routes>
            {/*<Body/>*/}
            {/*<Navbar/>*/}
        </div>
  );
}

export default App;
