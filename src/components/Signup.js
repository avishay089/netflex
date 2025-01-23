import React, { useState, useEffect } from "react"
import { Link, useLocation, useNavigate } from "react-router-dom"
import "./Signup.css"

function Signup() {
  const location = useLocation()
  const navigate = useNavigate()
  const [formData, setFormData] = useState({
    email: "",
    fullName: "",
    id: "",
    password: "",
    passwordVerify: "",
    userName: "",
    phone: "",
    profilePicture: null,
  })
  const [previewImage, setPreviewImage] = useState(null)
  const [passwordError, setPasswordError] = useState("")
  const [idError, setIdError] = useState("")
  const [signupError, setSignupError] = useState("")

  useEffect(() => {
    if (location.state?.email) {
      setFormData((prev) => ({ ...prev, email: location.state.email }))
    }
  }, [location.state?.email])

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }))

    if (name === "passwordVerify" || name === "password") {
      if (name === "password" && formData.passwordVerify && value !== formData.passwordVerify) {
        setPasswordError("Passwords do not match")
      } else if (name === "passwordVerify" && value !== formData.password) {
        setPasswordError("Passwords do not match")
      } else {
        setPasswordError("")
      }
    }

  if (name === "password") {
      validatePassword(formData.password)
    }
    if (name === "id") {
      validateId(value)
    }
  }

  const validatePassword = (password, passwordVerify) => {
    if (password.length < 6) {
      setPasswordError("Password must be at least 6 characters long")
    } else if (passwordVerify && password !== passwordVerify) {
      setPasswordError("Passwords do not match")
    } else {
      setPasswordError("")
    }
  }

  const validateId = (id) => {
    if (id.length !== 9 || !/^\d+$/.test(id)) {
      setIdError("ID must be exactly 9 digits")
    } else {
      setIdError("")
    }
  }

  const handleImageChange = (e) => {
    const file = e.target.files[0]
    if (file) {
      setFormData((prev) => ({
        ...prev,
        profilePicture: file,
      }))
      const reader = new FileReader()
      reader.onloadend = () => {
        setPreviewImage(reader.result)
      }
      reader.readAsDataURL(file)
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setSignupError("")

    try {
      const response = await fetch("http://localhost:5000/api/users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: formData.email,
          name: formData.fullName,
          tz: formData.id,
          password: formData.password,
          username: formData.userName,
          // Note: You might need to handle profile picture upload separately
        }),
      })
      console.log(response);
      if (response.ok) {
        // Signup successful, redirect to login page
        navigate("/login", { state: { message: "Signup successful. Please log in." } })
      } else {
        const errorData = await response.json()
        setSignupError(errorData.message || "Signup failed. Please try again.")
      }
    } catch (error) {
      console.error("Signup error:", error)
      setSignupError("An error occurred. Please try again later.")
    }
  }

  return (
    <div className="signup-container">
      <div className="background-image">
        <img
          src="https://hebbkx1anhila5yf.public.blob.vercel-storage.com/background.jpg-cnsrxkylOlgoxIUEeV3t9nLeLKHCa6.jpeg"
          alt="Netflix Background"
          className="bg-image"
        />
      </div>
      <div className="content-wrapper">
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
                  <h1 className="card-title mb-4">Sign Up</h1>
                  {signupError && (
                    <div className="alert alert-danger" role="alert">
                      {signupError}
                    </div>
                  )}
                  <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                      <input
                        type="email"
                        className="form-control form-control-lg bg-dark text-white"
                        placeholder="Email"
                        name="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    <div className="mb-3">
                      <input
                        type="text"
                        className="form-control form-control-lg bg-dark text-white"
                        placeholder="ID Number (9 digits)"
                        name="id"
                        value={formData.id}
                        onChange={handleInputChange}
                        required
                      />
                      {idError && <div className="text-danger">{idError}</div>}
                    </div>
                    <div className="mb-3">
                      <input
                        type="text"
                        className="form-control form-control-lg bg-dark text-white"
                        placeholder="Full Name"
                        name="fullName"
                        value={formData.fullName}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    <div className="mb-3">
                      <input
                        type="text"
                        className="form-control form-control-lg bg-dark text-white"
                        placeholder="User Name"
                        name="userName"
                        value={formData.userName}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    <div className="mb-3">
                      <input
                        type="password"
                        className="form-control form-control-lg bg-dark text-white"
                        placeholder="Password"
                        name="password"
                        value={formData.password}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    <div className="mb-3">
                      <input
                        type="password"
                        className="form-control form-control-lg bg-dark text-white"
                        placeholder="Verify Password"
                        name="passwordVerify"
                        value={formData.passwordVerify}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    {passwordError && <div className="text-danger mb-3">{passwordError}</div>}
                    <div className="mb-3">
                      <input
                        type="tel"
                        className="form-control form-control-lg bg-dark text-white"
                        placeholder="Phone Number (Optional)"
                        name="phone"
                        value={formData.phone}
                        onChange={handleInputChange}
                      />
                    </div>
                    <div className="mb-4">
                      <label className="form-label">Profile Picture</label>
                      <div className="d-flex gap-3 align-items-center">
                        <div className="profile-picture-preview">
                          {previewImage ? (
                            <img
                              src={previewImage || "/placeholder.svg"}
                              alt="Profile Preview"
                              className="preview-image"
                            />
                          ) : (
                            <div className="preview-placeholder">
                              <i className="bi bi-person-fill"></i>
                            </div>
                          )}
                        </div>
                        <input
                          type="file"
                          className="form-control form-control-lg bg-dark text-white"
                          accept="image/*"
                          onChange={handleImageChange}
                        />
                      </div>
                    </div>
                    <button type="submit" className="btn btn-danger btn-lg w-100 mb-3" disabled={!!passwordError}>
                      Sign Up
                    </button>
                    <div className="text-white-50">
                      <p>
                        Already have an account?{" "}
                        <Link to="/login" className="text-white text-decoration-none">
                          Sign in now.
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

export default Signup