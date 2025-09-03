package com.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.game.model.*;

/**
 * La classe {@code LevelRenderer} gère le rendu des éléments de la carte du niveau.
 *
 * <p>Cette classe est responsable de dessiner les différents composants visuels
 * d'un niveau, notamment :</p>
 * <ul>
 *   <li>Les murs</li>
 *   <li>Les fruits collectables</li>
 *   <li>Les ennemis</li>
 *   <li>Le joueur</li>
 * </ul>
 *
 * <p>Elle utilise {@link SpriteBatch} pour dessiner les textures et {@link ShapeRenderer}
 * pour tout dessin supplémentaire si nécessaire.</p>
 */
public class LevelRenderer {
    /** La carte du jeu contenant tous les éléments à dessiner. */
    private GameMap gameMap;

    /** Utilisé pour des rendus graphiques supplémentaires si nécessaire. */
    private ShapeRenderer shapeRenderer;

    /**
     * Initialise le gestionnaire de rendu avec une carte du jeu.
     *
     * @param gameMap La carte du jeu contenant les entités et les éléments à afficher.
     */
    public LevelRenderer(GameMap gameMap) {
        this.gameMap = gameMap;
        this.shapeRenderer = new ShapeRenderer();
    }

    /**
     * Affiche les éléments du niveau à l'écran.
     *
     * <p>Cette méthode dessine successivement :</p>
     * <ul>
     *   <li>Les murs</li>
     *   <li>Les fruits</li>
     *   <li>Les ennemis</li>
     *   <li>Le joueur</li>
     * </ul>
     *
     * @param batch   {@link SpriteBatch} utilisé pour dessiner les textures.
     * @param offsetX Décalage horizontal pour le rendu, utilisé pour centrer la carte.
     * @param offsetY Décalage vertical pour le rendu, utilisé pour centrer la carte.
     * @param scale   Échelle appliquée aux éléments pour adapter la carte à l'écran.
     */
    public void render(SpriteBatch batch, float offsetX, float offsetY, float scale) {
        batch.begin();

        // Dessiner les murs
        for (Tile wall : gameMap.getWalls()) {
            wall.render(batch, offsetX, offsetY, scale);
        }

        // Dessiner les fruits
        for (Fruit fruit : gameMap.getFruits()) {
            fruit.render(batch, offsetX, offsetY, scale);
        }

        // Dessiner les ennemis
        for (Enemy enemy : gameMap.getEnemies()) {
            enemy.render(batch, offsetX, offsetY, scale);
        }

        // Dessiner le joueur
        gameMap.getPlayer().render(batch, offsetX, offsetY, scale);

        batch.end();
    }

    /**
     * Libère les ressources utilisées par le rendu du niveau.
     *
     * <p>Cette méthode doit être appelée lorsque l'objet {@code LevelRenderer} n'est plus nécessaire,
     * afin d'éviter les fuites de mémoire.</p>
     */
    public void dispose() {
        shapeRenderer.dispose();
    }
}
