import React, { useState, useEffect } from "react"
import "./MovieForm.css"

function MovieForm({ movie, onUpdate, onCancel }) {
  const [formData, setFormData] = useState({
    name: "",
    category: "",
    starring: "",
    rating: "",
    duration: "",
  })

  useEffect(() => {
    if (movie) {
      setFormData({
        name: movie.name,
        category: movie.category,
        starring: movie.starring.join(", "),
        rating: movie.rating,
        duration: movie.duration,
      })
    } else {
      setFormData({
        name: "",
        category: "",
        starring: "",
        rating: "",
        duration: "",
      })
    }
  }, [movie])

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setFormData({ ...formData, [name]: value })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const token = localStorage.getItem("token")
    const movieData = {
      ...formData,
      starring: formData.starring.split(",").map((s) => s.trim()),
    }

    try {
      let response
      if (movie) {
        // Update existing movie
        response = await fetch(`http://localhost:5000/api/movies/${movie._id}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            user_id: localStorage.getItem("user_id"),
            token: token,
          },
          body: JSON.stringify(movieData),
        })
      } else {
        // Add new movie
        response = await fetch("http://localhost:5000/api/movies", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            user_id: token,
          },
          body: JSON.stringify(movieData),
        })
      }

      if (response.ok) {
        onUpdate()
        setFormData({
          name: "",
          category: "",
          starring: "",
          rating: "",
          duration: "",
        })
      }
    } catch (error) {
      console.error("Error saving movie:", error)
    }
  }

  const handleDelete = async () => {
    if (!movie) return

    const token = localStorage.getItem("token")
    try {
      const response = await fetch(`http://localhost:5000/api/movies/${movie._id}`, {
        method: "DELETE",
        headers: {
          user_id: token,
        },
      })

      if (response.ok) {
        onUpdate()
        setFormData({
          name: "",
          category: "",
          starring: "",
          rating: "",
          duration: "",
        })
      }
    } catch (error) {
      console.error("Error deleting movie:", error)
    }
  }

  return (
    <div className="movie-form-container">
      <h3>{movie ? "Edit Movie" : "Add New Movie"}</h3>
      <form onSubmit={handleSubmit} className="movie-form">
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleInputChange}
          placeholder="Movie Name"
          required
        />
        <input
          type="text"
          name="category"
          value={formData.category}
          onChange={handleInputChange}
          placeholder="Category"
          required
        />
        <input
          type="text"
          name="starring"
          value={formData.starring}
          onChange={handleInputChange}
          placeholder="Starring (comma-separated)"
          required
        />
        <input
          type="number"
          name="rating"
          value={formData.rating}
          onChange={handleInputChange}
          placeholder="Rating"
          step="0.1"
          min="0"
          max="10"
          required
        />
        <input
          type="number"
          name="duration"
          value={formData.duration}
          onChange={handleInputChange}
          placeholder="Duration (minutes)"
          required
        />
        <div className="form-buttons">
          <button type="submit" className="primary-button">
            {movie ? "Update Movie" : "Add Movie"}
          </button>
          {movie && (
            <button type="button" onClick={handleDelete} className="delete-button">
              Delete Movie
            </button>
          )}
          {movie && (
            <button type="button" onClick={onCancel} className="cancel-button">
              Cancel
            </button>
          )}
        </div>
      </form>
    </div>
  )
}

export default MovieForm

