#ifndef COMMANDABLE_H
#define COMMANDABLE_H

#include <string>
using namespace std;

/**
 * @class Commandable
 * @brief An abstract base class that defines a command interface.
 *
 * The Commandable class provides a pure virtual function `execute` that must be
 * implemented by any derived class. This function is intended to perform some
 * action on a given string.
 */
class Commandable {
public:
    /**
     * @brief Pure virtual function to execute a command on a string.
     * 
     * This function must be overridden by any class that inherits from Commandable.
     * 
     * @param str A reference to a string on which the command will be executed.
     */
    virtual string execute(string &str) = 0;

    /**
     * @brief Virtual destructor to ensure proper cleanup of derived classes.
     */
    virtual ~Commandable() = default; // Add this line
};

#endif // COMMANDABLE_H
