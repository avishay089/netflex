#ifndef DATASOURCE_H
#define DATASOURCE_H

#include <vector>    // For using std::vector
#include <string>    // For using std::string

// Abstract base class representing a data source (such as a file, database, etc.)
// Any concrete class that needs to interact with data should inherit from DataSource.
class DataSource {
public:
    // Virtual destructor to allow proper cleanup when derived classes are deleted through a pointer to base class
    virtual ~DataSource() = default;

    // Pure virtual function to read data from the data source
    // This function must be implemented by any derived class.
    virtual void readData() = 0;

    // Pure virtual function to write data to the data source
    // This function must be implemented by any derived class.
    virtual void writeData(const std::string &data) = 0;

    // Pure virtual function to insert data into a specific line number
    // This function must be implemented by any derived class.
    // lineNumber - The index of the line to insert into
    // data - The new data (list of movie IDs, for example) to insert
    virtual void insertInto(int lineNumber, const std::vector<int> &data) = 0;

    // Pure virtual function to search for a specific user by their userId
    // Returns the index of the user's data if found, or -1 if not found
    virtual int search(int userId) = 0;

    // New pure virtual function to retrieve a specific line of data based on line number
    // lineNumber - The index of the line to retrieve
    virtual std::string getLine(int lineNumber) = 0;

    // Pure virtual function to get the size of the data
    // Returns the total number of data records (lines) available in the data source
    virtual int getDataSize() = 0;


    // New pure virtual function to clear a specific line (replace content with an empty string)
    // lineNumber - The index of the line to clear
    virtual void clearLine(int lineNumber) = 0;
    
     // Pure virtual function to clear the content of a specific line, leaving only the first integer
    virtual void deleteLine(int lineNumber) = 0;  // New method to clear the line content (except the first integer)

};

#endif // DATASOURCE_H

