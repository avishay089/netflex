#include <iostream>
#include "Help.h"
#include <algorithm> // for sort
using namespace std;

/**
 * @brief Constructor for the Help class.
 * 
 * Initializes the commandVector with a list of available commands.
 */
Help::Help() {
    commandVector = {
        "POST, arguments: [userid] [movieid1] [movieid2] ...",
        "GET, arguments: [userid] [movieid]",
        "PATCH, arguments: [userid] [movieid1] [movieid2] ...",
        "DELETE, arguments: [userid] [movieid1] [movieid2] ...",
        "help"
    };
}

/**
 * @brief Executes the help command.
 * 
 * Returns a string containing the list of available commands.
 * 
 * @param str A reference to a string (not used in this implementation).
 */
string Help::execute(string &str) {
    str.erase(0, str.find_first_not_of(" ")); // Remove leading spaces
    str.erase(str.find_last_not_of(" ") + 1); // Remove trailing spaces

    if (str != "help") {
        // Return an error message if the command is invalid
        return "400 Bad Request\n";
    }

    // Sort the commandVector alphabetically
    sort(commandVector.begin(), commandVector.end());

    // Build the result string with the sorted commands
    string result = "200 Ok\n\n";
    for (const auto& command : commandVector) {
        result += command + "\n";
    }

    return result; // Return the list of commands as a string
}
