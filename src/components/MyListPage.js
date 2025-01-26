import React, { useState, useEffect, use } from "react"
import { useNavigate } from "react-router-dom"
import Navigation from "./Navigation"
import FeaturedMovie from "./FeaturedMovie"
import MovieCarousel from "./MovieCarousel"
import "./MyListPage.css"

function MyListPage() {
  const [watchedMovies, setWatchedMovies] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const navigate = useNavigate()

  useEffect(() => {
    const fetchWatchedMovies = async () => {
      try {
        const token = localStorage.getItem("token")
        const user_id = localStorage.getItem("user_id")
        if (!token) {
          navigate("/login")
          return
        }

        // Fetch all movies to get the "movies watched" category
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
                user_id: user_id,
                token: token,
              },
            }).then((response) => response.json()),
          )

          const watchedMoviesData = await Promise.all(watchedMoviesPromises)
          setWatchedMovies(watchedMoviesData.flat())
        }

        setLoading(false)
      } catch (err) {
        console.error("Error fetching watched movies:", err)
        setError(err.message)
        setLoading(false)
      }
    }

    fetchWatchedMovies()
  }, [navigate])

  if (loading) {
    return <div className="loading">Loading...</div>
  }

  if (error) {
    return <div className="error">{error}</div>
  }

  // Select a random movie for the featured section from watched movies
  const randomMovie = watchedMovies[Math.floor(Math.random() * watchedMovies.length)]

  return (
    <div className="my-list-page">
      <Navigation />

      {randomMovie && <FeaturedMovie movie={randomMovie} />}

      <div className="movies-container">
        <h1 className="page-header">My List</h1>
        {watchedMovies.length > 0 ? (
          <MovieCarousel title="Watched Movies" movies={watchedMovies} />
        ) : (
          <div className="no-movies">
            <p>You haven't watched any movies yet. Start exploring!</p>
          </div>
        )}
      </div>
    </div>
  )
}

export default MyListPage

