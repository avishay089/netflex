const User = require('../models/users');
const CounterModel = require('../models/counter');  // Rename to `CounterModel` to avoid confusion


const createUser = async (userData) => {
    const user = new User(userData);
    await user.save();
    return user;
};

const getUserById = async (id) => {
    return await User.findById(id);  // מחפש משתמש לפי ID במאגר
};

const addMovieWatched = async (user_id, movie_id) => { 
    try {
        // Search for the user by ID
        const user = await getUserById(user_id);
        if (!user) {
            return { error: 'User not found' };  // If no user is found with the provided ID
        }
        // Add the movie to the movies_watched array
        user.movies_watched.push(movie_id);

        // Save the updated user back to the database
        await user.save();

        return { success: 'Movie added to watched list' };  // Return success message if movie was added successfully
    } catch (error) {
        return { error: error.message };  // Return the error message if there is an issue during the process
    }
};


const findUserByUsernameOrTzOrEmail = async (username, tz, email) => {
    return await User.findOne({
        $or: [
            { username: username },  // חיפוש לפי שם משתמש
            { tz: tz },              // חיפוש לפי תעודת זהות
            { email: email }         // חיפוש לפי מייל
        ]
    });
};


const getNextUserId = async () => {
    const counter = await CounterModel.findByIdAndUpdate(
        { _id: 'userId' },
        { $inc: { seq: 1 } },
        { new: true, upsert: true } // Creates the counter if it doesn't exist
    );
    return counter.seq;
};

module.exports = {
    createUser,
    getUserById,
    findUserByUsernameOrTzOrEmail,
    getNextUserId,
    addMovieWatched
};
