package com.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.controller.*;
import com.game.assets.AssetPaths;

/**
 * La classe {@code LoadingScreen} représente l'écran de chargement affiché avant le début du jeu.
 *
 * <p>Elle utilise une barre de progression pour indiquer visuellement l'avancement du chargement.
 * Une fois le chargement terminé, elle passe automatiquement à l'écran du niveau.</p>
 *
 * <p>Cette classe utilise {@link ScreenManager} pour gérer les transitions d'écrans
 * et {@link LevelManager} pour préparer le niveau.</p>
 *
 */
public class LoadingScreen implements GameScreen {
    /** Scène pour afficher les éléments UI. */
    private Stage stage;
    /** Barre de progression indiquant l'état du chargement. */
    private ProgressBar progressBar;
    /** Étiquette affichant le pourcentage de chargement. */
    private Label percentageLabel;
    /** Indique si le chargement est terminé. */
    private boolean isReadyToStart;
    /** Progression actuelle du chargement (0-100%). */
    private float progress;
    /** Texture utilisée pour afficher le logo pendant le chargement. */
    private Texture logoTexture;
    /** Gestionnaire d'écrans pour naviguer entre les écrans. */
    private ScreenManager screenManager;
    /** Gestionnaire des niveaux pour préparer les niveaux après le chargement. */
    private LevelManager levelManager;

    /**
     * Initialise l'écran de chargement avec une barre de progression et un logo.
     *
     * @param screenManager Gestionnaire d'écrans pour gérer les transitions.
     * @param levelManager  Gestionnaire des niveaux pour charger les niveaux après le chargement.
     */
    public LoadingScreen(ScreenManager screenManager, LevelManager levelManager) {
        this.screenManager = screenManager;
        this.levelManager = levelManager;  // Initialiser avec le LevelManager

        stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal(AssetPaths.UISKIN));

        // Charger le logo
        logoTexture = new Texture(Gdx.files.internal(AssetPaths.MAZEGAME));

        // Barre de progression
        progressBar = new ProgressBar(0, 100, 1, false, skin);
        progressBar.setValue(0);

        // Label pour afficher les pourcentages
        percentageLabel = new Label("0%", skin);

        // Organisation des widgets dans la table
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // Ajouter l'image du logo
        table.add().row();
        float logoWidth = logoTexture.getWidth();
        float logoHeight = logoTexture.getHeight();
        float aspectRatio = logoWidth / logoHeight;
        float screenWidth = Gdx.graphics.getWidth();
        float newLogoWidth = screenWidth * 0.4f;
        float newLogoHeight = newLogoWidth / aspectRatio;
        table.add().size(newLogoWidth, newLogoHeight).padBottom(200).row();

        // Ajouter la barre de progression
        table.add(progressBar).width(400).padBottom(10).row();

        // Ajouter le label des pourcentages
        table.add(percentageLabel).padBottom(10).row();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

        progress = 0;
        isReadyToStart = false;
    }

    /**
     * Met à jour et affiche l'écran de chargement.
     *
     * <p>La barre de progression et le pourcentage augmentent au fil du temps.
     * Une fois le chargement terminé, l'écran passe automatiquement à l'écran du niveau.</p>
     *
     * @param batch Le {@link SpriteBatch} utilisé pour dessiner les éléments graphiques.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Mettre à jour la progression de la barre
        if (progress < 100) {
            progress += Gdx.graphics.getDeltaTime() * 50; // Progression de 50% par seconde
            progressBar.setValue(progress);
            percentageLabel.setText((int) progress + "%");
        }

        // Vérifier si le chargement est terminé
        if (progress >= 100 && !isReadyToStart) {
            isReadyToStart = true;
            // Passer à l'écran suivant après le chargement
            screenManager.setScreen(new LevelScreen(levelManager,screenManager));  // Charger le niveau 1
        }

        // Rendre la scène de chargement
        stage.act();
        stage.draw();

        // Dessiner le logo au centre tout en respectant le rapport d'aspect
        batch.begin();
        float logoWidth = logoTexture.getWidth();
        float logoHeight = logoTexture.getHeight();
        float aspectRatio = logoWidth / logoHeight;
        float screenWidth = Gdx.graphics.getWidth();
        float newLogoWidth = screenWidth * 0.4f;
        float newLogoHeight = newLogoWidth / aspectRatio;

        // Calculer la position de l'image pour la centrer
        float x = (screenWidth - newLogoWidth) / 2f;
        float y = (Gdx.graphics.getHeight() - newLogoHeight) / 2f;

        batch.draw(logoTexture, x, y, newLogoWidth, newLogoHeight); // Dessiner l'image du logo
        batch.end();
    }

    /**
     * Libère les ressources utilisées par l'écran de chargement.
     */
    @Override
    public void dispose() {
        stage.dispose();
        logoTexture.dispose();
    }

    @Override
    public Stage getStage() {
        return stage;  // Retourner le stage associé à cet écran
    }

    /**
     * Méthode requise par l'interface GameScreen, mais non utilisée dans cette classe.
     */
    @Override
    public void hide() {
        // Non implémenté, méthode requise par GameScreen
    }

    /**
     * Méthode requise par l'interface GameScreen, mais non utilisée dans cette classe.
     */
    @Override
    public void show() {
        // Non implémenté, méthode requise par GameScreen
    }

}
