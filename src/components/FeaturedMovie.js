import React, { useState, useRef } from "react"
import { Play, Info, Volume2, VolumeX } from "lucide-react"
import { Button } from "react-bootstrap"
import { useNavigate } from "react-router-dom"
import MovieModal from "./MovieModal"
import "./FeaturedMovie.css"

function FeaturedMovie({ movie, onMovieClick }) {
  const [showModal, setShowModal] = useState(false)
  const [isMuted, setIsMuted] = useState(true)
  const videoRef = useRef(null)
  const navigate = useNavigate()
  const handleMoreInfo = () => {
    if (onMovieClick) {
      onMovieClick(movie)
    } else {
      setShowModal(true)
    }
  }
  const handlePlay = () => {
    navigate(`/movies/${movie._id}`)
  }
  const toggleMute = () => {
    if (videoRef.current) {
      videoRef.current.muted = !videoRef.current.muted
      setIsMuted(!isMuted)
    }
  }

  return (
    <div className="featured-movie">
      <div className="featured-content">
      {movie.videoUrl ? (
          <video ref={videoRef} className="featured-backdrop" autoPlay muted loop playsInline>
            <source src={movie.videoUrl} type="video/mp4" />
          </video>
        ) : (
          <video ref={videoRef} className="featured-backdrop" autoPlay muted loop playsInline>
            <source src={'http://localhost:5000/halel.mp4'} type="video/mp4" />
          </video>
        )}
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
            {movie.videoUrl && (
              <Button variant="secondary" className="volume-button" onClick={toggleMute}>
                {isMuted ? <VolumeX size={20} /> : <Volume2 size={20} />}
              </Button>
            )}
          </div>
        </div>
      </div>
      {!onMovieClick && <MovieModal movie={movie} show={showModal} onHide={() => setShowModal(false)} />}
    </div>
  )
}

export default FeaturedMovie