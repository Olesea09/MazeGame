package com.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.controller.*;
import com.game.assets.AssetPaths;

/**
 * La classe {@code VictoryScreen} représente l'écran affiché lorsqu'un joueur termine un niveau avec succès.
 */
public class VictoryScreen extends EndScreen implements GameScreen {
    /** Musique de fond pour l'écran de victoire. */
    private Music victoryMusic;

    /**
     * Initialise l'écran de victoire avec une image spécifique et les gestionnaires nécessaires.
     *
     * @param screenManager Gestionnaire d'écrans pour gérer les transitions.
     * @param levelManager  Gestionnaire des niveaux pour manipuler les niveaux.
     */
    public VictoryScreen(ScreenManager screenManager, LevelManager levelManager) {
        super(AssetPaths.WIN, screenManager, levelManager);  // Appel du constructeur parent

        // Charger la musique pour l'écran de victoire
        victoryMusic = Gdx.audio.newMusic(Gdx.files.internal(AssetPaths.VICTORY_MUSIC));
        victoryMusic.setLooping(false);  // Ne pas boucler
    }

    /**
     * Affiche et met à jour les éléments de l'écran de victoire.
     */
    @Override
    public void render(SpriteBatch batch) {
        super.render();  // Appeler la méthode render() de EndScreen

        if (isReplayClicked()) {
            // Charger à nouveau le niveau
            levelManager.resetGame();
            screenManager.setScreen(new LevelScreen(levelManager, screenManager)); // Passer à LevelScreen
        }

        if (isQuitClicked()) {
            Gdx.app.exit();  // Quitter le jeu
        }
    }

    @Override
    public void show() {
        if (victoryMusic != null && !victoryMusic.isPlaying()) {
            victoryMusic.play();
        }
    }

    @Override
    public void hide() {
        if (victoryMusic != null && victoryMusic.isPlaying()) {
            victoryMusic.stop();
        }
    }

    @Override
    public void dispose() {
        super.dispose();  // Libérer les ressources de EndScreen

        if (victoryMusic != null) {
            victoryMusic.dispose();
        }
    }
}
