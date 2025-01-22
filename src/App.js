import logo from './logo.svg';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import Home from "./components/Home";
import Login from "./components/Login";
import Signup from "./components/Signup"
import "bootstrap-icons/font/bootstrap-icons.css"
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
      </Routes>
    </Router>
  );
}

export default App;
