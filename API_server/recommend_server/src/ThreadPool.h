#ifndef THREADPOOL_H
#define THREADPOOL_H

#include <vector>
#include <queue>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <functional>

class ThreadPool {
public:
    explicit ThreadPool(size_t numThreads); // Constructor
    ~ThreadPool(); // Destructor

    void enqueueTask(std::function<void()> task); // Add a task

private:
    std::vector<std::thread> workers; // Worker threads
    std::queue<std::function<void()>> tasks; // Task queue
    std::mutex queueMutex; // Mutex for queue access
    std::condition_variable condition; // Condition variable
    bool stop; // Stopping flag

    void workerThread(); // Worker thread function
};

#endif // THREADPOOL_H
