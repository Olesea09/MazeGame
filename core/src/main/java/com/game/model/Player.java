package com.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import com.game.assets.AssetPaths;
import com.game.controller.AudioManager;


/**
 * La classe {@code Player} représente le joueur dans le jeu.
 *
 * <p>Elle gère les déplacements, les interactions avec les objets du jeu (fruits, ennemis),
 * la détection des collisions, et le statut du joueur (vies, invulnérabilité).</p>
 *
 * <p>Cette classe hérite de {@link Entity} et implémente l'interface {@link Movable}
 * pour permettre les déplacements.</p>
 *
 */
public class Player extends Entity implements Movable {
    /** Nombre de vies du joueur. */
    private int lives;
    /** Largeur du joueur pour la détection de collisions. */
    private float width;
    /** Hauteur du joueur pour la détection de collisions. */
    private float height;
    /** Temps restant d'invulnérabilité après une collision. */
    private float invulnerabilityTime;
    /** Durée totale d'invulnérabilité après une collision (en secondes). */
    private static final float INVULNERABILITY_DURATION = 2.5f;
    /** Indique si le joueur est en état "touché" après une collision. */
    private boolean isHit;
    /** Timer pour l'effet de clignotement visuel après une collision. */
    private float hitTimer;
    /** Durée de chaque clignotement pendant l'invulnérabilité (en secondes). */
    private static final float HIT_EFFECT_DURATION = 0.2f;
    private AudioManager audioManager;    /**
     * Initialise un joueur avec une position initiale spécifiée.
     *
     * @param startX La position initiale horizontale du joueur.
     * @param startY La position initiale verticale du joueur.
     */
    public Player(float startX, float startY, AudioManager audioManager) {
        super(startX, startY, AssetPaths.PLAYER_TEXTURE);
        if (audioManager == null) {
            throw new IllegalArgumentException("AudioManager ne peut pas être null");
        }
        this.audioManager = audioManager;
        lives = 3;
        this.width = 32;
        this.height = 32;
        this.invulnerabilityTime = 0;
        this.isHit = false;
        this.hitTimer = 0;
    }


    /**
     * Déplace le joueur selon les valeurs spécifiées sur les axes X et Y.
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
     * Gère les entrées clavier pour déplacer le joueur.
     *
     * @param gameMap La carte actuelle du jeu pour vérifier les collisions.
     */
    public void handleInput(GameMap gameMap) {
        float deltaX = 0, deltaY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) deltaY = 2;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) deltaY = -2;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) deltaX = -2;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) deltaX = 2;

        if (!gameMap.isCollision(x + deltaX, y + deltaY)) {
            move(deltaX, deltaY);
        }
    }

    /**
     * Gère les collisions avec les fruits et met à jour les vies du joueur.
     *
     * @param fruits La liste des fruits présents sur la carte.
     */
    public void handleFruitCollision(List<Fruit> fruits) {
        for (Fruit fruit : fruits) {
            if (fruit.checkCollision(x, y, width, height)) {
                lives++;
                System.out.println("Fruit mangé ! Vies restantes : " + lives);
                audioManager.playFruitCollectedSound(); // Son de collecte
            }
        }
    }

    /**
     * Gère les collisions avec les ennemis et met à jour les vies du joueur.
     *
     * @param enemies La liste des ennemis présents sur la carte.
     */

    public void handleEnemyCollision(List<Enemy> enemies) {
        if (invulnerabilityTime > 0) {
            invulnerabilityTime -= Gdx.graphics.getDeltaTime();
            return;
        }

        for (Enemy enemy : enemies) {
            if (enemy.checkCollision(x, y, width, height)) {
                if (enemy.isHorizontal()) {
                    lives -= 2;
                } else {
                    lives--;
                }
                invulnerabilityTime = INVULNERABILITY_DURATION;
                isHit = true;
                hitTimer = HIT_EFFECT_DURATION;
                audioManager.playEnemyHitSound(); // Son de collision avec ennemi
                break;
            }
        }
    }
    /**
     * Met à jour l'état du joueur, notamment la gestion de l'invulnérabilité et du clignotement.
     *
     * @param deltaTime Temps écoulé depuis la dernière frame.
     */
    public void update(float deltaTime) {
        if (invulnerabilityTime > 0) {
            invulnerabilityTime -= deltaTime;
            hitTimer -= deltaTime;

            if (hitTimer <= 0) {
                hitTimer = HIT_EFFECT_DURATION;
            }
        } else {
            isHit = false; // Désactiver l'état "touché" une fois l'invulnérabilité terminée
        }
    }

    /**
     * Affiche le joueur à l'écran avec un effet de clignotement si le joueur est en état "touché".
     *
     * @param batch   Le {@link SpriteBatch} utilisé pour dessiner le joueur.
     * @param offsetX Décalage horizontal pour le rendu.
     * @param offsetY Décalage vertical pour le rendu.
     * @param scale   Facteur d'échelle appliqué au rendu.
     */
    public void render(SpriteBatch batch, float offsetX, float offsetY, float scale) {
        if (!isHit || (Math.floor(hitTimer * 10) % 2 == 0)) {
            batch.draw(texture, offsetX + x * scale, offsetY + y * scale, width * scale, height * scale);
        }
    }

    public int getLives() {
        return lives;
    }

}
