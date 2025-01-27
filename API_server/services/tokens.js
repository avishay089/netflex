const crypto= require ('crypto')
exports.generateToken = () => {
    return crypto.randomBytes(16).toString('hex');
};