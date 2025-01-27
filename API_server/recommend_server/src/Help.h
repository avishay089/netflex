#ifndef HELP_H
#define HELP_H

#include <vector>
#include <string>
#include "Commandable.h"

//using namespace std;
/**
 * @file Help.h
 * @brief Declaration of the Help class.
 */

/**
 * @class Help
 * @brief A class that provides help commands.
 * 
 * The Help class is derived from the Commandable interface and is used to
 * execute help-related commands. It maintains a vector of command strings.
 */
class Help : public Commandable {
    private:
        std::vector<std::string> commandVector;
    public:
        /**
         * @brief Constructs a new Help object.
         * 
         * Initializes the Help object and sets up the command vector.
         */
        Help();
        /**
         * @brief Executes the help command.
         * 
         * @param str A reference to a string that contains the command to be executed.
         * 
         * This function overrides the execute method from the Commandable interface
         * and provides the implementation for executing help commands.
         */
        string execute(string &str) override;
        const std::vector<std::string>& getCommandVector() const {
        return commandVector;
    }
};

#endif // HELP_H