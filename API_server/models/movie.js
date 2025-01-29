const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const MovieSchema = new Schema({
    name: {
        type: String,
        required: true
    },
    category: {
        type: String,
        required: true
    },
    releaseDate: {
        type: Date,
        default: Date.now
    },
    rating: {
        type: Number,
        min: 0,
        max: 10
    },
    duration: {
        type: Number // Duration in minutes
    },
    director: {
        type: String
    },
    starring: {
        type: [String] // Array of actors' names
    },
    description: {
        type: String // Short description of the movie
    },
    int_Id: { // Custom user ID
        type: Number,
        required: true,
        unique: true
    },
    imageUrl: {
        type: String // URL of the movie poster
    },

videoUrl: {
    type: String // URL of the video
    }
});

module.exports = mongoose.model('Movie', MovieSchema);