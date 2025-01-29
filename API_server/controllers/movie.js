const movieService = require('../services/movie');
const {getNextMovieId} = require('../services/movie');
const categoryService = require('../services/category')
const usersService = require('../services/users')

const createMovie = async (req, res) => {
    try {
        if (!req.body.name) {
            return res.status(400).json({ error: ['Name is required'] });
        }
        if (!req.body.category) {
            return res.status(400).json({ error: ['Category is required'] });
        }
        if (!req.body.videoUrl) { 
            req.body.videoUrl = `http://localhost:5000/${req.body.name}.mp4`;
        }
        const movieId = await getNextMovieId();
        req.body.int_Id = movieId;
        const movie = await movieService.createMovie(req.body);
        res.set('user_id', req.headers['user_id']);
        res.status(201).end();
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

const getMovies = async (req, res) => {
    try {
        const movies = await movieService.getMovies(req.headers["user_id"]);
        res.set('user_id', req.headers['user_id']);
        res.status(200).json(movies);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

const getMovie = async (req, res) => {
    try {
        const movie = await movieService.getMovieById(req.params.id);
        if (!movie) {
            return res.status(404).json({ error: 'Movie not found' });
        }
        res.set('user_id', req.headers['user_id']);
        res.status(200).json(movie);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

const updateMovie = async (req, res) => {
    try {
        const movie = await movieService.updateMovie(req.params.id, req.body);
        if (!movie) {
            return res.status(404).json({ error: 'Movie not found' });
        }
        res.set('user_id', req.headers['user_id']);
        res.status(204).end();
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
};

const deleteMovie = async (req, res) => {
    try {
        const movie = await movieService.deleteMovie(req.params.id);
        if (!movie) {
            return res.status(404).json({ error: 'Movie not found' });
        }
        res.set('user_id', req.headers['user_id']);
        res.status(204).end();
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
};

const searchMovies = async (req, res) => {
    try {
        const { query } = req.params; // תקבל את המחרוזת ישירות מתוך ה-params
        console.log('Received search query:', query); // הדפסת המחרוזת שהתקבלה

        // אם לא נמסר מחרוזת חיפוש
        if (!query) {
            return res.status(400).json({ error: 'Query parameter is required' });
        }

        // חיפוש סרטים
        const movies = await movieService.searchMovies(query);
        console.log('Movies found:', movies); // הדפסת תוצאות החיפוש

        if (movies.length === 0) {
            return res.status(404).json({ error: 'No movies found matching the query' });
        }

        res.status(200).json(movies);
    } catch (error) {
        console.error('Error searching movies:', error);
        res.status(400).json({ error: error.message });
    }
};


module.exports = {
    createMovie,
    getMovies,
    getMovie,
    updateMovie,
    deleteMovie,
    searchMovies
};
