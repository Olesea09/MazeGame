package com.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * La classe abstraite {@code Entity} représente une entité de base dans le jeu.
 *
 * <p>Elle fournit les propriétés et les méthodes fondamentales pour gérer les
 * coordonnées d'une entité, son apparence graphique avec une texture, et
 * son rendu à l'écran.</p>
 *
 * <p>Les sous-classes doivent hériter de cette classe pour représenter des entités
 * spécifiques comme des joueurs, des ennemis ou des objets du jeu.</p>
 *
 */
public abstract class Entity {
    /** Coordonnée X de l'entité sur l'écran. */
    protected float x;
    /** Coordonnée Y de l'entité sur l'écran. */
    protected float y;
    /** Texture utilisée pour représenter visuellement l'entité. */
    protected Texture texture;

    /**
     * Initialise une nouvelle entité avec des coordonnées et une texture spécifiées.
     *
     * @param startX      La position initiale sur l'axe X.
     * @param startY      La position initiale sur l'axe Y.
     * @param texturePath Le chemin du fichier de la texture.
     */
    public Entity(float startX, float startY, String texturePath) {
        x = startX;
        y = startY;
        texture = new Texture(texturePath);
    }

    /**
     * Affiche l'entité à l'écran.
     *
     * <p>La méthode utilise un {@link SpriteBatch} pour dessiner la texture de l'entité
     * à sa position actuelle avec une taille par défaut de 64x64 pixels.</p>
     *
     * @param batch Le {@link SpriteBatch} utilisé pour dessiner la texture.
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, 64, 64); // Taille par défaut 64x64
    }

    /**
     * Libère les ressources associées à la texture de l'entité.
     *
     * <p>Cette méthode doit être appelée lorsque l'entité n'est plus utilisée
     * afin d'éviter les fuites de mémoire.</p>
     */
    public void dispose() {
        texture.dispose();
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
}
