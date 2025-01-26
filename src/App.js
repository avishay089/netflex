// import logo from './logo.svg';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import Home from "./components/Home";
import Login from "./components/Login";
import Signup from "./components/Signup"
import MainPage from "./components/MainPage"
import CategoryPage from "./components/CategoryPage"
import MyListPage from "./components/MyListPage"
import "bootstrap-icons/font/bootstrap-icons.css"
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/browse" element={<MainPage />} />
        <Route path="/category/:category" element={<CategoryPage />} />
        <Route path="/my-list" element={<MyListPage />} />
      </Routes>
    </Router>
  );
}

export default App;
