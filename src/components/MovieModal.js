import React, { useState, useEffect } from "react"
import { X, Play } from "lucide-react"
import MovieCarousel from "./MovieCarousel"
import "./MovieModal.css"

function MovieModal({ movie, onClose }) {
  const [similarMovies, setSimilarMovies] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    document.body.style.overflow = "hidden"
    return () => {
      document.body.style.overflow = "unset"
    }
  }, [])

  useEffect(() => {
    const fetchSimilarMovies = async () => {
      try {
        const token = localStorage.getItem("token")
        const user_id = localStorage.getItem("user_id")
        const recommendResponse = await fetch(`http://localhost:5000/api/movies/${movie._id}/recommend`, {
          headers: {
            "Content-Type": "application/json",
            user_id: user_id,
            token: token,
          },
        })

        if (!recommendResponse.ok) {
          throw new Error("Failed to fetch recommendations")
        }

        const recommendData = await recommendResponse.json()
        const recommendedIds = recommendData.Recommendations || []

        if (recommendedIds.length === 0) {
          setSimilarMovies([])
          setLoading(false)
          return
        }

        // Then fetch the full details for each recommended movie
        const movieDetailsPromises = recommendedIds.map((movieId) =>
          fetch(`http://localhost:5000/api/movies/${movieId}`, {
            headers: {
              user_id: user_id,
              token: token,
            },
          }).then((res) => res.json()),
        )

        const moviesDetails = await Promise.all(movieDetailsPromises)
        setSimilarMovies(moviesDetails)
      } catch (error) {
        console.error("Error fetching similar movies:", error)
        setSimilarMovies([])
      } finally {
        setLoading(false)
      }
    }

    if (movie?._id) {
      fetchSimilarMovies()
    }
  }, [movie?._id])

  if (!movie) return null

  const handleContentClick = (e) => {
    e.stopPropagation() // Prevent clicks inside content from closing modal
  }
  // Handle mouse wheel scrolling
  const handleWheel = (e) => {
    e.stopPropagation()
  }
  return (
    <div className="movie-modal" onClick={onClose} onWheel={handleWheel}>
      <div className="modal-backdrop" />
      <div className="modal-content" onClick={(e) => e.stopPropagation()} onWheel={handleWheel}>
        <button className="modal-close" onClick={onClose}>
          <X className="close-icon" />
        </button>

        <div className="modal-hero">
          <img src="https://occ-0-5095-2774.1.nflxso.net/dnm/api/v6/Qs00mKCpRvrkl3HZAN5KwEL1kpE/AAAABfNLhB5R6EWSDcKOW4Z8EWmCFN8Cgd30pFcUs8DEuklzhAM4Obbv4qJMslWves17Uuah10sIovt71GmPcfTw7c-hJC9NDjbKFp0.webp?r=dd4" alt={movie.name} className="modal-backdrop-image" />
          <div className="modal-hero-content">
            <h1 className="modal-title">{movie.name}</h1>
            <div className="modal-metadata">
              <span className="modal-year">{new Date(movie.releaseDate).getFullYear()}</span>
              <span className="modal-duration">
                {Math.floor(movie.duration / 60)}h {movie.duration % 60}m
              </span>
              <span className="modal-rating">Rating: {movie.rating}</span>
            </div>
            <div className="modal-buttons">
              <button className="btn-play">
                <Play className="btn-icon" />
                Play
              </button>
            </div>
          </div>
        </div>

        <div className="modal-details">
          <div className="modal-description">
            <p>{movie.description || "No description available."}</p>
          </div>
          <div className="modal-info-grid">
            <div className="modal-info-item">
              <span className="info-label">Cast:</span>
              <span className="info-value">{movie.starring.join(", ")}</span>
            </div>
            <div className="modal-info-item">
              <span className="info-label">Genre:</span>
              <span className="info-value">{movie.category}</span>
            </div>
          </div>
        </div>

        {!loading && similarMovies.length > 0 && (
          <div className="modal-similar">
            <MovieCarousel
              title="More Like This"
              movies={similarMovies}
              onMovieClick={(newMovie) => {
                // Update the current movie and fetch new recommendations
                window.history.pushState({}, "", `/browse/${newMovie._id}`)
                // You might want to implement a callback to the parent to update the current movie
              }}
            />
          </div>
        )}
      </div>
    </div>
  )
}

export default MovieModal