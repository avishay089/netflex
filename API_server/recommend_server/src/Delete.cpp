#include "Delete.h"
#include <sstream>
#include <iostream>
#include <vector>
#include <set>
#include <cctype>
using namespace std;

string Delete::execute(string &command) {
    istringstream iss(command);
    string word;
    string response;  // Variable to store the response message

    // Step 1: Ignore the first word ("DELETE")
    iss >> word;
    if (word != "DELETE") {
        response = "400 Bad Request\n";  // Return Bad Request if command is invalid
        return response;
    }

    // Step 1.5: Check the number of arguments
    int argsCount = 0;
    string temp;
    while (iss >> temp) {
        argsCount++;
    }

    if (argsCount < 2) { // Check if the number of arguments is less than 2
        response = "400 Bad Request\n";
        return response;
    }

    // Reset the stream to parse again
    iss.clear();
    iss.str(command);
    iss >> word; // Skip the first word ("DELETE")

    // Step 2: Read the user ID
    int userId;
    iss >> userId;

    // Check if user ID is a valid integer
    if (iss.fail()) {
        response = "400 Bad Request\n";  // Invalid user ID
        return response;
    }

    // Step 3: Check if the user exists
    int lineNumber = data->search(userId);
    if (lineNumber == -1) {
        response = "404 Not Found\n";  // Return 404 if user does not exist
        return response;
    }

    // Step 4: Read the movie IDs (integers) to delete
    vector<int> moviesToDelete;
    string token;

    while (iss >> token) {
        // Use a string stream to attempt to parse each token as an integer
        istringstream tokenStream(token);
        int movieId;
        if (!(tokenStream >> movieId)) {
            response = "400 Bad Request\n"; // One of the movies is not an int
            return response;
        }
        moviesToDelete.push_back(movieId);
    }

    // If no movie IDs were provided, return an error
    if (moviesToDelete.empty()) {
        response = "400 Bad Request\n"; // No movie IDs provided
        return response;
    }

    // Step 5: Get the existing movie list
    string line = data->getLine(lineNumber);
    istringstream lineStream(line);
    int existingMovieId;

    vector<int> existingMovies;
    lineStream >> existingMovieId;  // Skip userId
    while (lineStream >> existingMovieId) {
        existingMovies.push_back(existingMovieId);
    }

    // Step 6: Check if all movie IDs to delete exist in the user's list
    set<int> existingMoviesSet(existingMovies.begin(), existingMovies.end());
    bool allMoviesExist = true;

    for (const auto &movie : moviesToDelete) {
        if (existingMoviesSet.find(movie) == existingMoviesSet.end()) {
            allMoviesExist = false;
            break;
        }
    }

    // אם יש סרטים שלא קיימים, תחזיר 404
    if (!allMoviesExist) {
        response = "404 Not Found\n"; // אחד או יותר מהסרטים לא קיימים ברשימה
        return response;
    }

    // Step 7: Remove specified movies
    vector<int> updatedMovies;

    // Convert moviesToDelete to a set for faster lookup
    set<int> deleteSet(moviesToDelete.begin(), moviesToDelete.end());

    for (const auto &existingMovie : existingMovies) {
        if (deleteSet.find(existingMovie) == deleteSet.end()) {
            // Movie is not in the delete set, so keep it in the updated list
            updatedMovies.push_back(existingMovie);
        }
    }

    // Step 8: Reconstruct the line with updated movies
    ostringstream updatedLine;
    updatedLine << userId;
    for (const auto &movie : updatedMovies) {
        updatedLine << " " << movie;
    }

    // Step 9: Use insertInto to update the line
    if (updatedMovies.empty()) {
        data->deleteLine(lineNumber);
    } else {
        data->clearLine(lineNumber);
        data->insertInto(lineNumber, updatedMovies);  // Update the line in the data source
    }

    response = "204 No Content\n";  // Successfully deleted
    return response;  // Return the response
}
