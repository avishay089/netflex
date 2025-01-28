#include "Post.h"
#include <sstream>
#include <iostream>
#include <vector>

using namespace std;
string Post::execute(string &command) {
    istringstream iss(command);
    string word;
    ostringstream result;  // משתנה לאחסון הפלט

    // Step 1: Ignore the first word ("POST")
    iss >> word;
    if (word != "POST") {
        result << "400 Bad Request\n";  // Return Bad Request if command is invalid
        return result.str();
    }

    // Step 2: Read the user ID
    int userId;
    iss >> userId;

    // Check if user ID is a valid integer
    if (iss.fail()) {
        result << "400 Bad Request\n";  // Return Bad Request if user ID is invalid
        return result.str();
    }

    // Step 3: Check if user already exists (POST should only add new users)
    if (data->search(userId) != -1) {
        result << "404 Not Found\n";  // Return Not Found if user already exists
        return result.str();
    }

    // Step 4: Read the movie IDs (integers)
    vector<int> movies;
    string token;
    while (iss >> token) {
        try {
            int movieId = stoi(token);  // Try converting the token to an integer
            movies.push_back(movieId);
        } catch (const invalid_argument &) {
            result << "400 Bad Request\n";  // Return Bad Request if a movie ID is invalid
            return result.str();
        } catch (const out_of_range &) {
            result << "400 Bad Request\n";  // Handle out-of-range integers
            return result.str();
        }
    }

    // If no movie IDs were provided, print an error
    if (movies.empty()) {
        result << "400 Bad Request\n";  // Return Bad Request if no movies were provided
        return result.str();
    }

    // Step 5: Add new user and movie list to the data
    ostringstream oss;
    oss << userId;
    for (const auto &movie : movies) {
        oss << " " << movie;
    }
    data->writeData(oss.str());  // Write the data to the source

    // Step 6: Successfully created the new user and movie list
    result << "201 Created\n";  // Return Created if user is successfully added
    return result.str();  // Return the result string
}
