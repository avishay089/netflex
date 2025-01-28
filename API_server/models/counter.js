
const mongoose = require('mongoose');
const Schema = mongoose.Schema;

// Define a schema to store the counters
const Counter = new Schema({
  _id: { type: String, required: true }, // counter name (e.g., 'userId', 'movieId')
  seq: { type: Number, default: 0 } // counter value
});

module.exports= mongoose.model('Counter', Counter);
