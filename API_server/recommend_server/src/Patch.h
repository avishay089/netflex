#ifndef PATCH_H
#define PATCH_H

#include "DataSource.h"   // Includes the header file that defines the DataSource interface
#include <string>         // Includes the header for using std::string
#include "Commandable.h"  // Includes the header file that defines the Commandable interface

// The Add class is responsible for executing the "add" command and adding movies to the data source
class Patch : public Commandable {
private:
    DataSource* data;  // Pointer to a DataSource object (external dependency)

public:
    // Constructor: Initializes the Add object with a DataSource object
    // dataSource - A pointer to a DataSource object (e.g., File object) passed from outside
    Patch(DataSource* dataSource) : data(dataSource) {}

    // Function that executes the "add" command from the given string (command)
    // It parses the command string and adds the movies to the data source
    string execute(std::string &command);
};

#endif // PATCH_H
