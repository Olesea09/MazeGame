package com.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.controller.*;
import com.game.assets.AssetPaths;

/**
 * La classe abstraite {@code EndScreen} représente un écran de fin de niveau.
 *
 * <p>Elle est utilisée pour afficher les options disponibles après la fin d'un niveau,
 * telles que rejouer le niveau, quitter le jeu ou accéder à la sélection de niveaux.</p>
 *
 * <p>Cette classe utilise le système {@link Stage} de LibGDX pour gérer les entrées utilisateur
 * et l'affichage des boutons et des éléments graphiques.</p>
 *
 */
public abstract class EndScreen {
    /** Scène (Stage) pour gérer les composants UI. */
    protected Stage stage;
    /** Bouton pour rejouer le niveau. */
    protected ImageButton replayButton;
    /** Bouton pour quitter le jeu. */
    protected ImageButton quitButton;
    /** Bouton pour accéder à l'écran de sélection des niveaux. */
    protected ImageButton levelSelectButton;
    /** Indique si le bouton "Rejouer" a été cliqué. */
    protected boolean replayClicked;
    /** Indique si le bouton "Quitter" a été cliqué. */
    protected boolean quitClicked;
    /** Image affichant le titre de l'écran (Game Over ou Winner). */
    protected Image titleImage;
    /** Gestionnaire d'écrans pour changer d'écran. */
    protected ScreenManager screenManager;
    /** Gestionnaire de niveaux pour recharger ou gérer les niveaux. */
    protected LevelManager levelManager;

    /**
     * Initialise un écran de fin avec une image de titre et des options interactives.
     *
     * @param titleImagePath Chemin de la texture pour l'image de titre.
     * @param screenManager  Gestionnaire des écrans pour naviguer entre les écrans.
     * @param levelManager   Gestionnaire des niveaux pour manipuler les niveaux.
     */
    public EndScreen(String titleImagePath, final ScreenManager screenManager, final LevelManager levelManager) {
        this.screenManager = screenManager;
        this.levelManager = levelManager;

        stage = new Stage(new ScreenViewport());

        // Charger l'image du titre
        Texture titleTexture = new Texture(titleImagePath);
        titleImage = new Image(titleTexture); // Créer un objet Image avec la texture du titre
        titleImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3); // Redimensionner l'image du titre

        // Charger les images pour les boutons
        Texture replayTexture = new Texture(AssetPaths.REPLAY_BUTTON);
        Texture quitTexture = new Texture(AssetPaths.EXIT_BUTTON);
        Texture levelSelectTexture = new Texture(AssetPaths.SELECT_BUTTON);  // Image pour "Niveaux"

        // Créer et configurer le bouton "Replay"
        replayButton = new ImageButton(new ImageButton.ImageButtonStyle());
        replayButton.getStyle().imageUp = new com.badlogic.gdx.scenes.scene2d.ui.Image(replayTexture).getDrawable();
        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                levelManager.restartLevel(); // Recharger le niveau actuel
                screenManager.setScreen(new LevelScreen(levelManager, screenManager)); // Retourner à l'écran du niveau
            }
        });

        // Créer et configurer le bouton "Quit"
        quitButton = new ImageButton(new ImageButton.ImageButtonStyle());
        quitButton.getStyle().imageUp = new com.badlogic.gdx.scenes.scene2d.ui.Image(quitTexture).getDrawable();
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quitClicked = true;
            }
        });

        // Créer et configurer le bouton "Select Level"
        levelSelectButton = new ImageButton(new ImageButton.ImageButtonStyle());
        levelSelectButton.getStyle().imageUp = new com.badlogic.gdx.scenes.scene2d.ui.Image(levelSelectTexture).getDrawable();
        levelSelectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelSelectScreen levelSelectScreen = new LevelSelectScreen(levelManager, screenManager);
                screenManager.setScreen(levelSelectScreen);  // Passer à l'écran de sélection des niveaux
                levelSelectScreen.checkProgress();  // Appeler checkProgress() pour mettre à jour les boutons
            }
        });

        // Organisation dans une table
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(titleImage).padBottom(50).row();
        table.add(replayButton).padBottom(10).row();
        table.add(quitButton).padBottom(10).row();
        table.add(levelSelectButton).padBottom(10).row();

        stage.addActor(table);
    }

    /**
     * Rend et met à jour les composants de l'écran.
     */
    public void render() {
        Gdx.input.setInputProcessor(stage); // S'assurer que cet écran gère les inputs
        stage.act();
        stage.draw();
    }

    /**
     * Vérifie si le bouton "Rejouer" a été cliqué.
     *
     * @return {@code true} si le bouton "Rejouer" a été cliqué, sinon {@code false}.
     */
    public boolean isReplayClicked() {
        boolean clicked = replayClicked;
        replayClicked = false; // Réinitialiser après utilisation
        return clicked;
    }

    /**
     * Vérifie si le bouton "Quitter" a été cliqué.
     *
     * @return {@code true} si le bouton "Quitter" a été cliqué, sinon {@code false}.
     */
    public boolean isQuitClicked() {
        boolean clicked = quitClicked;
        quitClicked = false; // Réinitialiser après utilisation
        return clicked;
    }

    /**
     * Libère les ressources utilisées par l'écran.
     */
    public void dispose() {
        stage.dispose();
    }

    /**
     * Retourne le {@link Stage} associé à l'écran.
     *
     * <p>Le stage est utilisé pour gérer les éléments UI et capturer les événements d'entrée.</p>
     *
     * @return Le {@link Stage} de l'écran.
     */
    public Stage getStage() {
        return stage;
    }
}
