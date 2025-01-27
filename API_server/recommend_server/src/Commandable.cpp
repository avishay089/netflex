
/**
 * @file Commandable.cpp
 * @brief This file contains the definition of the Commandable class.
 */

#include <string>

/**
 * @class Commandable
 * @brief An abstract base class that defines a command interface.
 *
 * The Commandable class provides an interface for executing commands.
 * Derived classes must implement the execute method.
 */
class Commandable {
public:
    /**
     * @brief Pure virtual function to execute a command.
     * 
     * This function must be overridden by derived classes to define
     * the specific command execution logic.
     * 
     * @param str A reference to a string that the command will operate on.
     */
    virtual std::string execute(std::string &str) = 0;
};
