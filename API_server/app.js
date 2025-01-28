const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const mongoose = require('mongoose');
const movies = require('./routes/movie'); // Add movie routes
const categories = require('./routes/category'); // Add category routes
const users = require('./routes/users'); // Add users routes
const tokens = require('./routes/tokens');  // הקישור למסלול ה-tokens
require('custom-env').env(process.env.NODE_ENV, './config');



mongoose.connect(process.env.CONNECTION_STRING);


var app = express();
app.use(express.static('public'));
app.use(cors());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.json());

app.use('/api/movies', movies); // Register the movie route
app.use('/api/categories', categories); // Register the category route
app.use('/api/users', users); // Register the users route
app.use('/api/tokens', tokens);


app.listen(process.env.PORT);