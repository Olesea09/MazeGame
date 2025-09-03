package com.game.controller;

import com.game.model.LevelInterface;
import com.game.model.Level;
import com.game.model.GameMap;
import com.game.assets.AssetPaths;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code LevelManager} gère la progression, le chargement et la gestion des niveaux du jeu.
 *
 * <p>Elle assure les responsabilités suivantes :</p>
 * <ul>
 *   <li>Charger les niveaux disponibles</li>
 *   <li>Débloquer le niveau suivant après avoir terminé le niveau actuel</li>
 *   <li>Suivre l'état du niveau actuel et du dernier niveau joué</li>
 *   <li>Réinitialiser la progression des niveaux</li>
 * </ul>
 *
 * <p>Chaque niveau est représenté par une instance de {@link LevelInterface}.</p>
 */
public class LevelManager {
    /** Indice du niveau actuellement en cours. */
    private int currentLevel;

    /** Indice du dernier niveau explicitement joué. */
    private int lastPlayedLevel;

    /** Liste des niveaux disponibles dans le jeu. */
    private List<LevelInterface> levels;
    private AudioManager audioManager;

    /**
     * Initialise le gestionnaire avec une liste de niveaux prédéfinie.
     *
     * <p>Par défaut, seul le premier niveau est débloqué.</p>
     */
    public LevelManager(AudioManager audioManager) {
        if (audioManager == null) {
            throw new IllegalArgumentException("AudioManager ne peut pas être null");
        }
        this.audioManager = audioManager;
        levels = new ArrayList<>();
        levels.add(new Level(AssetPaths.LEVEL1_MAP, true, audioManager));
        levels.add(new Level(AssetPaths.LEVEL2_MAP, false, audioManager));
        levels.add(new Level(AssetPaths.LEVEL3_MAP, false, audioManager));
        levels.add(new Level(AssetPaths.LEVEL4_MAP, false, audioManager));
        currentLevel = 1;
        lastPlayedLevel = 1;
    }

    /**
     * Charge un niveau spécifique si celui-ci est débloqué.
     *
     * @param level Le numéro du niveau à charger.
     */
    public void loadLevel(int level) {
        if (level < 1 || level > levels.size()) {
            System.out.println("Niveau non valide.");
            return;
        }

        LevelInterface selectedLevel = levels.get(level - 1);

        if (selectedLevel.isUnlocked()) {
            selectedLevel.load();
            currentLevel = level;
            lastPlayedLevel = level;
            System.out.println("Niveau " + level + " chargé.");
        } else {
            System.out.println("Le niveau " + level + " n'est pas encore débloqué !");
        }
    }

    /**
     * Marque le niveau actuel comme complété.
     *
     * <p>Cette méthode met à jour l'état de complétion du niveau actuel.</p>
     */
    public void setLevelCompleted() {
        LevelInterface current = levels.get(currentLevel - 1);
        current.setCompleted(true);
        System.out.println("Niveau " + currentLevel + " complété !");
    }

    /**
     * Débloque le niveau suivant, si disponible.
     *
     * <p>Cette méthode incrémente également le niveau actuel pour passer au suivant.</p>
     */
    public void unlockNextLevel() {
        if (currentLevel < levels.size()) {
            LevelInterface nextLevel = levels.get(currentLevel);
            nextLevel.setUnlocked(true);
            currentLevel++;
            System.out.println("Niveau " + currentLevel + " débloqué.");
        } else {
            System.out.println("Tous les niveaux sont déjà débloqués.");
        }
    }

    /**
     * Réinitialise la progression du jeu.
     *
     * <p>Tous les niveaux sont verrouillés et non complétés, sauf le premier niveau qui reste débloqué.</p>
     */
    public void resetGame() {
        for (LevelInterface level : levels) {
            level.setCompleted(false);
            level.setUnlocked(false);
        }
        levels.get(0).setUnlocked(true); // Le premier niveau est toujours débloqué.
        currentLevel = 1;
        loadLevel(currentLevel);
        System.out.println("Jeu réinitialisé. Retour au niveau 1.");
    }

    /**
     * Vérifie si un niveau spécifique est débloqué.
     *
     * @param level Le numéro du niveau à vérifier.
     * @return {@code true} si le niveau est débloqué, sinon {@code false}.
     */
    public boolean isLevelUnlocked(int level) {
        return levels.get(level - 1).isUnlocked();
    }

    /**
     * Vérifie si un niveau spécifique est complété.
     *
     * @param level Le numéro du niveau à vérifier.
     * @return {@code true} si le niveau est complété, sinon {@code false}.
     */
    public boolean isLevelCompleted(int level) {
        return levels.get(level - 1).isCompleted();
    }

    /**
     * Recharge le dernier niveau joué.
     *
     * <p>Cette méthode recharge le niveau mémorisé comme étant le dernier joué.</p>
     */
    public void restartLevel() {
        if (lastPlayedLevel > 0 && lastPlayedLevel <= levels.size()) {
            System.out.println("Rechargement du dernier niveau joué : " + lastPlayedLevel);
            loadLevel(lastPlayedLevel);
        } else {
            System.out.println("Impossible de rejouer le niveau. Niveau invalide : " + lastPlayedLevel);
        }
    }

    /**
     * Retourne l'objet représentant le niveau actuel.
     *
     * @return Une instance de {@link LevelInterface} correspondant au niveau actuel.
     * @throws IllegalStateException si le niveau actuel est invalide.
     */
    public LevelInterface getCurrentLevelObject() {
        if (currentLevel > 0 && currentLevel <= levels.size()) {
            return levels.get(currentLevel - 1);
        } else {
            throw new IllegalStateException("Le niveau actuel est invalide : " + currentLevel);
        }
    }

    /**
     * Retourne la carte du jeu associée au niveau actuel.
     *
     * @return Une instance de {@link GameMap} représentant la carte actuelle.
     * @throws IllegalStateException si le niveau actuel ne possède pas de carte valide.
     */
    public GameMap getCurrentGameMap() {
        LevelInterface currentLevel = getCurrentLevelObject();
        if (currentLevel instanceof Level) {
            return ((Level) currentLevel).getGameMap();
        }
        throw new IllegalStateException("Le niveau actuel n'a pas de GameMap valide.");
    }

    /**
     * Définit le dernier niveau joué.
     *
     * @param i Le numéro du dernier niveau joué.
     */
    public void setLastPlayedLevel(int i) {
        this.lastPlayedLevel = i;
    }

    /**
     * Retourne un niveau spécifique de la liste.
     *
     * @param level Le numéro du niveau à récupérer.
     * @return Une instance de {@link LevelInterface}, ou {@code null} si le niveau est invalide.
     */
    public LevelInterface getLevel(int level) {
        if (level >= 1 && level <= levels.size()) {
            return levels.get(level - 1);
        }
        return null;
    }
}
