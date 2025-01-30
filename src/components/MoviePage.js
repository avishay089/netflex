import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react";
import "./MoviePage.css";

function MoviePage() {
  const { id } = useParams();
  const [movie, setMovie] = useState(null);
  const [showInfo, setShowInfo] = useState(true);
  const timeoutRef = useRef(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchMovie = async () => {
      try {
        const token = localStorage.getItem("token")
        if (!token) {
          navigate("/login")
          return
        }
        const response = await fetch(`http://localhost:5000/api/movies/${id}`);
        const movieData = await response.json();
        setMovie(movieData);

        await fetch(`http://localhost:5000/api/movies/${id}/recommend`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            user_id: localStorage.getItem("user_id"),
          }
        });
      } catch (error) {
        console.error("Failed to fetch movie data:", error);
      }
    };
    fetchMovie();
  }, [id]);

  const handleMouseMove = () => {
    setShowInfo(true);
    clearTimeout(timeoutRef.current);
    timeoutRef.current = setTimeout(() => setShowInfo(false), 3000);
  };

  if (!movie) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="video-frame" onMouseMove={handleMouseMove}>
      <div className="movie-info-overlay">
        <h1 className="movie-title-video">{movie.name}</h1>
        <p className="movie-duration-video">Duration: {movie.duration}</p>
        <p className="movie-category-video">Category: {movie.category}</p>
        <p className="movie-rating-video">Rating: {movie.rating}</p>
      </div>
      <video className="fullscreen-video" controls autoPlay>
        <source src={movie.videoUrl || 'http://localhost:5000/halel.mp4'} type="video/mp4" />
        Your browser does not support the video tag.
      </video>
    </div>
  );
}

export default MoviePage;