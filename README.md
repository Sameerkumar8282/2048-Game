# 2048 Game

## A web-based implementation of the popular 2048 puzzle game using Java Spring Boot for the backend and React for the frontend.

## Features

- Move tiles: Up, Down, Left, Right
- Random tile generation: 2 or 4 after each move
- Score tracking: Update score in real-time
- High score persistence: Stored in database using JPA
- Win detection: Detect when 2048 tile is reached
- Game over detection: No possible moves left

### Tech Stack

- Backend: Java Spring Boot
- Database: H2 / MySQL (via Spring Data JPA)
- Frontend: React + TailwindCSS
- HTTP client: Axios
- Build tools: Maven (backend), Vite (frontend)
