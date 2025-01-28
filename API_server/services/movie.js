const Movie = require('../models/movie');
const CounterModel = require('../models/counter');  // Rename to `CounterModel` to avoid confusion
const Category = require('../models/category');
const User = require('./users');

const createMovie = async (movieData) => {
    const movie = new Movie(movieData);
    return await movie.save();
};

const getMovies = async (user_id) => { 
    const categories_array = [];
    const user = await User.getUserById(user_id);
    const categories = await Category.find({"promoted" : true});
    
    var movies_seen = user["movies_watched"]
    for (let i = 0; i < categories.length; i++) {
        const category = categories[i];
        const movies = await Movie.find({"category":{$all:[`${category.name}`]}});
                
        var movies_not_seen = movies.filter((value) => {
            return !movies_seen.includes(value);
        });
        movies_not_seen = shuffleArray(movies_not_seen);
        if(movies_not_seen.length > 20) {
            movies_not_seen = movies_not_seen.slice(0, 20);
        }
        const category_json = {"category_name" : category["name"], "movies" : movies_not_seen};
        categories_array.push(category_json);
    }
    if (movies_seen.length > 20) {
        let start_index = movies_seen.length - 20;
        movies_seen = movies_seen.slice(start_index, movies_seen.length)
    }
    const category_json = {"category_name" : "movies watched", "movies" : movies_seen};
    categories_array.push(category_json);
    return await categories_array;

};

const getMovieById = async (id) => {
    return await Movie.findById(id);
};

const updateMovie = async (id, updatedData) => {
    const movie = await Movie.findByIdAndUpdate(id, updatedData, { new: true });
    return movie;
};

const deleteMovie = async (id) => {
    const movie = await Movie.findByIdAndDelete(id);
    return movie;
};
const searchMovies = async (query) => {
    try {

        // בדוק אם המילת חיפוש היא מספר
        const queryIsNumber = !isNaN(query);
        const queryIsDate = !isNaN(Date.parse(query)); // בדוק אם החיפוש הוא תאריך

        // יצירת תנאים לחיפוש
        const searchConditions = [
            { name: { $regex: query, $options: 'i' } },
            { category: { $regex: query, $options: 'i' } },
            { director: { $regex: query, $options: 'i' } },
            { starring: { $regex: query, $options: 'i' } },
            { description: { $regex: query, $options: 'i' } }
        ];

        if (queryIsNumber) {
            // אם החיפוש הוא מספר, חפש ב-rating ו-duration
            searchConditions.push(
                { rating: query },  // חיפוש ב-rating
                { duration: query }  // חיפוש ב-duration
            );
        }

        if (queryIsDate) {
            // אם החיפוש הוא תאריך, חפש ב-releaseDate
            const queryDate = new Date(query);
            searchConditions.push(
                { releaseDate: { $gte: queryDate } } // חפש סרטים שיצאו אחרי התאריך הנבחר
            );
        }

        return await Movie.find({ $or: searchConditions });

    } catch (error) {
        console.error(error);
        throw new Error(error.message);
    }
};

const getNextMovieId = async () => {
    const counter = await CounterModel.findByIdAndUpdate(
        { _id: 'movieId' },
        { $inc: { seq: 1 } },
        { new: true, upsert: true } // Creates the counter if it doesn't exist
    );
    return counter.seq;
};

function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        // Generate a random index between 0 and i
        const j = Math.floor(Math.random() * (i + 1));
        let temp = array[i]
        // Swap elements at indices i and j
        array[i]= array[j];
        array[j] = temp;
    }
    return array;
}

module.exports = {
    createMovie,
    getMovies,
    getMovieById,
    updateMovie,
    deleteMovie,
    searchMovies,
    getNextMovieId
};
