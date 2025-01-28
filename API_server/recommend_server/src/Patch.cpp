#include "Patch.h"
#include <sstream>
#include <iostream>
#include <vector>

using namespace std;

string Patch::execute(string &command) {
    istringstream iss(command);
    string word;
    string response;  // Variable to store the response message

    // Step 1: Ignore the first word ("PATCH")
    iss >> word;
    if (word != "PATCH") {
        response = "400 Bad Request\n";  // Return Bad Request if command is invalid
        return response;
    }

    // Step 2: Read the user ID
    int userId;
    iss >> userId;

    // Check if user ID is a valid integer
    if (iss.fail()) {
        response = "400 Bad Request\n";  // Invalid user ID
        return response;
    }

    // Step 3: Check if the user exists (using the search method from Add class)
    int lineNumber = data->search(userId);
    if (lineNumber == -1) {
        response = "404 Not Found\n";  // Return 404 if user does not exist
        return response;
    }

    // Step 4: Read the movie IDs (integers)
    vector<int> movies;
    string token;
    while (iss >> token) {
        try {
            int movieId = stoi(token);  // Try converting the token to an integer
            movies.push_back(movieId);
        } catch (const invalid_argument &) {
            response = "400 Bad Request\n";  // Return Bad Request if a movie ID is invalid
            return response;
        } catch (const out_of_range &) {
            response = "400 Bad Request\n";  // Handle out-of-range integers
            return response;
        }
    }

    // If no movie IDs were provided, return an error
    if (movies.empty()) {
        response = "400 Bad Request\n";  // No movie IDs provided
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

    // Step 6: Add only new movies (that don't exist already)
    vector<int> newMovies;
    for (const auto& movie : movies) {
        bool exists = false;
        for (const auto& existingMovie : existingMovies) {
            if (existingMovie == movie) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            newMovies.push_back(movie);
        }
    }

    if (newMovies.empty()) {
        response = "204 No Content\n";  // No new movies to patch
        return response;
    }

    // Step 7: Update the user record with new movies
    data->insertInto(lineNumber, newMovies);  // Assuming insertInto updates the line
    response = "204 No Content\n";  // Successfully patched

    return response;  // Return the response
}
