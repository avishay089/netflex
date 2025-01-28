import React, { useState, useEffect } from "react"
import "./AdminCategoryManager.css"

function AdminCategoryManager() {
  const [categories, setCategories] = useState([])
  const [newCategory, setNewCategory] = useState({ name: "", promoted: false })

  useEffect(() => {
    fetchCategories()
  }, [])

  const fetchCategories = async () => {
    const token = localStorage.getItem("token")
    try {
      const response = await fetch("http://localhost:5000/api/categories", {
        headers: {
          user_id: localStorage.getItem("user_id"),
          token: token,
        },
      })
      if (response.ok) {
        const data = await response.json()
        setCategories(data)
      }
    } catch (error) {
      console.error("Error fetching categories:", error)
    }
  }

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target
    setNewCategory({
      ...newCategory,
      [name]: type === "checkbox" ? checked : value,
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const token = localStorage.getItem("token")
    try {
      const response = await fetch("http://localhost:5000/api/categories", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          user_id: localStorage.getItem("user_id"),
          token: token,
        },
        body: JSON.stringify(newCategory),
      })
      if (response.ok) {
        fetchCategories()
        setNewCategory({ name: "", promoted: false })
      }
    } catch (error) {
      console.error("Error adding category:", error)
    }
  }

  const handleUpdate = async (id, updatedCategory) => {
    const token = localStorage.getItem("token")
    try {
      const response = await fetch(`http://localhost:5000/api/categories/${id}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          user_id: localStorage.getItem("user_id"),
          token: token,
        },
        body: JSON.stringify(updatedCategory),
      })
      if (response.ok) {
        fetchCategories()
      }
    } catch (error) {
      console.error("Error updating category:", error)
    }
  }

  const handleDelete = async (id) => {
    const token = localStorage.getItem("token")
    try {
      const response = await fetch(`http://localhost:5000/api/categories/${id}`, {
        method: "DELETE",
        headers: {
          user_id: token,
        },
      })
      if (response.ok) {
        fetchCategories()
      }
    } catch (error) {
      console.error("Error deleting category:", error)
    }
  }

  return (
    <div className="admin-category-manager">
      <h2>Category Manager</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="name"
          value={newCategory.name}
          onChange={handleInputChange}
          placeholder="Category Name"
          required
        />
        <label>
          <input type="checkbox" name="promoted" checked={newCategory.promoted} onChange={handleInputChange} />
          Promoted
        </label>
        <button type="submit">Add Category</button>
      </form>
      <div className="category-list">
        {categories.map((category) => (
          <div key={category._id} className="category-item">
            <input
              type="text"
              value={category.name}
              onChange={(e) => handleUpdate(category._id, { name: e.target.value })}
            />
            <label>
              <input
                type="checkbox"
                checked={category.promoted}
                onChange={(e) => handleUpdate(category._id, { promoted: e.target.checked })}
              />
              Promoted
            </label>
            <button onClick={() => handleDelete(category._id)}>Delete</button>
          </div>
        ))}
      </div>
    </div>
  )
}

export default AdminCategoryManager