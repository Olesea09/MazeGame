package com.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.controller.*;
import com.game.model.*;
import com.game.assets.AssetPaths;


/**
 * La classe {@code LevelSelectScreen} représente l'écran de sélection des niveaux du jeu.
 *
 * <p>Elle permet au joueur de choisir un niveau parmi ceux disponibles. Les niveaux non débloqués
 * sont affichés de manière désactivée et ne sont pas accessibles.</p>
 *
 * <p>Chaque bouton de niveau est associé à un niveau spécifique, et une fois sélectionné,
 * le jeu passe à l'écran du niveau correspondant.</p>
 *
 */
public class LevelSelectScreen implements GameScreen {
    /** Table pour organiser les boutons de sélection des niveaux. */
    private Table table;
    /** Boutons pour accéder aux différents niveaux. */
    private ImageButton level1Button, level2Button, level3Button, level4Button;
    /** Gestionnaire des niveaux pour charger et gérer les niveaux. */
    private LevelManager levelManager;
    /** Gestionnaire d'écrans pour naviguer entre les écrans. */
    private ScreenManager screenManager;
    /** Scène pour gérer les composants UI. */
    private Stage stage;

    /**
     * Initialise l'écran de sélection des niveaux.
     *
     * @param levelManager  Gestionnaire des niveaux pour contrôler les niveaux débloqués.
     * @param screenManager Gestionnaire d'écrans pour naviguer entre les écrans.
     */
    public LevelSelectScreen(LevelManager levelManager, ScreenManager screenManager) {
        this.levelManager = levelManager;
        this.screenManager = screenManager;

        stage = new Stage(new ScreenViewport()); // Créez explicitement un Stage
        table = new Table();
        table.setFillParent(true);

        //Image du titre
        Texture titleTexture = new Texture(Gdx.files.internal(AssetPaths.SELECT_LEVEL));
        com.badlogic.gdx.scenes.scene2d.ui.Image titleImage = new com.badlogic.gdx.scenes.scene2d.ui.Image(titleTexture);

        titleImage.setPosition(
            (Gdx.graphics.getWidth() - titleImage.getWidth()) / 2, // Centrer horizontalement
            Gdx.graphics.getHeight() - titleImage.getHeight() - 150 // En haut avec une marge
        );

        stage.addActor(titleImage); // Ajouter directement l'image au stage

        // Créer les boutons des niveaux avec des images
        createLevelButton(AssetPaths.LEVEL1_BUTTON, "Level 1", 1);
        createLevelButton(AssetPaths.LEVEL2_BUTTON, "Level 2", 2);
        createLevelButton(AssetPaths.LEVEL3_BUTTON, "Level 3", 3);
        createLevelButton(AssetPaths.LEVEL4_BUTTON, "Level 4", 4);

        // Ajouter la table au stage
        stage.addActor(table);

        // Vérifier la progression des niveaux
        checkProgress();

        // S'assurer que cet écran gère les inputs
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Crée un bouton de sélection de niveau.
     *
     * @param imagePath Chemin de l'image du bouton.
     * @param levelName Nom du niveau.
     * @param level     Numéro du niveau.
     */
    private void createLevelButton(String imagePath, String levelName, final int level) {
        Texture levelTexture = new Texture(Gdx.files.internal(imagePath));
        com.badlogic.gdx.scenes.scene2d.ui.Image image = new com.badlogic.gdx.scenes.scene2d.ui.Image(levelTexture); // Créer une image
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = image.getDrawable();

        ImageButton levelButton = new ImageButton(style);

        // Si le niveau est débloqué, ajouter un listener
        if (levelManager.isLevelUnlocked(level)) {
            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    startLevel(level);  // Charger le niveau si cliqué
                }
            });
        } else {
            // Appliquer un effet visuel pour indiquer que le niveau est verrouillé
            Pixmap pixmap = new Pixmap(Gdx.files.internal(imagePath));
            Pixmap grayPixmap = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), pixmap.getFormat());

            for (int y = 0; y < pixmap.getHeight(); y++) {
                for (int x = 0; x < pixmap.getWidth(); x++) {
                    int pixel = pixmap.getPixel(x, y);
                    int r = (pixel >> 24) & 0xff; // Extraire rouge
                    int g = (pixel >> 16) & 0xff; // Extraire vert
                    int b = (pixel >> 8) & 0xff;  // Extraire bleu
                    int gray = (r + g + b) / 3;    // Calculer la moyenne pour une couleur grise
                    grayPixmap.drawPixel(x, y, (gray << 24) | (gray << 16) | (gray << 8) | 0xff);
                }
            }

            Texture grayTexture = new Texture(grayPixmap);
            grayPixmap.dispose();
            pixmap.dispose();

            style.imageUp = new com.badlogic.gdx.scenes.scene2d.ui.Image(grayTexture).getDrawable();
            levelButton.setStyle(style);

            levelButton.setDisabled(true); // Désactiver les interactions
        }

        // Ajouter le bouton à la table
        table.add(levelButton).padRight(60).size(150, 150); // Ajouter un espace horizontal entre les boutons avec padRight()

        // Assignation des boutons selon le niveau
        if (level == 1) level1Button = levelButton;
        if (level == 2) level2Button = levelButton;
        if (level == 3) level3Button = levelButton;
        if (level == 4) level4Button = levelButton;
    }

    /**
     * Affiche et met à jour les éléments de l'écran de sélection des niveaux.
     *
     * @param batch Le {@link SpriteBatch} utilisé pour dessiner les composants.
     */
    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        stage.act();
        stage.draw();
        batch.end();
    }

    /**
     * Libère les ressources utilisées par l'écran de sélection des niveaux.
     */
    @Override
    public void dispose() {
        stage.dispose();  // Libérer les ressources
    }

    /**
     * Méthode appelée lorsque l'écran devient actif.
     *
     * <p>Restaure les entrées utilisateur et vérifie la progression des niveaux.</p>
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        checkProgress();  // Actualiser les boutons en fonction de la progression
    }

    /**
     * Démarre un niveau spécifique lorsqu'un bouton est cliqué.
     *
     * @param level Le numéro du niveau à démarrer.
     */
    public void startLevel(int level) {
        LevelInterface selectedLevel = levelManager.getLevel(level);

        if (selectedLevel != null && selectedLevel.isUnlocked()) {
            levelManager.setLastPlayedLevel(level);
            levelManager.loadLevel(level);
            screenManager.setScreen(new LevelScreen(levelManager, screenManager));
        } else {
            System.out.println("Impossible de charger le niveau " + level + ". Il est verrouillé ou introuvable.");
        }
    }


    /**
     * Vérifie la progression des niveaux et active/désactive les boutons en conséquence.
     */
    public void checkProgress() {
        level1Button.setDisabled(!levelManager.isLevelUnlocked(1));
        level2Button.setDisabled(!levelManager.isLevelUnlocked(2));
        level3Button.setDisabled(!levelManager.isLevelUnlocked(3));
        level4Button.setDisabled(!levelManager.isLevelUnlocked(4));
    }

    @Override
    public Stage getStage() {
        return stage;
    }


    /**
     * Méthode requise par l'interface GameScreen, mais non utilisée dans cette classe.
     */
    @Override
    public void hide() {
        // Non implémenté, méthode requise par GameScreen
    }

}
