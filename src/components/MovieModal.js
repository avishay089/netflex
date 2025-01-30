import React, { useState, useEffect } from "react"
import { Modal, Button, Row, Col } from "react-bootstrap"
import { Play } from "lucide-react"
import { useNavigate } from "react-router-dom"
import MovieCarousel from "./MovieCarousel"
import "./MovieModal.css"

function MovieModal({ movie, show, onHide }) {
  const [similarMovies, setSimilarMovies] = useState([])
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()

  useEffect(() => {
    const fetchSimilarMovies = async () => {
      if (!movie) return

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

    if (show && movie) {
      fetchSimilarMovies()
    }
  }, [movie, show])

  const handlePlay = () => {
    onHide()
    navigate(`/movies/${movie._id}`)
  }

  if (!movie) return null

  return (
    <Modal show={show} onHide={onHide} size="xl" centered>
      <Modal.Header closeButton>
        <Modal.Title>{movie.name}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div className="movie-modal-content">
          <div className="movie-hero mb-4">
            <img
              src={movie.imageUrl || "https://occ-0-5095-2774.1.nflxso.net/dnm/api/v6/Qs00mKCpRvrkl3HZAN5KwEL1kpE/AAAABfNLhB5R6EWSDcKOW4Z8EWmCFN8Cgd30pFcUs8DEuklzhAM4Obbv4qJMslWves17Uuah10sIovt71GmPcfTw7c-hJC9NDjbKFp0.webp?r=dd4"}
              alt={movie.name}
              className="img-fluid w-100"
              style={{ height: "400px", objectFit: "cover" }}
            />
            <div className="movie-hero-overlay">
              <h2 className="movie-title-modal">{movie.name}</h2>
              <div className="movie-metadata mb-3">
                <span className="me-3">{new Date(movie.releaseDate).getFullYear()}</span>
                <span className="me-3">
                  {Math.floor(movie.duration / 60)}h {movie.duration % 60}m
                </span>
                <span>Rating: {movie.rating}</span>
              </div>
              <div className="movie-buttons">
                <Button variant="light" className="me-2" onClick={handlePlay}>
                  <Play size={20} /> Play
                </Button>
              </div>
            </div>
          </div>

          <Row className="mb-4">
            <Col>
              <h3>Description</h3>
              <p>{movie.description || "No description available."}</p>
            </Col>
          </Row>

          <Row className="mb-4">
            <Col md={6}>
              <h3>Cast</h3>
              <p>{movie.starring.join(", ")}</p>
            </Col>
            <Col md={6}>
              <h3>Genre</h3>
              <p>{movie.category}</p>
            </Col>
          </Row>

          {!loading && similarMovies.length > 0 && (
            <div className="similar-movies">
              <h3 className="mb-3">More Like This</h3>
              <MovieCarousel
                movies={similarMovies}
                onMovieClick={(newMovie) => {
                  onHide()
                  navigate(`/movies/${newMovie._id}`)
                }}
              />
            </div>
          )}
        </div>
      </Modal.Body>
    </Modal>
  )
}

export default MovieModal