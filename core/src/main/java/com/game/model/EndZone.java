package com.game.model;

/**
 * La classe {@code EndZone} représente une zone de fin dans le jeu.
 *
 * <p>Cette zone définit une région rectangulaire où le joueur doit se rendre
 * pour atteindre la fin d'un niveau.</p>
 *
 */
public class EndZone {
    /** Coordonnée X de la zone de fin. */
    private float x;
    /** Coordonnée Y de la zone de fin. */
    private float y;
    /** Largeur de la zone de fin. */
    private float width;
    /** Hauteur de la zone de fin. */
    private float height;

    /**
     * Initialise une nouvelle zone de fin avec des coordonnées et des dimensions spécifiées.
     *
     * @param x      La coordonnée X du coin inférieur gauche de la zone.
     * @param y      La coordonnée Y du coin inférieur gauche de la zone.
     * @param width  La largeur de la zone.
     * @param height La hauteur de la zone.
     */
    public EndZone(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Vérifie si les coordonnées du joueur se trouvent dans la zone de fin.
     *
     * @param playerX La coordonnée X du joueur.
     * @param playerY La coordonnée Y du joueur.
     * @return {@code true} si le joueur est dans la zone de fin, sinon {@code false}.
     */
    public boolean isPlayerOnEnd(float playerX, float playerY) {
        return playerX < x + width && playerX + width > x &&
            playerY < y + height && playerY + height > y;
    }
}
