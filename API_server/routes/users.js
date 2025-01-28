const express = require('express');
const router = express.Router();
const usersController = require('../controllers/users');

router.route('/')
    .post(usersController.createUser) // Create a new movie

router.route('/:id')
    .get(usersController.getUser)
module.exports = router;