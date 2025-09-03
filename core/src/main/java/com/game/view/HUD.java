package com.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.assets.AssetPaths;

/**
 * La classe {@code HUD} (Heads-Up Display) affiche les informations essentielles au joueur,
 * telles que le nombre de vies restantes.
 *
 * <p>Elle utilise une texture pour représenter les cœurs et les affiche à l'écran en fonction
 * du nombre de vies actuelles du joueur.</p>
 *
 * <p>Cette classe est destinée à être rendue à chaque frame pendant le jeu.</p>
 *
 */
public class HUD {
    /** Texture utilisée pour représenter une vie (cœur). */
    private Texture heartTexture;
    /** Nombre maximum de vies du joueur. */
    private int maxLives;

    /**
     * Initialise le HUD avec le nombre maximum de vies de départ du joueur.
     *
     * @param maxLives Le nombre de vies de départ.
     */
    public HUD(int maxLives) {
        this.maxLives = maxLives;
        heartTexture = new Texture(AssetPaths.HEART_TEXTURE);
    }

    /**
     * Affiche les cœurs représentant les vies du joueur à l'écran.
     *
     * <p>Les cœurs sont affichés en haut à gauche de l'écran, avec un espacement uniforme.</p>
     *
     * @param batch        Le {@link SpriteBatch} utilisé pour dessiner les cœurs.
     * @param currentLives Le nombre actuel de vies du joueur.
     * @param screenWidth  La largeur de l'écran (utilisée pour le positionnement relatif).
     * @param screenHeight La hauteur de l'écran (utilisée pour le positionnement relatif).
     */
    public void render(SpriteBatch batch, int currentLives, float screenWidth, float screenHeight) {
        float startX = 20;
        float startY = screenHeight - 80;

        for (int i = 0; i < currentLives; i++) {
            batch.draw(heartTexture, startX + i * 50, startY, 52, 52);
        }
    }

    /**
     * Libère les ressources associées à la texture du HUD.
     *
     * <p>Cette méthode doit être appelée lorsque le HUD n'est plus nécessaire,
     * pour éviter les fuites de mémoire.</p>
     */
    public void dispose() {
        heartTexture.dispose();
    }
}
