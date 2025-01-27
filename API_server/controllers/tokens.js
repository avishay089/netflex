// Token Controller - מנהל את תהליך האימות של המשתמש
const User = require('../models/users');
const Token = require('../models/tokens');
const { generateToken } = require('../services/tokens');

exports.login = async (req, res) => {
    try {
        const { username, password } = req.body; // מקבל שם משתמש וסיסמה מהבקשה

        const user = await User.findOne({ username, password }); // מחפש משתמש עם השם והסיסמה כפי שהיא
        if (!user) {
            return res.status(404).json({ error: 'Invalid username or password' }); // אם המשתמש לא נמצא, מחזיר שגיאה
        }

        // יצירת Token (ללא הצפנה)
        const token = generateToken(); // יוצר טוקן חדש למשתמש
        await Token.create({ userID: user._id, token }); // שמירת הטוקן במסד הנתונים

        res.status(201).json({ userId: user._id, token: token, isAdmin: user.isAdmin}); // מחזיר את מזהה המשתמש והטוקן בתשובה
    } catch (error) {
        res.status(400).json({ error: error.message }); // מטפל בשגיאות בלתי צפויות
    }
};

