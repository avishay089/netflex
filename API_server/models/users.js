const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const UserSchema = new Schema({
    int_Id: { // Custom user ID
        type: Number,
        required: true,
        unique: true
    },
    tz: { // תעודת זהות
        type: String,
        required: true,
        unique: true,
        match: /^[0-9]{9}$/  
    },
    password: {
        type: String,
        required: true,
        minlength: 6 
    },
    name: { 
        type: String,
        required: true
    },
    email: { // מייל
        type: String,
        required: true,
        unique: true,
        match: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/
    },
    username: {
        type: String,
        required: true,
        unique: true
    },
    paymentMethod: { // כרטיס אשראי
        type: String,
        required: false,
        match: /^[0-9]{16}$/
    },
    profilePicture: { // תמונה
        type: String,
        default:  "https://wallpapers.com/images/high/netflix-profile-pictures-1000-x-1000-qo9h82134t9nv0j0.webp"
    },
    releaseDate: { // תאריך רישום
        type: Date,
        default: Date.now
    },
    movies_watched: {
        type: [mongoose.Schema.Types.ObjectId],
        ref: 'Movie',
        default: []
    },
    isAdmin: {
        type: Boolean,
        default: false
    }
});

module.exports = mongoose.model('User', UserSchema);
