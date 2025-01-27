const express = require('express');
const router = express.Router();
const movieController = require('../controllers/movie');
const recommendController = require('../controllers/recommend');

router.route('/')
    .post(movieController.createMovie) // Create a new movie
    .get(movieController.getMovies);   // Get movies based on categories and user history

router.route('/:id')
    .get(movieController.getMovie)    // Get a specific movie by ID
    .put(movieController.updateMovie) // Update a movie by ID
    .delete(movieController.deleteMovie); // Delete a movie by ID

router.route('/search/:query')
    .get(movieController.searchMovies);

router.route('/:id/recommend')
    .get(recommendController.getRecommends)
    .post(recommendController.addRecommend);

module.exports = router;