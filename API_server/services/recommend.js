const net = require('net');
const Movie = require('../models/movie'); // Importing the movie model
const User = require('../models/users'); // Importing the user model
const Userservices = require('../services/users'); // Importing the user model
// Function to find a user by user_id and return the user's int_Id
const getUserIntId = async (user_id) => {
    try {
        const user = await User.findOne({ _id: user_id });
        if (!user) {
            throw new Error(`User with ID ${user_id} not found`);
        }
        return user.int_Id;
    } catch (error) {
        throw error;
    }
};

// Function to find a movie by movie_id and return the movie's int_Id
const getMovieIntId = async (movie_id) => {
    try {
        const movie = await Movie.findOne({ _id: movie_id });
        if (!movie) {
            throw new Error(`Movie with ID ${movie_id} not found`);
        }
        return movie.int_Id;
    } catch (error) {
        throw error;
    }
};

// Function to create a recommendation for a user
const createRecommend = async (user_id, movie_id, port, host) => {
    if (port == null) port = 8080;
    if (host == null) host = '127.0.0.1';
    const userIntId = await getUserIntId(user_id);
    const fiction = -1;
    var data = `POST ${userIntId}${fiction}`;
    const ret = await sendData(data, port, host);
    return ret; 
};

// Function to add a recommendation for a user and movie
const addRecommend = async (user_id, movie_id, port, host) => {
    if (port == null) port = 8080;
    if (host == null) host = '127.0.0.1';
    const user = await User.findOne({ _id: user_id });
    if (!user) {
        throw new Error(`User with ID ${user_id} not found`);
    }
    const movieWatched = user.movies_watched || [];
    if (!movieWatched.includes(movie_id)) {
        await Userservices.addMovieWatched(user_id, movie_id);
    }
    const userIntId = await getUserIntId(user_id);
    const movieIntId = await getMovieIntId(movie_id);
    var data = `PATCH ${userIntId} ${movieIntId}`;
    return await sendData(data, port, host); 
};

// Function to convert int_Id to MongoDB _id for each movie
const convertIntIdToMongoId = async (recommendations) => {
    const updatedRecommendations = [];
    const recommendationsArray = recommendations.split(' ');

    for (let i = 0; i < recommendationsArray.length; i++) {
        const recommendation = recommendationsArray[i];
        const recommendationInt = parseInt(recommendation, 10);
        if (!Number.isInteger(recommendationInt)|| recommendationInt === -1) {
            continue;
        }
        try {
            const movie = await Movie.findOne({ int_Id: recommendationInt });
            if (movie) {
                updatedRecommendations.push(movie._id);
            }
        } catch (error) {
            // Error handled silently
        }
    }
    return updatedRecommendations;
};

// Function to get recommendations for a user and movie
const getRecommends = async (user_id, movie_id, port = 8080, host = '127.0.0.1') => {
    const userIntId = await getUserIntId(user_id);
    const movieIntId = await getMovieIntId(movie_id);
    const data = `GET ${userIntId} ${movieIntId}`;
    const res = await sendData(data, port, host);
    const responseParts = res.split('\n');
    const status = responseParts[0].trim();
    const moviesData = responseParts.slice(1).join('\n');
    const Recommendations = await convertIntIdToMongoId(moviesData);

    return {
        status,
        Recommendations,
    };
};

// Function to delete a recommendation for a user and movie
const deleteRecommend = async (user_id, movie_id, port, host) => { 
    if (port == null) port = 8080;
    if (host == null) host = '127.0.0.1';
    const userIntId = await getUserIntId(user_id);
    const movieIntId = await getMovieIntId(movie_id);
    var data = `DELETE ${userIntId} ${movieIntId}`;
    const res = await sendData(data, port, host); 
    return res;
};

// Function to send data via TCP
function sendData(data, port, host) {
    const client = new net.Socket();
    client.connect(port, host, () => {
        client.write(data);
    });

    return new Promise((resolve, reject) => {
        client.on('data', (data) => {
            const receivedData = data.toString();
            client.destroy();
            resolve(receivedData);
        });
    });
}

// Exporting the functions so they can be used elsewhere in the application
module.exports = { createRecommend, getRecommends, deleteRecommend, addRecommend };
