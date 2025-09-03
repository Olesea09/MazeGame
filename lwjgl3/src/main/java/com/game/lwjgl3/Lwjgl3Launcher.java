package com.game.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.game.controller.Main;

/**
 * La classe {@code Lwjgl3Launcher} est le point d'entrée principal de l'application de jeu.
 *
 * <p>Elle configure et lance l'application LibGDX avec les paramètres spécifiques pour LWJGL3.</p>
 *
 * <p>Cette classe définit des configurations telles que le titre de la fenêtre, le mode plein écran,
 * la synchronisation verticale et les icônes de la fenêtre.</p>
 *
 */
public class Lwjgl3Launcher {

    /**
     * Point d'entrée principal du jeu.
     *
     * @param args Les arguments passés depuis la ligne de commande.
     */
    public static void main(String[] args) {
        createApplication();
    }

    /**
     * Crée et lance une instance de l'application LibGDX avec une configuration par défaut.
     *
     * @return Une instance de {@link Lwjgl3Application}.
     */
    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    /**
     * Fournit la configuration par défaut pour l'application LWJGL3.
     *
     * <p>Cette configuration inclut le titre de la fenêtre, la résolution de l'écran,
     * l'activation de la synchronisation verticale (VSync) et l'ajout des icônes de la fenêtre.</p>
     *
     * @return Une instance de {@link Lwjgl3ApplicationConfiguration} avec les paramètres par défaut.
     */
    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("MazeGame");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);

        // Récupérer la résolution de l'écran principal
        int screenWidth = Lwjgl3ApplicationConfiguration.getDisplayMode().width;
        int screenHeight = Lwjgl3ApplicationConfiguration.getDisplayMode().height;

        // Adapter la fenêtre à la taille de l'écran
        configuration.setWindowedMode(screenWidth, screenHeight);
        configuration.setMaximized(true); // Maximiser la fenêtre automatiquement

        // Définir les icônes de la fenêtre
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

        return configuration;
    }

}
