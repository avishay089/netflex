import React, { useState, useEffect } from "react"
import { Link, useNavigate, useLocation } from "react-router-dom"
import "./Login.css"

function Login() {
  const [username, setUserName] = useState("")
  const [password, setPassword] = useState("")
  const [loginError, setLoginError] = useState("")
  const [successMessage, setSuccessMessage] = useState("")
  const navigate = useNavigate()
  const location = useLocation()

  useEffect(() => {
    if (location.state?.message) {
      setSuccessMessage(location.state.message)
    }
  }, [location.state])

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoginError("")

    try {
      const response = await fetch("http://localhost:5000/api/tokens", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      })

      if (response.ok) {
        const data = await response.json()
        // Store the token in localStorage or in a state management solution
        localStorage.setItem("token", data.token)
        localStorage.setItem("user_id", data.userId)
        // Redirect to the main page or dashboard
        navigate("/browse")
      } else {
        const errorData = await response.json()
        setLoginError(errorData.message || "Login failed. Please try again.")
      }
    } catch (error) {
      console.error("Login error:", error)
      setLoginError("An error occurred. Please try again later.")
    }
  }

  return (
    <div className="min-vh-100 position-relative">
      <div className="position-absolute top-0 start-0 w-100 h-100">
        <img
          src="https://hebbkx1anhila5yf.public.blob.vercel-storage.com/background.jpg-cnsrxkylOlgoxIUEeV3t9nLeLKHCa6.jpeg"
          alt="Netflix Background"
          className="w-100 h-100 object-fit-cover"
          style={{ filter: "brightness(35%)" }}
        />
      </div>

      <div className="position-relative">
        <header className="p-4">
          <Link to="/" className="text-danger text-decoration-none">
            <div className="fs-1 fw-bold">NETFLIX</div>
          </Link>
        </header>

        <div className="container">
          <div className="row justify-content-center">
            <div className="col-md-6 col-lg-4">
              <div className="card bg-black bg-opacity-75 text-white p-4">
                <div className="card-body">
                  <h1 className="card-title mb-4">Sign In</h1>
                  {successMessage && (
                    <div className="alert alert-success" role="alert">
                      {successMessage}
                    </div>
                  )}
                  {loginError && (
                    <div className="alert alert-danger" role="alert">
                      {loginError}
                    </div>
                  )}
                  <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                      <input
                        type="text"
                        className="form-control form-control-lg bg-dark text-white"
                        placeholder="User Name"
                        value={username}
                        onChange={(e) => setUserName(e.target.value)}
                        required
                      />
                    </div>
                    <div className="mb-3">
                      <input
                        type="password"
                        className="form-control form-control-lg bg-dark text-white"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                      />
                    </div>
                    <button type="submit" className="btn btn-danger btn-lg w-100 mb-3">
                      Sign In
                    </button>

                    <div className="d-flex justify-content-between align-items-center mb-4">
                      <div className="form-check">
                        <input type="checkbox" className="form-check-input" id="remember" />
                        <label className="form-check-label" htmlFor="remember">
                          Remember me
                        </label>
                      </div>
                      <a href="#" className="text-white-50 text-decoration-none">
                        Forgot password?
                      </a>
                    </div>

                    <div className="text-white-50">
                      <p>
                        New to Netflix?{" "}
                        <Link to="/signup" className="text-white text-decoration-none">
                          Sign up now.
                        </Link>
                      </p>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Login

