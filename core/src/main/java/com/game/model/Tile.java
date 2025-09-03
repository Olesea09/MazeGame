package com.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * La classe {@code Tile} représente une tuile individuelle dans la carte du jeu.
 *
 * <p>Chaque tuile possède des coordonnées, une texture et des dimensions spécifiques.
 * Elle peut être rendue avec ou sans décalage et échelle, selon les besoins d'affichage.</p>
 *
 * <p>Cette classe est utilisée pour représenter les murs et les sols de la carte.</p>
 *
 */
public class Tile {
    /** Coordonnée X de la tuile sur la carte. */
    private float x;
    /** Coordonnée Y de la tuile sur la carte. */
    private float y;
    /** Région de texture représentant l'apparence visuelle de la tuile. */
    private TextureRegion region;
    /** Largeur de la tuile (en pixels). */
    private float width;
    /** Hauteur de la tuile (en pixels). */
    private float height;

    /**
     * Initialise une nouvelle tuile avec des coordonnées, une texture et des dimensions spécifiées.
     *
     * @param x         La position horizontale de la tuile.
     * @param y         La position verticale de la tuile.
     * @param region    La texture de la tuile.
     * @param tileWidth La largeur de la tuile.
     * @param tileHeight La hauteur de la tuile.
     */
    public Tile(float x, float y, TextureRegion region, float tileWidth, float tileHeight) {
        this.x = x;
        this.y = y;
        this.region = region;
        this.width = tileWidth;
        this.height = tileHeight;
    }

    /**
     * Affiche la tuile à sa position actuelle avec ses dimensions par défaut.
     *
     * @param batch Le {@link SpriteBatch} utilisé pour dessiner la tuile.
     */
    public void render(SpriteBatch batch) {
        batch.draw(region, x, y);
    }

    /**
     * Affiche la tuile avec un décalage et une échelle spécifiés.
     *
     * @param batch   Le {@link SpriteBatch} utilisé pour dessiner la tuile.
     * @param offsetX Décalage horizontal pour le rendu.
     * @param offsetY Décalage vertical pour le rendu.
     * @param scale   Facteur d'échelle appliqué à la largeur et à la hauteur.
     */
    public void render(SpriteBatch batch, float offsetX, float offsetY, float scale) {
        batch.draw(region, offsetX + x * scale, offsetY + y * scale, width * scale, height * scale);
    }

    /**
     * Affiche la tuile avec un décalage spécifié, sans échelle.
     *
     * @param batch   Le {@link SpriteBatch} utilisé pour dessiner la tuile.
     * @param offsetX Décalage horizontal pour le rendu.
     * @param offsetY Décalage vertical pour le rendu.
     */
    public void render(SpriteBatch batch, float offsetX, float offsetY) {
        batch.draw(region, x + offsetX, y + offsetY, region.getRegionWidth(), region.getRegionHeight());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
