
const mongoose = require('mongoose');


const TokenSchema= new mongoose.Schema ({
userID: {type:mongoose.Schema.Types.ObjectId, ref: 'User', required:true},
token:{type:String, required:true}
});
module.exports= mongoose.model('Token',TokenSchema);