package com.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.controller.AudioManager;

/**
 * La classe {@code Enemy} représente un ennemi dans le jeu.
 *
 * <p>Les ennemis peuvent se déplacer horizontalement ou verticalement, détecter les collisions
 * avec les murs et interagir avec le joueur.</p>
 *
 * <p>Cette classe hérite de {@link Entity} et implémente l'interface {@link Movable} pour gérer
 * les déplacements.</p>
 *
 */
public class Enemy extends Entity implements Movable {
    /** Indique si l'ennemi est actif. */
    private boolean isActive;
    /** Largeur de l'ennemi. */
    private float width;
    /** Hauteur de l'ennemi. */
    private float height;
    /** Vitesse de déplacement de l'ennemi (en pixels par seconde). */
    private float speed = 100f;
    /** Direction actuelle pour les mouvements horizontaux. */
    private boolean movingRight = true;
    /** Direction actuelle pour les mouvements verticaux. */
    private boolean movingUp = true;
    /** Définit si l'ennemi se déplace verticalement ou horizontalement. */
    private boolean isVertical;

    /**
     * Initialise un nouvel ennemi avec ses coordonnées, ses textures et son type de mouvement.
     *
     * @param x                  La coordonnée X initiale de l'ennemi.
     * @param y                  La coordonnée Y initiale de l'ennemi.
     * @param horizontalTexturePath Le chemin de la texture pour les déplacements horizontaux.
     * @param verticalTexturePath   Le chemin de la texture pour les déplacements verticaux.
     * @param isVertical         Définit si l'ennemi se déplace verticalement.
     */
    public Enemy(float x, float y, String horizontalTexturePath, String verticalTexturePath, boolean isVertical) {
        super(x, y, isVertical ? verticalTexturePath : horizontalTexturePath);
        this.width = 32;
        this.height = 32;
        this.isVertical = isVertical;
        this.isActive = true;
    }

    /**
     * Déplace l'ennemi en ajoutant des valeurs aux coordonnées actuelles.
     *
     * @param deltaX Le déplacement horizontal.
     * @param deltaY Le déplacement vertical.
     */
    @Override
    public void move(float deltaX, float deltaY) {
        x += deltaX;
        y += deltaY;
    }

    /**
     * Vérifie si l'ennemi se déplace horizontalement.
     *
     * @return {@code true} si l'ennemi se déplace horizontalement, sinon {@code false}.
     */
    public boolean isHorizontal() {
        return !isVertical;
    }

    /**
     * Vérifie si l'ennemi se déplace verticalement.
     *
     * @return {@code true} si l'ennemi se déplace verticalement, sinon {@code false}.
     */
    public boolean isVertical() {
        return isVertical;
    }

    /**
     * Met à jour la position de l'ennemi en fonction du temps écoulé et des collisions.
     *
     * @param deltaTime Le temps écoulé depuis la dernière mise à jour (en secondes).
     * @param gameMap   La carte du jeu utilisée pour vérifier les collisions.
     */
    public void update(float deltaTime, GameMap gameMap) {
        if (isVertical) {
            // Déplacement vertical (haut et bas)
            float deltaY = movingUp ? speed * deltaTime : -speed * deltaTime;

            // Vérifier les collisions pour changer de direction
            if (movingUp && gameMap.isCollision(x, y + deltaY)) {
                movingUp = false; // Change direction vers le bas
            } else if (!movingUp && gameMap.isCollision(x, y + deltaY)) {
                movingUp = true; // Change direction vers le haut
            } else {
                move(0, deltaY);
            }
        } else {
            // Déplacement horizontal (gauche et droite)
            float deltaX = movingRight ? speed * deltaTime : -speed * deltaTime;

            // Vérifier les collisions pour changer de direction
            if (movingRight && gameMap.isCollision(x + deltaX, y)) {
                movingRight = false; // Change direction vers la gauche
            } else if (!movingRight && gameMap.isCollision(x + deltaX, y)) {
                movingRight = true; // Change direction vers la droite
            } else {
                move(deltaX, 0);
            }
        }
    }

    /**
     * Affiche l'ennemi à l'écran.
     *
     * @param batch   Le {@link SpriteBatch} utilisé pour dessiner les textures.
     * @param offsetX Décalage horizontal appliqué au rendu.
     * @param offsetY Décalage vertical appliqué au rendu.
     * @param scale   Facteur d'échelle pour les dimensions.
     */
    public void render(SpriteBatch batch, float offsetX, float offsetY, float scale) {
        batch.draw(texture,
            offsetX + x * scale,
            offsetY + y * scale,
            width * scale,
            height * scale
        );
    }

    /**
     * Vérifie si l'ennemi entre en collision avec le joueur.
     *
     * @param playerX      La coordonnée X du joueur.
     * @param playerY      La coordonnée Y du joueur.
     * @param playerWidth  La largeur du joueur.
     * @param playerHeight La hauteur du joueur.
     * @return {@code true} si une collision est détectée, sinon {@code false}.
     */
    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight) {
        return isActive
            && playerX < x + width && playerX + playerWidth > x
            && playerY < y + height && playerY + playerHeight > y;

    }
}
