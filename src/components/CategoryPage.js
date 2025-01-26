import React, { useState, useEffect } from "react"
import { useParams, useNavigate } from "react-router-dom"
import Navigation from "./Navigation"
import FeaturedMovie from "./FeaturedMovie"
import MovieCarousel from "./MovieCarousel"
import "./CategoryPage.css"

function CategoryPage() {
  const [movies, setMovies] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const { category } = useParams()
  const navigate = useNavigate()

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const token = localStorage.getItem("token")
        const user_id = localStorage.getItem("user_id")
        if (!token) {
          navigate("/login")
          return
        }

        const response = await fetch("http://localhost:5000/api/movies", {
          headers: {
            user_id: user_id,
            token: token,
          },
        })

        if (!response.ok) {
          throw new Error("Failed to fetch movies")
        }

        const data = await response.json()
        // Find the category's movies
        const categoryData = data.find((cat) => cat.category_name === category)
        if (categoryData) {
          setMovies(categoryData.movies)
        } else {
          throw new Error("Category not found")
        }
        setLoading(false)
      } catch (err) {
        setError(err.message)
        setLoading(false)
      }
    }

    fetchMovies()
  }, [category, navigate])

  if (loading) {
    return <div className="loading">Loading...</div>
  }

  if (error) {
    return <div className="error">{error}</div>
  }

  // Select a random movie for the featured section
  const randomMovie = movies[Math.floor(Math.random() * movies.length)]

  return (
    <div className="category-page">
      <Navigation />

      {randomMovie && <FeaturedMovie movie={randomMovie} />}

      <div className="movies-container">
        <h1 className="category-header">{category} Movies</h1>
        <MovieCarousel title="All Movies" movies={movies} />
      </div>
    </div>
  )
}

export default CategoryPage

