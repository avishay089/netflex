cmake_minimum_required(VERSION 3.14)
project(my_project)

# GoogleTest requires at least C++14
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Find threading library
find_package(Threads REQUIRED)

# Include all source files in the src directory
file(GLOB SOURCES
    *.cpp
)
# Add the executable
add_executable(server.exe ${SOURCES})

# Link pthread (threading) library to the executable
target_link_libraries(server.exe Threads::Threads)