package com.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.game.assets.AssetPaths;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.game.assets.AssetPaths;

/**
 * La classe {@code AudioManager} gère les sons et musiques du jeu.
 */
public class AudioManager {
    private Music backgroundMusic;
    private Sound enemyHitSound;
    private Sound fruitCollectedSound;

    /**
     * Initialise les ressources audio du jeu.
     */
    public AudioManager() {
        try {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(AssetPaths.BACKGROUND_MUSIC));
            enemyHitSound = Gdx.audio.newSound(Gdx.files.internal(AssetPaths.ENEMY_HIT_SOUND));
            fruitCollectedSound = Gdx.audio.newSound(Gdx.files.internal(AssetPaths.FRUIT_COLLECTED_SOUND));

            backgroundMusic.setLooping(true); // Boucle la musique
            backgroundMusic.setVolume(0.5f); // Volume par défaut
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des fichiers audio : " + e.getMessage());
        }
    }

    /**
     * Joue la musique de fond.
     */
    public void playBackgroundMusic() {
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    /**
     * Joue le son lorsqu'un ennemi est touché.
     */
    public void playEnemyHitSound() {
        if (enemyHitSound != null) {
            enemyHitSound.play(1.0f);
        }
    }

    /**
     * Joue le son lorsqu'un fruit est collecté.
     */
    public void playFruitCollectedSound() {
        if (fruitCollectedSound != null) {
            fruitCollectedSound.play(1.0f);
        }
    }

    /**
     * Libère les ressources audio.
     */
    public void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
        if (enemyHitSound != null) {
            enemyHitSound.dispose();
        }
        if (fruitCollectedSound != null) {
            fruitCollectedSound.dispose();
        }
    }
}
