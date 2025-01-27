import React, { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import Navigation from "./Navigation"
import AdminMovieManager from "./AdminMovieManager"
import AdminCategoryManager from "./AdminCategoryManager"
import "./AdminPage.css"

function AdminPage() {
    const [isAdmin, setIsAdmin] = useState(false)
    const navigate = useNavigate()

    useEffect(() => {
        // Check if the user is an admin
        const checkAdminStatus = async () => {
            const token = localStorage.getItem("token")
            const isAdmin = localStorage.getItem("isAdmin")
            if (!token) {
                navigate("/login")
                return
            }

            if (isAdmin === "true") {
                setIsAdmin(isAdmin)
            } else {
                // If not admin, redirect to home page
                navigate("/browse")
            }
        }
        checkAdminStatus()
    }, [navigate])
    
    if (!isAdmin) {
        return <div>Loading...</div>
    }

    return (
        <div className="admin-page">
            <Navigation />
            <div className="admin-content">
                <h1>Admin Dashboard</h1>
                <div className="admin-sections">
                    <AdminMovieManager />
                    <AdminCategoryManager />
                </div>
            </div>
        </div>
    )
}

export default AdminPage

