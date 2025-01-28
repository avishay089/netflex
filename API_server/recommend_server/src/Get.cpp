#include "Get.h"
#include <sstream>
#include <algorithm>
#include <iostream>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <map>
#include <algorithm>
#include "DataSource.h"
#include "File.h" 
#include <fstream>
#include <regex> // For regex-based integer validation


// Get class implementation for generating movie recommendations based on users' movie preferences.
using namespace std;

/*
This function converts the data from a DataSource into an unordered_map where:
- The key is the user ID (from the first column),
- The value is a set of movies the user has watched (from the second column).
The function assumes the data source has two columns: user ID and movie IDs.
*/
unordered_map<int, unordered_set<int>> Get::convertDataSourceToMap(DataSource& someData) {
    unordered_map<int, unordered_set<int>> returnedMap;
    int someDataSize = someData.getDataSize();  // Get the number of rows in the data source.
    
    for (int i = 0; i < someDataSize; ++i) { // Iterate through the data rows.
        string line = someData.getLine(i); // Get each line of the data.

        if (!line.empty()) { // Skip empty lines.
            istringstream iss(line); // Create a stream from the line.
            int key;
            unordered_set<int> values; // Set to store movie IDs (unique).

            // Extract the user ID (key) from the line.
            iss >> key;

            // Extract the movie IDs (values) from the rest of the line.
            int value;
            while (iss >> value) {
                values.insert(value); // Insert movie IDs into the set to avoid duplicates.
            }

            // Add the user ID and their movie set to the returned map.
            returnedMap[key] = values;
        }
    }

    return returnedMap; // Return the populated map.
}

/*
This function calculates the number of common movies between User A and all other users.
It returns a map where:
- The key is another user ID,
- The value is the number of common movies between User A and the other user.
*/
map<int, int> Get::calculateCommonMovieCount(int userA, const unordered_map<int, unordered_set<int>>& userMovies) {
    map<int, int> commonMovieCount; // Map to store the number of common movies for each user.

    // Find User A's movie set from the userMovies map.
    auto it = userMovies.find(userA);
    if (it == userMovies.end()) {
        cout << "User A not found!" << endl;
        return commonMovieCount; // Return empty map if User A is not found.
    }

    const unordered_set<int>& moviesA = it->second; // Get the movie set of User A.

    // Iterate through all other users in userMovies.
    for (const auto& pair : userMovies) {
        int userB = pair.first;
        if (userB == userA) continue; // Skip comparing with User A itself.

        const unordered_set<int>& moviesB = pair.second; // Get movies watched by User B.
        int commonCount = 0; // Initialize the counter for common movies.

        // Check for common movies between User A and User B.
        for (int movie : moviesA) {
            if (moviesB.find(movie) != moviesB.end()) {
                commonCount++; // Increment counter if a common movie is found.
            }
        }

        // If there are any common movies, add the count to the map.
        if (commonCount > 0) {
            commonMovieCount[userB] = commonCount;
        }
    }

    return commonMovieCount; // Return the map of common movie counts.
}

/*
This function calculates the relevance of a given movie for User A.
Relevance is based on common movie preferences with other users and their interest in the target movie (movieID).
It returns a map where:
- The key is the movie ID,
- The value is the relevance score (sum of the common movie counts from similar users).
*/
map<int, int> Get::calculateRelevance(int userA, int movieID, const unordered_map<int, unordered_set<int>>& userMovies) {
    // Step 1: Get common movies between User A and other users.
    map<int, int> commonMoviesMap = calculateCommonMovieCount(userA, userMovies);

    // Step 2: Initialize map to store relevance of movies.
    map<int, int> movieRelevance;

    // Check if user A exists in the userMovies map.
    auto it = userMovies.find(userA);
    if (it == userMovies.end()) {
        cout << "User A not found!" << endl;
        return movieRelevance; // Return empty map if User A is not found.
    }
    // Find User A's movie set (to exclude the target movie).
    const unordered_set<int>& moviesA = userMovies.at(userA);

    // Step 3: Iterate through all users to find relevance based on common interests.
    for (const auto& userPair : userMovies) {
        int user = userPair.first; // Current user ID.
        if (user == userA) continue; // Skip User A itself.
        const unordered_set<int>& moviesWatched = userPair.second; // Movies watched by the current user.
        // Step 4: Check if the user has watched the target movie (movieID).
        if (moviesWatched.find(movieID) != moviesWatched.end()) {
            // Step 5: Get the similarity weight based on common movies.
            int weight = commonMoviesMap.count(user) ? commonMoviesMap[user] : 0;

            // Step 6: Add the weight to all movies this user has watched, excluding the target movie.
            for (int movie : moviesWatched) {
                if (movie != movieID) {
                    movieRelevance[movie] += weight; // Update relevance score for each movie.
                }
            }
        }
    }

    // Step 7: Remove the movies User A has already watched from the relevance map.
    for (int movie : moviesA) {
        movieRelevance.erase(movie);
    }

    return movieRelevance; // Return the relevance map for the target movie.
}

