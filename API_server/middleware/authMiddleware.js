const Token = require('../models/tokens');

module.exports = async (req, res, next) => {
    // שליפת ה-Authorization Header
    const authHeader = req.headers['authorization'];
    if (!authHeader) {
        return res.status(401).json({ error: 'Unauthorized: Authorization header is missing' });
    }

    // חילוץ הטוקן
    const token = authHeader.split(' ')[1]; // פורמט: "Bearer <token>"
    if (!token) {
        return res.status(401).json({ error: 'Unauthorized: Token is missing' });
    }

    try {
        // בדיקה אם הטוקן קיים במסד הנתונים
        const validToken = await Token.findOne({ token });
        if (!validToken) {
            return res.status(401).json({ error: 'Unauthorized: Invalid or expired token' });
        }

        // שמירת מזהה המשתמש בבקשה
        req.userId = validToken.userID;

        // ממשיכים לנתיב הבא
        next();
    } catch (error) {
        // טיפול בשגיאות
        res.status(500).json({ error: error.message });
    }
};
