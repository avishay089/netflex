import React from "react"
import { useNavigate } from "react-router-dom"
import "./SearchResults.css"

function SearchResults({ results, onClose }) {
  const navigate = useNavigate()

  if (!results || results.length === 0) {
    return null
  }

  return (
    <div className="search-results">
      <div className="search-results-content">
        {results.map((movie) => (
          <div key={movie._id} className="search-result-item" onClick={() => {               console.log(`Navigating to /movies/${movie._id}`); navigate(`/movies/${movie._id}`)}}>
            <img src="https://occ-0-5095-2774.1.nflxso.net/dnm/api/v6/Qs00mKCpRvrkl3HZAN5KwEL1kpE/AAAABfNLhB5R6EWSDcKOW4Z8EWmCFN8Cgd30pFcUs8DEuklzhAM4Obbv4qJMslWves17Uuah10sIovt71GmPcfTw7c-hJC9NDjbKFp0.webp?r=dd4" alt={movie.name} className="search-result-image" />
            <div className="search-result-info">
              <h3 className="search-result-title">{movie.name}</h3>
              <div className="search-result-details">
                <span className="search-result-category">{movie.category}</span>
                <span className="search-result-rating">Rating: {movie.rating}</span>
                <span className="search-result-duration">
                  {Math.floor(movie.duration / 60)}h {movie.duration % 60}m
                </span>
              </div>
              <div className="search-result-starring">Starring: {movie.starring.join(", ")}</div>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default SearchResults

