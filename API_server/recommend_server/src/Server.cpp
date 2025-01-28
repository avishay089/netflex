#include "Server.h"

using namespace std;

Server::Server(int port, size_t threadPoolSize) : port(port), serverSocket(-1), threadPool(new ThreadPool(threadPoolSize)) {
    initializeCommandMap();
}

Server::~Server() {
    if (serverSocket >= 0) {
        close(serverSocket);
    }
    deleteCommandMap();
    delete threadPool;
}

void Server::initializeCommandMap() {
    const string FILE_PATH = "/usr/src/my_project/data/data.txt"; // Adjust as needed
    File* file = new File(FILE_PATH);

    commandMap = {
        {"help", new Help()},
        {"POST", new Post(file)},
        {"PATCH", new Patch(file)},
        {"GET", new Get(file)},
        {"DELETE", new Delete(file)}
    };
}

void Server::deleteCommandMap() {
    for (auto& pair : commandMap) {
        delete pair.second;
    }
    commandMap.clear();
}

void Server::setupServer() {
    serverSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (serverSocket < 0) {
        cerr << "Error creating socket" << endl;
        throw runtime_error("Socket creation failed");
    }

    struct sockaddr_in server;
    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(port);

    if (bind(serverSocket, (struct sockaddr*)&server, sizeof(server)) < 0) {
        cerr << "Error binding socket" << endl;
        throw runtime_error("Socket binding failed");
    }

    if (listen(serverSocket, 5) < 0) {
        cerr << "Error listening on socket" << endl;
        throw runtime_error("Socket listening failed");
    }
}

void Server::start() {
    setupServer();

    while (true) {
        struct sockaddr_in client;
        socklen_t clientSize = sizeof(client);
        int clientSocket = accept(serverSocket, (struct sockaddr*)&client, &clientSize);

        if (clientSocket < 0) {
            cerr << "Error accepting client connection" << endl;
            continue;
        }

        threadPool->enqueueTask([clientSocket, this] {
            handleClient(clientSocket, commandMap);
        });
    }
}

void Server::handleClient(int clientSocket, map<string, Commandable*>& commandMap) {
    char buffer[1024];
    string input, command;

    while (true) {
        int bytesReceived = recv(clientSocket, buffer, sizeof(buffer), 0);
        if (bytesReceived <= 0) {
            close(clientSocket);
            return;
        }

        input = string(buffer, 0, bytesReceived);
        istringstream stream(input);
        stream >> command;

        string output;
        if (commandMap.find(command) == commandMap.end()) {
            output = "400 Bad Request\n";
        } else {
            output = commandMap[command]->execute(input);
        }

        send(clientSocket, output.c_str(), output.size(), 0);
    }
}