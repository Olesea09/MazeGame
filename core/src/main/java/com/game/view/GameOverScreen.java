package com.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.controller.*;
import com.game.assets.AssetPaths;

/**
 * La classe {@code GameOverScreen} représente l'écran de fin de jeu lorsqu'une partie est perdue.
 */
public class GameOverScreen extends EndScreen implements GameScreen {
    /** Musique de fond pour l'écran Game Over. */
    private Music gameOverMusic;

    /**
     * Initialise un nouvel écran de fin avec une image spécifique à l'écran Game Over.
     *
     * @param screenManager Gestionnaire d'écrans pour naviguer entre les différents écrans du jeu.
     * @param levelManager  Gestionnaire de niveaux pour gérer et réinitialiser les niveaux.
     */
    public GameOverScreen(ScreenManager screenManager, LevelManager levelManager) {
        super(AssetPaths.GAMEOVER, screenManager, levelManager);  // Appel du constructeur parent

        // Charger la musique pour l'écran Game Over
        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal(AssetPaths.GAMEOVER_MUSIC));
        gameOverMusic.setLooping(false);  // Ne pas boucler
    }

    /**
     * Affiche et met à jour les éléments de l'écran Game Over.
     */
    @Override
    public void render(SpriteBatch batch) {
        super.render();

        if (isReplayClicked()) {
            levelManager.resetGame();
            screenManager.setScreen(new LevelScreen(levelManager, screenManager)); // Passer à LevelScreen
        }

        if (isQuitClicked()) {
            Gdx.app.exit();
        }
    }

    @Override
    public void show() {
        if (gameOverMusic != null && !gameOverMusic.isPlaying()) {
            gameOverMusic.play();
        }
    }

    @Override
    public void hide() {
        if (gameOverMusic != null && gameOverMusic.isPlaying()) {
            gameOverMusic.stop();
        }
    }

    @Override
    public void dispose() {
        super.dispose();  // Libérer les ressources de EndScreen

        if (gameOverMusic != null) {
            gameOverMusic.dispose();
        }
    }
}
