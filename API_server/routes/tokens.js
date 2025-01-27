const express = require('express');
const router = express.Router();
const tokenController = require('../controllers/tokens');

// Route לביצוע login
router.post('/', tokenController.login);

module.exports = router;
