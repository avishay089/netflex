#ifndef SERVER_H
#define SERVER_H

#include <iostream>
#include <string>
#include <map>
#include <thread>
#include <cstring>
#include <netinet/in.h>
#include <unistd.h>
#include <sstream>
#include "Commandable.h"
#include "File.h"
#include "Help.h"
#include "Post.h"
#include "Patch.h"
#include "Get.h"
#include "Delete.h"
#include "ThreadPool.h"

class Server {
public:
    Server(int port, size_t threads);
    ~Server();
    std::map<std::string, Commandable*>& getCommandMap(){
        return commandMap;
    }
    void start();

private:
    int port;
    int serverSocket;
    ThreadPool* threadPool; 
    std::map<std::string, Commandable*> commandMap;

    void setupServer();
    static void handleClient(int clientSocket, std::map<std::string, Commandable*>& commandMap);
    void initializeCommandMap();
    void deleteCommandMap();
};

#endif