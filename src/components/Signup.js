import React, { useState, useEffect } from "react"
import { Link, useLocation } from "react-router-dom"
import "./Signup.css"

function Signup() {
  const location = useLocation()
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    passwordVerify: "",
    displayName: "",
    phone: "",
    profilePicture: null,
  })
  const [previewImage, setPreviewImage] = useState(null)

  // Set email from homepage if provided
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
  }

  const handleImageChange = (e) => {
    const file = e.target.files[0]
    if (file) {
      setFormData((prev) => ({
        ...prev,
        profilePicture: file,
      }))
      // Create preview URL
      const reader = new FileReader()
      reader.onloadend = () => {
        setPreviewImage(reader.result)
      }
      reader.readAsDataURL(file)
    }
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    // Add your signup logic here
    console.log("Form submitted:", formData)
  }

  return (
    <div className="min-vh-100 position-relative">
      {/* Background Image */}
      <div className="position-absolute top-0 start-0 w-100 h-100">
        <img
          src="https://hebbkx1anhila5yf.public.blob.vercel-storage.com/background.jpg-cnsrxkylOlgoxIUEeV3t9nLeLKHCa6.jpeg"
          alt="Netflix Background"
          className="w-100 h-100 object-fit-cover"
          style={{ filter: "brightness(35%)" }}
        />
      </div>

      {/* Content */}
      <div className="position-relative">
        {/* Header */}
        <header className="p-4">
          <Link to="/" className="text-danger text-decoration-none">
            <div className="fs-1 fw-bold">NETFLIX</div>
          </Link>
        </header>

        {/* Signup Form */}
        <div className="container">
          <div className="row justify-content-center">
            <div className="col-md-6 col-lg-4">
              <div className="card bg-black bg-opacity-75 text-white p-4">
                <div className="card-body">
                  <h1 className="card-title mb-4">Sign Up</h1>
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
                    <div className="mb-3">
                      <input
                        type="text"
                        className="form-control form-control-lg bg-dark text-white"
                        placeholder="Display Name"
                        name="displayName"
                        value={formData.displayName}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
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
                    <button type="submit" className="btn btn-danger btn-lg w-100 mb-3">
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

