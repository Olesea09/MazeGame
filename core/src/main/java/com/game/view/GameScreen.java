package com.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * L'interface {@code GameScreen} définit les méthodes essentielles pour gérer les écrans dans le jeu.
 *
 * <p>Chaque écran du jeu (écran de chargement, écran de jeu, écran de fin et écran de sélection des niveaux) doit implémenter
 * cette interface pour assurer une gestion cohérente du cycle de vie et du rendu.</p>
 *
 * <p>Les méthodes incluent des événements clés comme le rendu, l'affichage, le masquage et la libération des ressources.</p>
 *
 */
public interface GameScreen {
    /**
     * Affiche les éléments de l'écran à chaque frame.
     *
     * @param batch Le {@link SpriteBatch} utilisé pour dessiner les éléments graphiques de l'écran.
     */
    void render(SpriteBatch batch);

    /**
     * Méthode appelée lorsque l'écran devient actif.
     *
     * <p>Peut être utilisée pour initialiser ou charger des ressources nécessaires à l'écran.</p>
     */
    void show();

    /**
     * Méthode appelée lorsque l'écran devient inactif.
     *
     * <p>Peut être utilisée pour arrêter des processus en arrière-plan ou libérer des ressources temporaires.</p>
     */
    void hide();

    /**
     * Libère les ressources associées à l'écran.
     *
     * <p>Doit être appelée lorsque l'écran n'est plus nécessaire afin d'éviter les fuites de mémoire.</p>
     */
    void dispose();

    /**
     * Retourne le {@link Stage} associé à l'écran.
     *
     * <p>Le stage est utilisé pour gérer les éléments UI et capturer les événements d'entrée.</p>
     *
     * @return Le {@link Stage} de l'écran.
     */
    Stage getStage();
}
