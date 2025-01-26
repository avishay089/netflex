import React from "react"
import "./FeaturedMovie.css"

function FeaturedMovie({ movie }) {
  return (
    <div className="featured-movie">
      <div className="featured-content">
        <img src="https://occ-0-5095-2774.1.nflxso.net/dnm/api/v6/Qs00mKCpRvrkl3HZAN5KwEL1kpE/AAAABfNLhB5R6EWSDcKOW4Z8EWmCFN8Cgd30pFcUs8DEuklzhAM4Obbv4qJMslWves17Uuah10sIovt71GmPcfTw7c-hJC9NDjbKFp0.webp?r=dd4" alt={movie.name} className="featured-backdrop" />
        {/* <img class="boxart-image boxart-image-in-padded-container" src="https://occ-0-5095-2774.1.nflxso.net/dnm/api/v6/Qs00mKCpRvrkl3HZAN5KwEL1kpE/AAAABfNLhB5R6EWSDcKOW4Z8EWmCFN8Cgd30pFcUs8DEuklzhAM4Obbv4qJMslWves17Uuah10sIovt71GmPcfTw7c-hJC9NDjbKFp0.webp?r=dd4" alt=""></img> */}
        <div className="featured-details">
          <h1 className="featured-title">{movie.name}</h1>
          <div className="featured-info">
            <span className="rating">Rating: {movie.rating}</span>
            <span className="duration">
              {Math.floor(movie.duration / 60)}h {movie.duration % 60}m
            </span>
          </div>
          <div className="featured-starring">Starring: {movie.starring.join(", ")}</div>
          <div className="featured-buttons">
            <button className="btn btn-light btn-lg play-button">
              <i className="bi bi-play-fill"></i> Play
            </button>
            <button className="btn btn-secondary btn-lg info-button">
              <i className="bi bi-info-circle"></i> More Info
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default FeaturedMovie