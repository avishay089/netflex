const categoryService = require('../services/category');
const createCategory = async (req, res) => {
    if (!req.body.name) {
        return res.status(400).json({ error: ['Name is required'] });
    }
    await categoryService.createCategory(req.body.name, req.body.promoted);
    res.set('user_id', req.headers['user_id']);
    res.status(201).end();
};
const getCategories = async (req, res) => {
    res.set('user_id', req.headers['user_id']);
    res.status(200).json(await categoryService.getCategories());
};
const getCategory = async (req, res) => {
    // if (!Number.isInteger(req.params.id)) {
    //     return res.status(404).json({ errors: ['Category not found'] });
    // }
    const category = await categoryService.getCategoryById(req.params.id);
    if (!category) {
        return res.status(404).json({ errors: ['Category not found'] });
    }
    res.set('user_id', req.headers['user_id']);
    res.status(200).json(category);
};
const updateCategory = async (req, res) => {
    // if (!Number.isInteger(req.params.id)) {
    //     return res.status(404).json({ errors: ['Category not found'] });
    // }
    const category = await categoryService.updateCategory(req.params.id, req.body.name, req.body.promoted);
    if (!category) {
        return res.status(404).json({ errors: ['Category not found'] });
    }
    res.set('user_id', req.headers['user_id']);
    res.status(204).end();
};
const deleteCategory = async (req, res) => {
    const category = await categoryService.deleteCategory(req.params.id);
    if (!category) {
        return res.status(404).json({ errors: ['Category not found'] });
    }
    res.set('user_id', req.headers['user_id']);
    res.status(204).end();
};
module.exports = { createCategory, getCategories, getCategory, updateCategory, deleteCategory };