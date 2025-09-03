package com.game.model;

/**
 * L'interface {@code Movable} définit un comportement de déplacement pour les objets du jeu.
 *
 * <p>Les classes qui implémentent cette interface doivent fournir une logique
 * pour déplacer un objet en modifiant ses coordonnées selon des valeurs spécifiées.</p>
 *
 * <p>Cette interface est utilisée pour les entités mobiles telles que
 * le joueur et les ennemis.</p>
 *
 */
public interface Movable {
    /**
     * Déplace l'objet selon les valeurs spécifiées sur les axes X et Y.
     *
     * @param deltaX Le déplacement sur l'axe horizontal (X).
     * @param deltaY Le déplacement sur l'axe vertical (Y).
     */
    public void move(float deltaX, float deltaY);
}
