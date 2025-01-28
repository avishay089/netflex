import React, { useRef } from "react"
import { ChevronLeft, ChevronRight } from "lucide-react"
import "./MovieCarousel.css"

function MovieCarousel({ title, movies, onMovieClick }) {
  const sliderRef = useRef(null)

  const scroll = (direction) => {
    if (sliderRef.current) {
      const scrollAmount = direction === "left" ? -400 : 400
      sliderRef.current.scrollBy({ left: scrollAmount, behavior: "smooth" })
    }
  }
  const handleMovieClick = (movie) => {
    if (typeof onMovieClick === "function") {
      onMovieClick(movie)
    }
    else 
    {
      console.log("Movie clicked: ", movie)
    }
  }
  return (
    <div className="movie-carousel">
      <h2 className="category-title">{title}</h2>
      <div className="carousel-container">
        <button className="carousel-arrow carousel-arrow-left" onClick={() => scroll("left")} aria-label="Scroll left">
          <ChevronLeft className="arrow-icon" />
        </button>

        <div className="movies-slider" ref={sliderRef}>
        {movies.map((movie) => (
          <div key={movie._id} className="movie-card" onClick={() => handleMovieClick(movie)}
          role="button"
          tabIndex={0}>
            <img src={movie.imageUrl || "https://occ-0-5095-2774.1.nflxso.net/dnm/api/v6/Qs00mKCpRvrkl3HZAN5KwEL1kpE/AAAABfNLhB5R6EWSDcKOW4Z8EWmCFN8Cgd30pFcUs8DEuklzhAM4Obbv4qJMslWves17Uuah10sIovt71GmPcfTw7c-hJC9NDjbKFp0.webp?r=dd4"} alt={movie.name} className="movie-poster" />
            <div className="movie-info">
              <h3 className="movie-title">{movie.name}</h3>
              <div className="movie-details">
                <span className="movie-rating">Rating: {movie.rating}</span>
                <span className="movie-duration">
                  {Math.floor(movie.duration / 60)}h {movie.duration % 60}m
                </span>
              </div>
              <div className="movie-starring">Starring: {movie.starring.join(", ")}</div>
            </div>
          </div>
        ))}
        </div>
        
        <button
          className="carousel-arrow carousel-arrow-right"
          onClick={() => scroll("right")}
          aria-label="Scroll right"
        >
          <ChevronRight className="arrow-icon" />
        </button>
      </div>
    </div>
  )
}

export default MovieCarousel