/*
This function sorts the relevance map and returns the top 10 recommended movies.
It sorts the map first by relevance score (in descending order), then by movie ID (in ascending order).
*/
vector<int> Get::getTop10Movies(const map<int, int>& movieRelevance) {
    // Convert the map into a vector of pairs for sorting.
    vector<pair<int, int>> sortedRelevance(movieRelevance.begin(), movieRelevance.end());

    // Sort the vector by relevance (value) in descending order.
    sort(sortedRelevance.begin(), sortedRelevance.end(), [](const pair<int, int>& a, const pair<int, int>& b) {
        if (a.second == b.second) {
            return a.first < b.first; // Sort by movie ID in ascending order if relevance is equal.
        }
        return a.second > b.second; // Sort by relevance in descending order.
    });

    // Extract the top 10 movies from the sorted vector.
    vector<int> top10Movies;
    for (size_t i = 0; i < sortedRelevance.size() && i < 10; ++i) {
        top10Movies.push_back(sortedRelevance[i].first); // Add the movie ID to the result.
    }

    return top10Movies; // Return the top 10 recommended movie IDs.
}
int Get::validateCommand(const std::string& command, int& userID, int& movieID, unordered_map<int, unordered_set<int>>& userMovies) {
    // Parse the command using a string stream.
    std::istringstream iss(command);
    std::vector<std::string> components;
    std::string word;

    // Split the command into components.
    while (iss >> word) {
        components.push_back(word);
    }

    // Step 1: Check if exactly three components are present.
    if (components.size() != 3) {
        // std::cerr << "Invalid command format. Expected: 'GET <userID> <movieID>'." << std::endl;
        return 400;
    }

    // Step 2: Ensure the first word is "GET".
    if (components[0] != "GET") {
        // std::cerr << "Invalid command. Expected 'GET' as the first word." << std::endl;
        return 400;
    }

    // Step 3: Validate that userID and movieID are integers.
    std::regex integerRegex("^[0-9]+$");
    if (!std::regex_match(components[1], integerRegex) || !std::regex_match(components[2], integerRegex)) {
        // std::cerr << "Invalid input. User ID and Movie ID must be integers." << std::endl;
        return 400;
    }

    userID = std::stoi(components[1]);
    movieID = std::stoi(components[2]);

    // Step 4: Convert the data source to a map.
    userMovies = convertDataSourceToMap(*data);

    if (userMovies.find(userID) == userMovies.end()) {
        return 404;
    }

    // Step 8: Check if there are users with overlapping movie preferences.
    map<int, int> commonMoviesMap = calculateCommonMovieCount(userID, userMovies);
    if (commonMoviesMap.empty()) {
        //std::cout << "Not enough data to compute recommendations for User " << userID << "." << std::endl;
        return 200;
    }

    return -1;
}

/*
This function executes the recommendation command by processing the user ID and movie ID.
It calls the necessary functions to compute the recommended movies and prints the results.
*/
string Get::execute(std::string& command) {
    string response;  // Variable to store the response message

    // Validate the command and retrieve the user and movie IDs.
    int userID, movieID;
    unordered_map<int, unordered_set<int>> userMovies;

    int status = validateCommand(command, userID, movieID, userMovies);
    if (status == 400) {
        response = "400 Bad Request\n";  // Invalid command or input
        return response;
    }
    if (status == 404) {
        response = "404 Not Found\n";  // User ID not found in the data source
        return response;
    }

    // Step 9: Calculate movie relevance for the given user and movie.
    map<int, int> relevanceMap = calculateRelevance(userID, movieID, userMovies);

    // Step 10: Check if there are any movies left to recommend.
    if (relevanceMap.empty()) {
        //response = "No recommendations available for User " + to_string(userID) + ".\n";
        //return "404 Not Found\n";
    }

    // Step 11: Get the top 10 recommended movies.
    vector<int> vec = getTop10Movies(relevanceMap);
    response = "200 Ok\n\n";
    if (!vec.empty()) {
        for (int movie : vec) {
            response += to_string(movie) + " "; // Add each recommended movie ID to the response
        }
    }
    response += "\n";

    return response;  // Return the response string
}
