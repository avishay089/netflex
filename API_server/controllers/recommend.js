const recommendService = require('../services/recommend');
const usersService = require('../services/users'); // Importing the usersService

const addRecommend = async (req, res) => {
    try {
        // Check if the user exists by calling the getUserById function
        const userExists = await usersService.getUserById(req.headers["user_id"]);
        if (!userExists) {
            // If the user does not exist, return a 404 error
            return res.status(404).json({ error: "User not found" });
        }

        // Proceed to add the recommendation if the user exists
        const newRecommend = await recommendService.addRecommend(
            req.headers["user_id"], 
            req.params.id, 
            req.headers["port_num"], 
            req.headers["host_name"]
        );

        // Return the new recommendation with a 201 status code
        res.status(204).location(`api/movies/${req.headers["user_id"]}/recommend/${newRecommend._id}`).json(newRecommend);
    } catch (error) {
        // Print the error if something goes wrong
        console.error(error.message);
        return res.status(400).json({ error: "Error adding recommendation" });
    }
};

const getRecommends = async (req, res) => {
    try {
        // Check if the user exists by calling the getUserById function
        const userExists = await usersService.getUserById(req.headers["user_id"]);
        if (!userExists) {
            // If the user does not exist, return a 404 error
            return res.status(404).json({ error: "User not found" });
        }

        // Proceed to get the recommendations if the user exists
        const newRecommend = await recommendService.getRecommends(
            req.headers["user_id"], 
            req.params.id, 
            req.headers["port_num"], 
            req.headers["host_name"]
        );
        
        // Return the recommendations with a 200 status code
        res.status(200).json(newRecommend);

    } catch (error) {
        // Print the error if something goes wrong
        console.error(error.message);
        return res.status(400).json({ error: "Error fetching recommendations" });
    }
};

module.exports = { addRecommend, getRecommends };
