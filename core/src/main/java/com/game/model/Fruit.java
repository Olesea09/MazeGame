package com.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.controller.AudioManager;

/**
 * La classe {@code Fruit} représente un objet collectable dans le jeu.
 *
 * <p>Un fruit peut être affiché à une position spécifique et peut être collecté
 * par le joueur lorsqu'une collision est détectée.</p>
 *
 * <p>Cette classe hérite de {@link Entity} et ajoute une logique pour vérifier
 * si le fruit a été collecté (mangé).</p>
 *
 */
public class Fruit extends Entity {
    /** Indique si le fruit a été collecté (mangé) par le joueur. */
    private boolean isEaten;
    /** Largeur du fruit pour la détection de collisions et le rendu graphique. */
    private float width;
    /** Hauteur du fruit pour la détection de collisions et le rendu graphique. */
    private float height;
    /** Son de l'interface. */
    private AudioManager audioManager; // Ajouter un attribut AudioManager

    /**
     * Initialise un fruit avec des coordonnées spécifiques et une texture.
     *
     * @param x           La position initiale horizontale du fruit.
     * @param y           La position initiale verticale du fruit.
     * @param texturePath Le chemin de la texture utilisée pour afficher le fruit.
     */
    public Fruit(float x, float y, String texturePath) {
        super(x, y, texturePath); // Appelle le constructeur de Entity pour initialiser la position et la texture
        this.isEaten = false;
        this.width = 32; // Par défaut, largeur d'une tuile
        this.height = 32; // Par défaut, hauteur d'une tuile
    }

    /**
     * Affiche le fruit sur l'écran si celui-ci n'a pas encore été collecté.
     *
     * <p>Utilise la méthode {@link Entity#render(SpriteBatch)} pour dessiner le fruit.</p>
     *
     * @param batch Le {@link SpriteBatch} utilisé pour dessiner le fruit.
     */
    @Override
    public void render(SpriteBatch batch) {
        if (!isEaten) { // Ne dessine que si le fruit n'est pas mangé
            super.render(batch); // Utilise la méthode render d'Entity
        }
    }

    /**
     * Affiche le fruit avec des décalages et une échelle spécifiés.
     *
     * @param batch   Le {@link SpriteBatch} utilisé pour dessiner le fruit.
     * @param offsetX Décalage horizontal appliqué au rendu.
     * @param offsetY Décalage vertical appliqué au rendu.
     * @param scale   Facteur d'échelle appliqué à la largeur et à la hauteur.
     */
    public void render(SpriteBatch batch, float offsetX, float offsetY, float scale) {
        if (!isEaten) { // Ne dessine que si le fruit n'est pas mangé
            batch.draw(texture,
                offsetX + x * scale,
                offsetY + y * scale,
                width * scale,          // Appliquer l'échelle sur la largeur
                height * scale          // Appliquer l'échelle sur la hauteur
            );
        }
    }

    /**
     * Vérifie si le joueur entre en collision avec le fruit.
     *
     * <p>Si une collision est détectée, le fruit est marqué comme collecté.</p>
     *
     * @param playerX      Coordonnée X du joueur.
     * @param playerY      Coordonnée Y du joueur.
     * @param playerWidth  Largeur du joueur.
     * @param playerHeight Hauteur du joueur.
     * @return {@code true} si une collision est détectée et que le fruit est collecté,
     *         sinon {@code false}.
     */
    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight) {
        if (!isEaten
            && playerX < x + 32 && playerX + playerWidth > x
            && playerY < y + 32 && playerY + playerHeight > y) {
            isEaten = true; // Marque le fruit comme mangé
            return true; // Collision détectée

        }
        return false; // Pas de collision
    }
}
