package com.game.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.view.*;

/**
 * La classe {@code Main} est le point d'entrée principal du jeu LibGDX.
 *
 * <p>Elle initialise les principaux gestionnaires du jeu, notamment le
 * {@link ScreenManager} pour la gestion des écrans et le {@link LevelManager}
 * pour le chargement et la gestion des niveaux.</p>
 *
 * <p>Cette classe suit le cycle de vie classique d'une application LibGDX
 * avec les méthodes {@code create}, {@code render}, et {@code dispose}.</p>
 */
public class Main extends ApplicationAdapter {
    /** Permet de dessiner les textures et les sprites. */
    private SpriteBatch batch;
    /** Gestionnaire d'écrans pour gérer les différentes scènes du jeu. */
    private ScreenManager screenManager;
    /** Gestionnaire des niveaux pour le chargement et le suivi des niveaux. */
    private LevelManager levelManager;
    /** Stage utilisé pour gérer les éléments UI. */
    private Stage stage;
    /** Gestionnaire audio pour la musique et les sons. */
    private AudioManager audioManager;

    /**
     * Méthode appelée au démarrage de l'application.
     *
     * <p>Initialise les composants principaux du jeu, notamment :</p>
     * <ul>
     *   <li>SpriteBatch pour le rendu des graphismes.</li>
     *   <li>Stage pour les éléments UI.</li>
     *   <li>LevelManager pour le contrôle des niveaux.</li>
     *   <li>ScreenManager pour la gestion des écrans.</li>
     * </ul>
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage();  // Initialiser le Stage
        audioManager = new AudioManager();  // Initialisation correcte
        audioManager.playBackgroundMusic(); // Jouer la musique de fond

        levelManager = new LevelManager(audioManager); // Passer l'instance d'AudioManager

        // Initialiser le gestionnaire d'écrans
        screenManager = new ScreenManager(stage);
        levelManager.loadLevel(1);

        // Afficher l'écran de chargement d'abord
        screenManager.setScreen(new LoadingScreen(screenManager, levelManager));
    }


    /**
     * Méthode appelée à chaque frame pour dessiner et mettre à jour le jeu.
     *
     * <p>Elle efface l'écran et délègue le rendu au gestionnaire d'écrans.</p>
     */
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Effacer l'écran à chaque frame
        screenManager.render(batch);  // Afficher l'écran actuel (via ScreenManager)
    }

    /**
     * Méthode appelée à la fermeture de l'application.
     *
     * <p>Elle libère les ressources utilisées par le jeu, notamment :</p>
     * <ul>
     *   <li>SpriteBatch</li>
     *   <li>Stage</li>
     *   <li>ScreenManager</li>
     * </ul>
     */
    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();  // Libérer les ressources du Stage
        screenManager.dispose();
        audioManager.dispose(); // Libérer les ressources audio
    }


}
