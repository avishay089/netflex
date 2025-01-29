// import logo from './logo.svg';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import Home from "./components/Home";
import Login from "./components/Login";
import Signup from "./components/Signup"
import MainPage from "./components/MainPage"
import MyListPage from "./components/MyListPage"
import CategoryPage from "./components/CategoryPage"
import AdminPage from "./components/AdminPage"
import MoviePage from "./components/MoviePage"
import "bootstrap-icons/font/bootstrap-icons.css"
import "bootstrap/dist/css/bootstrap.min.css"
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
        <Route path="/admin" element={<AdminPage />} />
        <Route path="/movies/:id" element={<MoviePage />} />
      </Routes>
    </Router>
  );
}

export default App;
