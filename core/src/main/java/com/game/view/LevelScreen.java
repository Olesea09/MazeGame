package com.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.game.model.*;
import com.game.controller.*;

/**
 * La classe {@code LevelScreen} représente l'écran principal où se déroule le jeu.
 *
 * <p>Elle gère l'affichage de la carte du jeu, du joueur, des ennemis, des fruits,
 * et du HUD (affichage des vies). Elle s'occupe également de gérer les transitions entre les écrans
 * de victoire et de défaite.</p>
 */
public class LevelScreen implements GameScreen {
    /** Scène pour gérer les éléments de l'interface utilisateur. */
    private Stage stage;
    /** Gestionnaire des niveaux pour charger et gérer les niveaux. */
    private LevelManager levelManager;
    /** Gestionnaire d'écrans pour naviguer entre les écrans. */
    private ScreenManager screenManager;
    /** Carte du jeu actuelle. */
    private GameMap gameMap;
    /** HUD affichant les vies restantes. */
    private HUD hud;
    /** Caméra orthographique pour afficher la carte du jeu. */
    private OrthographicCamera camera;
    /** Gestionnaire de rendu des éléments du niveau. */
    private LevelRenderer levelRenderer;
    /** Indique si le jeu est terminé avec succès. */
    private boolean gameFinished;

    /**
     * Initialise un nouvel écran de jeu avec un gestionnaire de niveaux et un gestionnaire d'écrans.
     *
     * @param levelManager  Gestionnaire des niveaux pour charger et gérer les niveaux.
     * @param screenManager Gestionnaire des écrans pour naviguer entre les écrans.
     */
    public LevelScreen(LevelManager levelManager, ScreenManager screenManager) {
        this.levelManager = levelManager;
        this.screenManager = screenManager;
        this.gameMap = levelManager.getCurrentGameMap(); // Récupération de la GameMap actuelle.
        this.hud = new HUD(3); // Initialisation du HUD.
        this.stage = new Stage(); // Initialisation de la scène.
        this.levelRenderer = new LevelRenderer(gameMap); // Initialisation du LevelRenderer.
    }

    /**
     * Méthode appelée lorsque l'écran devient actif.
     *
     * <p>Configure la caméra et vérifie si la carte du jeu est correctement chargée.</p>
     */
    @Override
    public void show() {
        gameFinished = false;

        if (gameMap == null) {
            throw new IllegalStateException("Erreur : gameMap est null. Assurez-vous que LevelManager a chargé une GameMap valide.");
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameMap.getMapWidth(), gameMap.getMapHeight());
        camera.position.set(gameMap.getMapWidth() / 2, gameMap.getMapHeight() / 2, 0);
        camera.update();
    }

    /**
     * Affiche et met à jour les éléments du niveau.
     *
     * @param batch Le {@link SpriteBatch} utilisé pour dessiner les éléments du niveau.
     */
    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Nettoyage de l'écran.

        Player player = gameMap.getPlayer();

        // Calculer les échelles et décalages pour centrer la carte.
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float mapPixelWidth = gameMap.getMapWidth() * gameMap.getTileWidth();
        float mapPixelHeight = gameMap.getMapHeight() * gameMap.getTileHeight();
        float scale = Math.min(screenWidth / mapPixelWidth, screenHeight / mapPixelHeight);
        float offsetX = (screenWidth - mapPixelWidth * scale) / 2;
        float offsetY = (screenHeight - mapPixelHeight * scale) / 2;

        // Afficher le HUD.
        batch.begin();
        hud.render(batch, player.getLives(), screenWidth, screenHeight);
        batch.end();

        // Mettre à jour les ennemis avant de les afficher
        for (Enemy enemy : gameMap.getEnemies()) {
            enemy.update(Gdx.graphics.getDeltaTime(), gameMap);
        }

        // Afficher la carte et les entités via le LevelRenderer.
        levelRenderer.render(batch, offsetX, offsetY, scale);

        // Gérer les entrées et mises à jour du joueur.
        player.handleInput(gameMap);
        player.handleFruitCollision(gameMap.getFruits());
        player.handleEnemyCollision(gameMap.getEnemies());
        player.update(Gdx.graphics.getDeltaTime());

        // Vérifier si le joueur a atteint la fin.
        if (gameMap.isPlayerInEndZone(player.getX(), player.getY())) {
            if (!gameFinished) {
                gameFinished = true;
                levelManager.setLevelCompleted();
                levelManager.unlockNextLevel();
                screenManager.setScreen(new VictoryScreen(screenManager, levelManager));
            }
        }

        // Vérifier si le joueur a perdu toutes ses vies.
        if (player.getLives() <= 0) {
            screenManager.setScreen(new GameOverScreen(screenManager, levelManager));
        }
    }

    /**
     * Libère les ressources utilisées par l'écran.
     */
    @Override
    public void dispose() {
        hud.dispose();
        levelRenderer.dispose();
        stage.dispose();
    }

    /**
     * Retourne le stage actuel de l'écran.
     *
     * @return Le {@link Stage} associé à cet écran.
     */
    @Override
    public Stage getStage() {
        return stage;
    }

    /**
     * Méthode requise par l'interface GameScreen, mais non utilisée dans cette classe.
     */
    @Override
    public void hide() {
        // Non implémenté, méthode requise par GameScreen.
    }
}
