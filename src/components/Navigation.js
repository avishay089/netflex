import React, { useState, useEffect } from "react"
import { Link, useNavigate } from "react-router-dom"
import "./Navigation.css"
import SearchResults from "./SearchResults"
import "bootstrap/dist/js/bootstrap.bundle.min.js"

function Navigation() {
  const [showSearch, setShowSearch] = useState(false)
  const [searchQuery, setSearchQuery] = useState("")
  const [searchResults, setSearchResults] = useState([])
  const [categories, setCategories] = useState([])
  const [showCategories, setShowCategories] = useState(false)
  const [isSearching, setIsSearching] = useState(false)
  const navigate = useNavigate()

  const userName = localStorage.getItem("userName") || "User"
  const userImage = localStorage.getItem("userImage") || "https://wallpapers.com/images/high/netflix-profile-pictures-1000-x-1000-qo9h82134t9nv0j0.webp"

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const token = localStorage.getItem("token")
        const user_id = localStorage.getItem("user_id")
        const response = await fetch("http://localhost:5000/api/categories", {
          headers: {
            user_id: user_id,
            token: token,
          },
        })

        if (response.ok) {
          const data = await response.json()
          setCategories(Array.isArray(data) ? data : [])
        }
      } catch (error) {
        console.error("Error fetching categories:", error)
        setCategories([])
      }
    }

    fetchCategories()
  }, [])
  // Handle search input with debouncing
  useEffect(() => {
    const searchMovies = async () => {
      if (searchQuery.trim().length === 0) {
        setSearchResults([])
        return
      }

      setIsSearching(true)
      try {
        const token = localStorage.getItem("token")
        const user_id = localStorage.getItem("user_id")
        const response = await fetch(`http://localhost:5000/api/movies/search/${encodeURIComponent(searchQuery)}`, {
          headers: {
            user_id: user_id,
            token: token,
          },
        })

        if (response.ok) {
          const data = await response.json()
          setSearchResults(Array.isArray(data) && data.length > 0 ? data : [])
        } else {
          setSearchResults([])
        }
      } catch (error) {
        console.error("Error searching movies:", error)
        setSearchResults([])
      } finally {
        setIsSearching(false)
      }
    }

    const timeoutId = setTimeout(() => {
      if (searchQuery) {
        searchMovies()
      }
    }, 300) // Debounce time of 300ms

    return () => clearTimeout(timeoutId)
  }, [searchQuery])

  const handleSearchInputChange = (e) => {
    setSearchQuery(e.target.value)
    if (!showSearch) {
      setShowSearch(true)
    }
  }

  const handleSearchClose = () => {
    setShowSearch(false)
    setSearchQuery(" ")
    setSearchResults([])
  }
  const toggleCategories = () => {
    setShowCategories(!showCategories)
  }

  return (
    <nav className="netflix-nav">
      <div className="nav-left">
        <Link to="/" className="netflix-logo">
          NETFLEX
        </Link>
        <div className="nav-links">
          <Link to="/browse" className="nav-link active">
            Home
          </Link>
          <div className="dropdown">
            <button className="nav-link dropdown-toggle" onClick={toggleCategories} aria-expanded={showCategories}>
              Categories
            </button>
            <ul className={`dropdown-menu ${showCategories ? "show" : ""}`}>
              {categories && categories.length > 0 ? (
                categories.map((category) => (
                  <li key={category._id}>
                    <Link
                      to={`/category/${category.name}`}
                      className="dropdown-item"
                      onClick={() => setShowCategories(false)}
                    >
                      {category.name}
                    </Link>
                  </li>
                ))
              ) : (
                <li>
                  <span className="dropdown-item">No categories available</span>
                </li>
              )}
            </ul>
          </div>
          <Link to="/my-list" className="nav-link">
            My List
          </Link>
          {localStorage.getItem("isAdmin") === "true" && (
            <Link to="/admin" className="nav-link">
              Admin
            </Link>
          )}
        </div>
      </div>

      <div className="nav-right">
        <div className={`search-container ${showSearch ? "active" : ""}`}>
          <button className="search-button" onClick={() => setShowSearch(!showSearch)}>
            <i className="bi bi-search"></i>
          </button>
          {showSearch && (
            <>
              <div className="search-input-wrapper">
                <input
                  type="text"
                  placeholder="Titles, people, genres"
                  value={searchQuery}
                  onChange={handleSearchInputChange}
                  className="search-input"
                />
                {isSearching && <div className="search-loading">Searching...</div>}
              </div>
              {searchResults.length > 0 && <SearchResults results={searchResults} onClose={handleSearchClose} />}
            </>
          )}
        </div>

        <div className="user-profile dropdown">
          <button className="dropdown-toggle" data-bs-toggle="dropdown">
            <img src={userImage || "//userImage.webp"} alt={userName} className="profile-image" />
          </button>
          <ul className="dropdown-menu dropdown-menu-end">
            <li>
              <span className="dropdown-item-text">{userName}</span>
            </li>
            <li>
              <hr className="dropdown-divider" />
            </li>
            <li>
              <Link to="/profile" className="dropdown-item">
                Profile
              </Link>
            </li>
            <li>
              <Link to="/settings" className="dropdown-item">
                Settings
              </Link>
            </li>
            <li>
              <hr className="dropdown-divider" />
            </li>
            <li>
              <button
                className="dropdown-item"
                onClick={() => {
                  localStorage.removeItem("token")
                  window.location.href = "/login"
                }}
              >
                Sign Out
              </button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  )
}

export default Navigation