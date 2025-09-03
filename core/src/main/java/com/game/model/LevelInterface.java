package com.game.model;

/**
 * L'interface {@code LevelInterface} représentant un niveau générique du jeu.
 */
public interface LevelInterface {
    /**
     * Charge les ressources nécessaires au niveau.
     */
    void load();

    /**
     * Indique si le niveau est débloqué.
     *
     * @return {@code true} si le niveau est débloqué, sinon {@code false}.
     */
    boolean isUnlocked();

    /**
     * Définit si le niveau est débloqué.
     *
     * @param unlocked {@code true} pour débloquer le niveau.
     */
    void setUnlocked(boolean unlocked);

    /**
     * Indique si le niveau est complété.
     *
     * @return {@code true} si le niveau est complété, sinon {@code false}.
     */
    boolean isCompleted();

    /**
     * Définit si le niveau est complété.
     *
     * @param completed {@code true} pour marquer le niveau comme complété.
     */
    void setCompleted(boolean completed);

    /**
     * Retourne le chemin du fichier de la carte associée au niveau.
     *
     * @return Le chemin du fichier de la carte.
     */
    String getMapFile();
}
