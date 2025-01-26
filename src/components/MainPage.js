import React, { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import Navigation from "./Navigation"
import FeaturedMovie from "./FeaturedMovie"
import MovieCarousel from "./MovieCarousel"
import MovieModal from "./MovieModal"
import "./MainPage.css"

function MainPage() {
  const [movies, setMovies] = useState([])
  const [watchedMovies, setWatchedMovies] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [selectedMovie, setSelectedMovie] = useState(null)
  const [showModal, setShowModal] = useState(false)
  const navigate = useNavigate()

  useEffect(() => {
    const fetchAllMovies = async () => {
      try {
        const token = localStorage.getItem("token")
        const user_id = localStorage.getItem("user_id")
        if (!token) {
          navigate("/login")
          return
        }

        const moviesResponse = await fetch("http://localhost:5000/api/movies", {
          headers: {
            user_id: user_id,
            token: token,
          },
        })
        if (!moviesResponse.ok) {
          throw new Error("Failed to fetch movies")
        }
        const moviesData = await moviesResponse.json()

        // Get the watched movies IDs
        const watchedMovieIds = moviesData.find((category) => category.category_name === "movies watched")?.movies || []

        // If there are watched movies, fetch their details
        if (watchedMovieIds.length > 0) {
          const watchedMoviesPromises = watchedMovieIds.map((id) =>
            fetch(`http://localhost:5000/api/movies/${id}`, {
              headers: {
                user_id: token,
              },
            }).then((response) => response.json()),
          )

          const watchedMoviesData = await Promise.all(watchedMoviesPromises)
          setWatchedMovies(watchedMoviesData.flat())
        }

        // Filter out the "movies watched" category from the main movies array
        const filteredMovies = moviesData.filter((category) => category.category_name !== "movies watched")

        setMovies(filteredMovies)
        setLoading(false)
      } catch (err) {
        console.error("Error fetching data:", err)
        setError(err.message)
        setLoading(false)
      }
    }

    fetchAllMovies()
  }, [navigate])
  const handleMovieClick = (movie) => {
    setSelectedMovie(movie)
    setShowModal(true)
  }
  if (loading) {
    return <div className="loading">Loading...</div>
  }

  if (error) {
    return <div className="error">{error}</div>
  }

  // Select a random movie for the featured section from all movies
  const allMovies = movies.reduce((acc, category) => {
    if (category.movies && Array.isArray(category.movies)) {
      return [...acc, ...category.movies]
    }
    return acc
  }, [])

  const randomMovie = allMovies[Math.floor(Math.random() * allMovies.length)]

  return (
    <div className="main-page">
      <Navigation />

      {randomMovie && <FeaturedMovie movie={randomMovie} onPlay={() => handleMovieClick(randomMovie)} />}

      <div className="categories-container">
        {/* Display Continue Watching section if there are watched movies */}
        {watchedMovies.length > 0 && (
          <div className="category-section">
            <MovieCarousel title="Continue Watching" movies={watchedMovies} onMovieClick={handleMovieClick} />
          </div>
        )}

        {/* Display other categories */}
        {movies.map((category, index) => (
          <div key={index} className="category-section">
            <MovieCarousel title={category.category_name} movies={category.movies} onMovieClick={handleMovieClick} />
          </div>
        ))}
      </div>
      <MovieModal movie={selectedMovie} show={showModal} onHide={() => setShowModal(false)} />
    </div>
  )
}

export default MainPage

