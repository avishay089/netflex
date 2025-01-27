import React, { useState } from "react"
import { Play, Info } from "lucide-react"
import { Button } from "react-bootstrap"
import { useNavigate } from "react-router-dom"
import MovieModal from "./MovieModal"
import "./FeaturedMovie.css"

function FeaturedMovie({ movie, onMovieClick }) {
  const [showModal, setShowModal] = useState(false)
  const navigate = useNavigate()
  const handleMoreInfo = () => {
    if (onMovieClick) {
      onMovieClick(movie)
    } else {
      setShowModal(true)
    }
  }
  const handlePlay = () => {
    navigate(`/movie/${movie._id}`)
  }

  return (
    <div className="featured-movie">
      <div className="featured-content">
        <img src={movie.imageUrl || "https://occ-0-5095-2774.1.nflxso.net/dnm/api/v6/Qs00mKCpRvrkl3HZAN5KwEL1kpE/AAAABfNLhB5R6EWSDcKOW4Z8EWmCFN8Cgd30pFcUs8DEuklzhAM4Obbv4qJMslWves17Uuah10sIovt71GmPcfTw7c-hJC9NDjbKFp0.webp?r=dd4"} alt={movie.name} className="featured-backdrop" />
        <div className="featured-details">
          <h1 className="featured-title">{movie.name}</h1>
          <div className="featured-info">
            <span className="rating">Rating: {movie.rating}</span>
            <span className="duration">
              {Math.floor(movie.duration / 60)}h {movie.duration % 60}m
            </span>
          </div>
          <div className="featured-starring">Starring: {movie.starring.join(", ")}</div>
          <div className="featured-buttons">
            <Button variant="light" className="play-button" onClick={handlePlay}>
              <Play size={20} /> Play
            </Button>
            <Button variant="secondary" className="info-button" onClick={handleMoreInfo}>
              <Info size={20} /> More Info
            </Button>
          </div>
        </div>
      </div>
      {!onMovieClick && <MovieModal movie={movie} show={showModal} onHide={() => setShowModal(false)} />}
    </div>
  )
}

export default FeaturedMovie