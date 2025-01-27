import React, { useState } from "react"
import MovieSearch from "./MovieSearch"
import MovieForm from "./MovieForm"
import "./AdminMovieManager.css"

function AdminMovieManager() {
  const [selectedMovie, setSelectedMovie] = useState(null)
  const [showResults, setShowResults] = useState(true)

  const handleMovieSelect = (movie) => {
    setSelectedMovie(movie)
    setShowResults(false)
  }
  const handleSearch = (hasResults) => {
    setShowResults(hasResults)
  }
  const handleMovieUpdate = () => {
    // Refresh the search results
    setSelectedMovie(null)
  }

  return (
    <div className="admin-movie-manager">
      <h2>Movie Manager</h2>
      <div className="manager-content">
        <MovieSearch onMovieSelect={handleMovieSelect} showResults={showResults} onSearchResults={handleSearch} />
        <MovieForm movie={selectedMovie} onUpdate={handleMovieUpdate} onCancel={() => setSelectedMovie(null)} />
      </div>
    </div>
  )
}

export default AdminMovieManager

