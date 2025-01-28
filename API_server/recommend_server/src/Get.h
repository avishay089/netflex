#ifndef GET_H
#define GET_H
#include "DataSource.h"
#include "File.h"
#include <string>
#include "Commandable.h"
#include <unordered_map>
#include <unordered_set>
#include <map>
#include <vector>


class Get : public Commandable {
private:
    DataSource* data; // Pointer to the DataSource object (external dependency)
    // Converts the DataSource to a map
    
public:
    Get(DataSource* dataSource) : data(dataSource) {}
    string execute(std::string &command);
    std::unordered_map<int, std::unordered_set<int>> convertDataSourceToMap(DataSource& someData);
    
    // Calculates the number of common movies
    std::map<int, int> calculateCommonMovieCount(int userA, const std::unordered_map<int, std::unordered_set<int>>& userMovies);

    // Calculates movie relevance
    std::map<int, int> calculateRelevance(int userA, int movieID, const std::unordered_map<int, std::unordered_set<int>>& userMovies);

    // Gets the top 10 movies based on relevance
    std::vector<int> getTop10Movies(const std::map<int, int>& movieRelevance);

    // input check for execute
    int validateCommand(const std::string& command, int& userID, int& movieID, unordered_map<int, unordered_set<int>>& userMovies);
};

#endif // GET_H

