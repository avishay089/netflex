#ifndef FILE_H
#define FILE_H

#include "DataSource.h"   // Include the base class DataSource for abstraction
#include <string>         // Include string library for handling strings
#include <vector>         // Include vector library for handling dynamic arrays

class File : public DataSource { // Define the File class inheriting from DataSource
private:
    std::string filePath; // Path to the file

public:
    // Constructor for File class, accepts file path
    File(const std::string &path);

    // Override the readData function to read the data from the file
    void readData() override;

    // Override the writeData function to write new data to the file
    void writeData(const std::string &data) override;

    // Override the insertInto function to insert data at a specific line in the file
    void insertInto(int lineNumber, const std::vector<int> &data) override;

    // Override the search function to search for a user by ID in the file
    int search(int userId) override;

    // Override the getLine function to retrieve a specific line from the file by line number
    std::string getLine(int lineNumber) override;

    // Override the getDataSize function to get the total number of lines in the file
    int getDataSize() override;

    
    // Function to clear a specific line in the file (replace content with an empty string)
    void clearLine(int lineNumber) override;

      // Override the deleteLine function to clear content of a specific line (leaving the first integer)
    void deleteLine(int lineNumber) override;
};

#endif // FILE_H