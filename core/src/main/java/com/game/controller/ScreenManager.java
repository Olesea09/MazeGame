package com.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.view.*;

/**
 * La classe {@code ScreenManager} gère la transition et le rendu des écrans du jeu.
 *
 * <p>Elle permet de basculer entre différents écrans de jeu ({@link GameScreen}),
 * de gérer les entrées utilisateur et de libérer correctement les ressources lorsque
 * l'écran change.</p>
 *
 * <p>Cette classe utilise un {@link Stage} pour afficher les éléments de l'interface utilisateur.</p>
 */
public class ScreenManager {
    /** L'écran actuellement affiché. */
    private GameScreen currentScreen;
    /** Stage utilisé pour gérer les éléments UI. */
    private Stage stage;

    /**
     * Initialise un gestionnaire d'écrans avec un {@link Stage} spécifié.
     *
     * @param stage Le {@link Stage} utilisé pour afficher les éléments UI.
     */
    public ScreenManager(Stage stage) {
        this.stage = stage;  // Passer le stage au ScreenManager
    }

    /**
     * Définit un nouvel écran à afficher.
     *
     * <p>Cette méthode s'occupe de masquer l'écran précédent, de désactiver les
     * entrées utilisateur pour celui-ci et d'afficher le nouvel écran en activant
     * les entrées pour celui-ci.</p>
     *
     * @param screen Le nouvel écran à afficher, implémentant {@link GameScreen}.
     */
    public void setScreen(GameScreen screen) {
        if (currentScreen != null) {
            currentScreen.hide();  // Cache l'écran actuel
            Gdx.input.setInputProcessor(null);  // Désactive les entrées pour cet écran
        }
        currentScreen = screen;  // Définit le nouvel écran
        currentScreen.show();  // Affiche le nouvel écran
        Gdx.input.setInputProcessor(currentScreen.getStage());  // Réactive les entrées pour le nouvel écran
    }

    /**
     * Rendu de l'écran actuel.
     *
     * <p>Cette méthode est appelée à chaque frame pour dessiner les éléments de l'écran actuel.</p>
     *
     * @param batch Le {@link SpriteBatch} utilisé pour dessiner les textures et les éléments graphiques.
     */
    public void render(SpriteBatch batch) {
        if (currentScreen != null) {
            currentScreen.render(batch);
        }
    }

    /**
     * Libère les ressources de l'écran actuel.
     *
     * <p>Cette méthode garantit que les ressources de l'écran actuellement affiché
     * sont correctement libérées avant de quitter ou de changer d'écran.</p>
     */
    public void dispose() {
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }

}
