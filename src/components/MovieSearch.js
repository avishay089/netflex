import React, { useState } from "react"
import "./MovieSearch.css"

function MovieSearch({ onMovieSelect, showResults, onSearchResults }) {
  const [searchQuery, setSearchQuery] = useState("")
  const [searchResults, setSearchResults] = useState([])

  const handleSearch = async (e) => {
    e.preventDefault()
    const token = localStorage.getItem("token")
    try {
      const response = await fetch(`http://localhost:5000/api/movies/search/${encodeURIComponent(searchQuery)}`, {
        headers: {
          user_id: localStorage.getItem("user_id"),
            token,
        },
      })
      if (response.ok) {
        const data = await response.json()
        setSearchResults(data)
        onSearchResults(data.length > 0)
      }
    } catch (error) {
      console.error("Error searching movies:", error)
      setSearchResults([])
      onSearchResults(false)
    }
  }

  return (
    <div className="movie-search">
      <form onSubmit={handleSearch}>
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="Search for a movie"
        />
        <button type="submit">Search</button>
      </form>
      {showResults && searchResults.length > 0 && (
        <div className="search-results">
          {searchResults.map((movie) => (
            <div key={movie._id} className="search-result-item" onClick={() => onMovieSelect(movie)}>
              <h3>{movie.name}</h3>
              <div className="search-result-details">
                <span>{movie.category}</span>
                <span>Rating: {movie.rating}</span>
                <span>{movie.duration} min</span>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

export default MovieSearch