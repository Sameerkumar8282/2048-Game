import React, { useEffect, useState } from "react";
import axios from "axios";

const API_BASE = "https://two048-game-oz5a.onrender.com/api/game";

export default function GameBoard() {
  const [board, setBoard] = useState([]);
  const [score, setScore] = useState(0);
  const [highScore, setHighScore] = useState(0);
  const [gameOver, setGameOver] = useState(false);
  const [won, setWon] = useState(false);
  const [lastMove, setLastMove] = useState("");

  useEffect(() => {
  fetchBoard();

  const handleKey = (e) => {
    if (gameOver || won) return;

    const directions = {
      ArrowUp: "UP",
      ArrowDown: "DOWN",
      ArrowLeft: "LEFT",
      ArrowRight: "RIGHT",
    };
    if (directions[e.key]) {
      e.preventDefault();
      move(directions[e.key]);
    }
  };

  window.addEventListener("keydown", handleKey);
  return () => window.removeEventListener("keydown", handleKey);
}, [gameOver, won]);


  const fetchBoard = async () => {
    const res = await axios.get(API_BASE);
    updateGame(res.data);
  };

  const move = async (direction) => {
  setLastMove(direction);
  const res = await axios.post(`${API_BASE}/move?direction=${direction}`);
  
  if (res.data.moved) {
    updateGame(res.data);
  }
  };


  const restart = async () => {
    const res = await axios.post(`${API_BASE}/restart`);
    updateGame(res.data);
    setLastMove("");
  };

  const updateGame = (data) => {
    setBoard(data.board);
    setScore(data.score);
    setHighScore(data.highScore);
    setGameOver(data.isGameOver);
    setWon(data.hasWon);
  };

  const getTileColor = (num) => {
    const colors = {
      0: "bg-gray-200",
      2: "bg-yellow-100 text-gray-700",
      4: "bg-yellow-200 text-gray-700",
      8: "bg-orange-300 text-white",
      16: "bg-orange-400 text-white",
      32: "bg-orange-500 text-white",
      64: "bg-red-500 text-white",
      128: "bg-green-400 text-white",
      256: "bg-green-500 text-white",
      512: "bg-blue-500 text-white",
      1024: "bg-purple-500 text-white",
      2048: "bg-pink-500 text-white",
    };
    return colors[num] || "bg-gray-400 text-white";
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 select-none">
      <h1 className="text-4xl font-bold mb-3">2048 Game </h1>

      <div className="flex gap-6 mb-4">
        <p className="text-lg font-semibold">Score: {score}</p>
        <p className="text-lg font-semibold text-blue-700">
          High Score: {highScore}
        </p>
      </div>

      {lastMove && (
        <p className="text-sm text-gray-600 mb-2 animate-pulse">
          Last Move: {lastMove}
        </p>
      )}

      <div className="grid grid-cols-4 gap-2 bg-gray-300 p-4 rounded-xl shadow-lg transition-all">
        {board.flat().map((num, i) => (
          <div
            key={i}
            className={`w-20 h-20 flex items-center justify-center font-bold text-2xl rounded-md transform transition-all duration-200 ${getTileColor(
              num
            )}`}
          >
            {num !== 0 ? num : ""}
          </div>
        ))}
      </div>

      <div className="mt-6 flex gap-4">
        <button
          onClick={restart}
          className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 transition-all"
        >
          Restart
        </button>
      </div>

      {gameOver && (
        <h2 className="text-2xl font-bold text-red-600 mt-4">Game Over </h2>
      )}
      {won && (
        <h2 className="text-2xl font-bold text-green-600 mt-4">You Win </h2>
      )}
    </div>
  );
}
