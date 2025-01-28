const usersService = require('../services/users');
const recommendService = require('../services/recommend');
const { findUserByUsernameOrTzOrEmail } = require('../services/users');
const {getNextUserId} = require('../services/users');


// Function to create a new user
const createUser = async (req, res) => {
    try {
        // Initial checks to ensure the required fields are present and valid
        if (!req.body.name) {
            return res.status(400).json({ error: ['Name is required'] });
        }
        if (!req.body.tz || !/^[0-9]{9}$/.test(req.body.tz)) {
            return res.status(400).json({ error: ['TZ is required and must be 10 digits'] });
        }
        if (!req.body.password || req.body.password.length < 6) {
            return res.status(400).json({ error: ['Password is required and must be at least 6 characters long'] });
        }
        // Email validation
        if (!req.body.email || !/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/.test(req.body.email)) {
            return res.status(400).json({ error: ['Valid email is required'] });
        }


        // Ensure there is no existing user with the same username, TZ, or email
        const existingUser = await findUserByUsernameOrTzOrEmail(req.body.username, req.body.tz, req.body.email);
        if (existingUser) {
            return res.status(400).json({ error: ['Username, TZ, or email already exists'] });
        }
        // Get the next available user ID using the counter service
        const userId = await getNextUserId();
        // Create the new user
        const user = await usersService.createUser({
            int_Id: userId,  // Use the custom userId from the counter
            tz: req.body.tz,
            name: req.body.name,
            email: req.body.email,
            username: req.body.username,
            password: req.body.password,
            isAdmin: req.body.isAdmin,
        });
        await recommendService.createRecommend(user._id, null, null);

        res.status(201).json();
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
};

// Function to get a user by their ID
const getUser = async (req, res) => {
    try {
        // חיפוש המשתמש לפי ה-ID
        const user = await usersService.getUserById(req.params.id);
        if (!user) {
            return res.status(404).json({ error: 'User not found' });
        }

        // בחר את השדות הרצויים
        const user_ans = {
            name: user.name,
            email: user.email,
            username: user.username,
            profilePicture: user.profilePicture, // שדה התמונה
        };

        // החזרת התשובה עם השדות שבחרת
        res.status(200).json(user_ans);
    } catch (error) {
        res.status(400).json({ error: error.message });
    }
};


module.exports = {
  createUser,
  getUser 
};
