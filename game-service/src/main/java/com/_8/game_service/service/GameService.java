package com._8.game_service.service;

import com._8.game_service.model.Score;
import com._8.game_service.repo.ScoreRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {
    private GameBoard board;
    private final ScoreRepo scoreRepo;

    public GameService(ScoreRepo scoreRepo) {
        this.scoreRepo = scoreRepo;
        board = new GameBoard(4);
    }

    public Map<String, Object> move(String direction) {
        boolean moved = board.move(direction);
        saveHighScoreIfNeeded(board.getScore());

        Map<String, Object> response = getGameState();
        response.put("moved", moved);
        return response;
    }


    public void restart() {
        board = new GameBoard(4);
    }

    public Map<String, Object> getGameState() {
        Map<String, Object> response = new HashMap<>();
        response.put("board", board.getBoard());
        response.put("score", board.getScore());
        response.put("highScore", getHighScore());
        response.put("isGameOver", board.isGameOver());
        response.put("hasWon", board.hasWon());
        return response;
    }

    private void saveHighScoreIfNeeded(int currentScore) {
        Score existing = scoreRepo.findAll().stream().findFirst().orElse(null);
        if (existing == null) {
            Score newScore = new Score();
            newScore.setHighScore(currentScore);
            scoreRepo.save(newScore);
        } else if (currentScore > existing.getHighScore()) {
            existing.setHighScore(currentScore);
            scoreRepo.save(existing);
        }
    }

    public int getHighScore() {
        return scoreRepo.findAll().stream()
                .mapToInt(Score::getHighScore)
                .max()
                .orElse(0);
    }
}
