import { useParams } from "react-router-dom";
import { useEffect, useState, useRef } from "react";
import "./MoviePage.css";

function MoviePage() {
  const { id } = useParams();
  const [movie, setMovie] = useState(null);
  const [showInfo, setShowInfo] = useState(true);
  const timeoutRef = useRef(null);

  useEffect(() => {
    const fetchMovie = async () => {
      try {
        const response = await fetch(`http://localhost:5000/api/movies/${id}`);
        const movieData = await response.json();
        setMovie(movieData);
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
        <h1 className="movie-title">{movie.name}</h1>
        <p className="movie-duration">Duration: {movie.duration}</p>
        <p className="movie-category">Category: {movie.category}</p>
        <p className="movie-rating">Rating: {movie.rating}</p>
      </div>
      <video className="fullscreen-video" controls autoPlay>
        <source src={movie.videoUrl} type="video/mp4" />
        Your browser does not support the video tag.
      </video>
    </div>
  );
}

export default MoviePage;