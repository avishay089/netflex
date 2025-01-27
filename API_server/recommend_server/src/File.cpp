#include "File.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <cstring> 

using namespace std;

// Constructor to initialize the file. If the file does not exist, it creates one.
File::File(const string &path) : filePath(path) {
    // Check if the file exists
    ifstream inFile(filePath);
    if (!inFile) {
        // File does not exist, create a new file
        ofstream outFile(filePath);
    } else {
        // File already exists, print message
        //cout << "File already exists: " << filePath << endl;
    }
}

// Reads all the data in the file and prints each line to the console
void File::readData() {
    ifstream inFile(filePath);
    string line;
    if (inFile.is_open()) {
        // Loop through each line in the file and print it
        while (getline(inFile, line)) {
            cout << line << endl;
        }
        inFile.close();
    } else {
        cerr << "Unable to open file for reading." << endl;
    }
}

// Appends new data to the file
void File::writeData(const string &data) {
    // Open file in append mode to avoid overwriting the existing content
    ofstream outFile(filePath, ios::app); 
    if (outFile.is_open()) {
        outFile << data << endl; // Write the new data
        outFile.close();
    } else {
        cerr << "Unable to open file for writing. Error: " << strerror(errno) << endl; // Print error if unable to open file
    }
}

// Searches for a user ID in the file and returns the line number if found, otherwise returns -1
int File::search(int userId) {
    ifstream inFile(filePath);
    string line;
    int lineNumber = 0;
    if (inFile.is_open()) {
        // Loop through each line and check if the userId matches
        while (getline(inFile, line)) {
            istringstream iss(line);
            int id;
            iss >> id; // Extract the user ID from the line
            if (id == userId) {
                return lineNumber; // Return the line number if found
            }
            lineNumber++;
        }
        inFile.close();
    } else {
        cerr << "Unable to open file for reading." << endl;
    }
    return -1; // Return -1 if the user ID is not found
}

// Returns the total number of lines in the file (i.e., the size of the data)
int File::getDataSize() {
    ifstream inFile(filePath);
    string line;
    int lineNumber = 0;
    if (inFile.is_open()) {
        // Count the number of lines in the file
        while (getline(inFile, line)) {
            lineNumber++;  // Increment line number for each line in the file
        }
        inFile.close();
    } else {
        cerr << "Unable to open file for reading." << endl;
    }

    return lineNumber;  // Return the total number of lines
}

// Inserts new data into the file at the specified line number
void File::insertInto(int lineNumber, const std::vector<int>& data) {
    std::ifstream inFile(filePath);
    std::vector<std::string> lines; // Vector to hold all lines from the file
    std::string line;

    // Read the existing content into the lines vector
    while (getline(inFile, line)) {
        lines.push_back(line);
    }
    inFile.close();

    // Convert the vector of integers into a space-separated string
    std::ostringstream oss;
    for (const auto &num : data) {
        oss << num << " "; // Append each number followed by a space
    }

    // Remove the trailing space at the end of the string
    std::string dataStr = oss.str();
    if (!dataStr.empty() && dataStr[dataStr.length() - 1] == ' ') {
        dataStr.pop_back(); // Remove last character (the trailing space)
    }

    // Insert the new data at the specified line number
    if (lineNumber < lines.size()) {
        lines[lineNumber] += " " + dataStr; // Append the new data to the line
    } else {
        std::cerr << "Line number out of range." << std::endl;
        return;
    }

    // Write the updated content back to the file
    std::ofstream outFile(filePath);
    for (const auto &l : lines) {
        outFile << l << std::endl; // Write each line back to the file
    }
    outFile.close();
}

// Retrieves a specific line from the file based on the given line number
std::string File::getLine(int lineNumber) {
    std::ifstream inFile(filePath);
    if (!inFile.is_open()) {
        std::cerr << "Error: Unable to open file: " << filePath << std::endl;
        return "";
    }

    std::string line;
    int currentLine = 0;

    // Read through the file line by line
    while (std::getline(inFile, line)) {
        if (currentLine == lineNumber) {
            inFile.close();
            return line; // Return the line when the desired lineNumber is reached
        }
        currentLine++;
    }

    // If the requested line number is out of range, print an error message
    std::cerr << "Error: Line number " << lineNumber << " is out of range." << std::endl;

    inFile.close();
    return ""; // Return an empty string for out-of-range line numbers
}



void File::clearLine(int lineNumber) {
    std::ifstream inFile(filePath);
    std::vector<std::string> lines; // Vector to hold all lines from the file
    std::string line;

    // Read the existing content into the lines vector
    while (getline(inFile, line)) {
        lines.push_back(line);
    }
    inFile.close();

    // Check if the lineNumber is valid
    if (lineNumber >= 0 && lineNumber < lines.size()) {
        // Process the specific line
        std::istringstream iss(lines[lineNumber]);
        int firstInt;
        if (iss >> firstInt) {
            // Preserve the first integer and set the rest of the line to an empty string
            lines[lineNumber] = std::to_string(firstInt);  // Set only the first integer as the content
        }

        // Rewrite the file with the updated content
        std::ofstream outFile(filePath);
        for (const auto &l : lines) {
            outFile << l << std::endl; // Write each remaining line back to the file
        }
        outFile.close();
    } else {
        std::cerr << "Error: Line number " << lineNumber << " is out of range." << std::endl;
    }
}

// Clears a specific line in the file (leaves only the first integer)
// Completely removes the content of a specific line (no empty line remains)
void File::deleteLine(int lineNumber) {
    std::ifstream inFile(filePath);
    std::vector<std::string> lines; // Vector to hold all lines from the file
    std::string line;

    // Read the existing content into the lines vector
    while (getline(inFile, line)) {
        lines.push_back(line);
    }
    inFile.close();

    // Check if the lineNumber is valid
    if (lineNumber >= 0 && lineNumber < lines.size()) {
        // Remove the content of the specific line (skip adding it to the lines vector)
        lines.erase(lines.begin() + lineNumber);
    } else {
        std::cerr << "Error: Line number " << lineNumber << " is out of range." << std::endl;
        return;
    }

    // Rewrite the file with the updated content, excluding the deleted line
    std::ofstream outFile(filePath);
    for (const auto &l : lines) {
        outFile << l << std::endl; // Write each remaining line back to the file
    }
    outFile.close();
}
