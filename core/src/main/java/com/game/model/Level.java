package com.game.model;
import com.game.controller.AudioManager;

/**
 * La classe {@code Level} représente un niveau du jeu, avec son état de déblocage, de complétion et sa carte associée.
 *
 * <p>Chaque niveau contient :</p>
 * <ul>
 *   <li>Un fichier de carte spécifiant la structure du niveau</li>
 *   <li>Un état indiquant si le niveau est débloqué</li>
 *   <li>Un état indiquant si le niveau est complété</li>
 *   <li>Une instance de {@link GameMap} représentant la carte du niveau</li>
 * </ul>
 *
 * <p>La classe permet également de recharger la carte via la méthode {@link #load()}.</p>
 */
public class Level implements LevelInterface {
    private AudioManager audioManager;
    /** Chemin du fichier de la carte du niveau. */
    private String mapFile;

    /** Indique si le niveau est débloqué. */
    private boolean unlocked;

    /** Indique si le niveau est complété. */
    private boolean completed;

    /** Carte associée au niveau. */
    private GameMap gameMap;

    /**
     * Construit un niveau avec un fichier de carte et un statut initial de déblocage.
     *
     * @param mapFile Chemin du fichier de la carte du niveau.
     * @param unlocked Indique si le niveau est débloqué.
     */
    public Level(String mapFile, boolean unlocked, AudioManager audioManager) {
        if (audioManager == null) {
            throw new IllegalArgumentException("AudioManager ne peut pas être null");
        }
        this.mapFile = mapFile;
        this.unlocked = unlocked;
        this.completed = false;
        this.audioManager = audioManager;
        this.gameMap = new GameMap(mapFile, audioManager);
    }

    /**
     * Charge les ressources nécessaires au niveau.
     *
     * <p>Cette méthode recharge la {@link GameMap} si nécessaire pour assurer que le niveau
     * est correctement initialisé avant d'être affiché ou joué.</p>
     */
    @Override
    public void load() {
        System.out.println("Chargement des ressources pour le niveau avec la carte : " + mapFile);
        this.gameMap = new GameMap(mapFile, audioManager);
    }

    /**
     * Retourne le statut de déblocage du niveau.
     *
     * @return {@code true} si le niveau est débloqué, sinon {@code false}.
     */
    @Override
    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     * Définit si le niveau est débloqué.
     *
     * @param unlocked {@code true} pour débloquer le niveau, {@code false} pour le verrouiller.
     */
    @Override
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    /**
     * Retourne le statut de complétion du niveau.
     *
     * @return {@code true} si le niveau est complété, sinon {@code false}.
     */
    @Override
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Définit si le niveau est complété.
     *
     * @param completed {@code true} pour marquer le niveau comme complété.
     */
    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Retourne le chemin du fichier de la carte du niveau.
     *
     * @return Le chemin du fichier de la carte sous forme de chaîne de caractères.
     */
    @Override
    public String getMapFile() {
        return mapFile;
    }

    /**
     * Retourne l'objet {@link GameMap} associé au niveau.
     *
     * <p>La carte contient les entités, les murs et les autres objets du niveau.</p>
     *
     * @return L'instance de {@link GameMap} associée à ce niveau.
     */
    public GameMap getGameMap() {
        return gameMap;
    }
}
