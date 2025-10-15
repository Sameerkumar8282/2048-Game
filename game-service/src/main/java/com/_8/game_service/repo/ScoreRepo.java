package com._8.game_service.repo;

import com._8.game_service.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepo extends JpaRepository<Score, Integer> {
}
