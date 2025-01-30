// HomeScreen.js
import React, { useState } from "react"
import "bootstrap/dist/css/bootstrap.min.css"
import "./Home.css"; // Create this CSS file
import { useNavigate } from "react-router-dom"

const HomeScreen = () => {
    const navigate = useNavigate()
    const [email, setEmail] = useState("")

    const handleGetStarted = (e) => {
        e.preventDefault()
        if (email) {
            navigate("/signup", { state: { email } })
        }
    }
    return (
        <div className="min-vh-100 position-relative">
            {/* Background Image */}
            <div className="position-absolute top-0 start-0 w-100 h-100">
                <img
                    src="https://hebbkx1anhila5yf.public.blob.vercel-storage.com/background.jpg-cnsrxkylOlgoxIUEeV3t9nLeLKHCa6.jpeg"
                    alt="Netflix Background"
                    className="w-100 h-100 object-fit-cover"
                    style={{ filter: "brightness(50%)" }}
                />
            </div>

            {/* Content */}
            <div className="position-relative z-1">
                {/* Header */}
                <header className="d-flex justify-content-between align-items-center p-4">
                    <div className="text-danger fs-1 fw-bold">NETFLEX</div>
                    <div className="d-flex gap-3">
                        <select className="form-select bg-transparent text-white border-white" style={{ width: "auto" }}>
                            <option value="en">English</option>
                        </select>
                        <button className="btn btn-danger" onClick={() => navigate("/login")}>Sign In</button>
                    </div>
                </header>

                {/* Hero Section */}
                <div
                    className="d-flex flex-column align-items-center text-center text-white p-4 py-5 mx-auto"
                    style={{ maxWidth: "768px" }}
                >
                    <h1 className="display-4 fw-bold mb-4">Unlimited movies, TV shows, and more</h1>
                    <p className="fs-3 mb-4">Starts at ₪32.90. Cancel anytime.</p>
                    <p className="fs-5 mb-4">Ready to watch? Enter your email to create or restart your membership.</p>

                    {/* Email Form */}
                    <form onSubmit={handleGetStarted} className="d-flex flex-column flex-sm-row gap-3 w-100">
                        <input
                            type="email"
                            placeholder="Email address"
                            className="form-control form-control-lg bg-dark bg-opacity-50 text-white"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                        <button type="submit" className="btn btn-danger btn-lg text-nowrap">
                            Get Started &gt;
                        </button>
                    </form>
                </div>
            </div>
        </div>
    )
};

export default HomeScreen;
