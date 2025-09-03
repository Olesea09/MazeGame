package com.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.game.assets.AssetPaths;
import com.game.controller.AudioManager;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code GameMap} représente la carte du jeu chargée depuis un fichier JSON.
 *
 * <p>Elle gère les éléments essentiels de la carte, notamment :</p>
 * <ul>
 *   <li>Les murs et les obstacles</li>
 *   <li>Le joueur</li>
 *   <li>Les fruits collectables</li>
 *   <li>Les zones de fin (EndZone)</li>
 *   <li>Les ennemis</li>
 * </ul>
 *
 * <p>La classe assure également la détection des collisions et le traitement des objets
 * dynamiquement ajoutés via l'éditeur de cartes Tiled.</p>
 *
 */
public class GameMap {
    /** Largeur de la carte (en nombre de tuiles). */
    private int mapWidth;
    /** Hauteur de la carte (en nombre de tuiles). */
    private int mapHeight;
    /** Largeur d'une tuile (en pixels). */
    private int tileWidth;
    /** Hauteur d'une tuile (en pixels). */
    private int tileHeight;
    /** Texture utilisée pour le tileset. */
    private Texture tilesetTexture;
    /** Tableau des régions de texture extraites du tileset. */
    private TextureRegion[][] tiles;
    /** Liste des murs et obstacles présents sur la carte. */
    private List<Tile> walls;
    /** Instance du joueur initialisée depuis la carte. */
    private Player player;
    /** Liste des zones de fin (EndZone) présentes sur la carte. */
    private List<EndZone> endZones;
    /** Liste des fruits collectables présents sur la carte. */
    private List<Fruit> fruits;
    /** Liste des ennemis présents sur la carte. */
    private List<Enemy> enemies;
    private AudioManager audioManager;

    /**
     * Initialise une nouvelle carte de jeu à partir d'un fichier JSON.
     *
     * @param mapFile Chemin du fichier JSON décrivant la carte.
     */
    public GameMap(String mapFile, AudioManager audioManager) {
        if (audioManager == null) {
            throw new IllegalArgumentException("AudioManager ne peut pas être null");
        }
        this.audioManager = audioManager;

        // Lancer la musique de fond
        audioManager.playBackgroundMusic();

        walls = new ArrayList<>();
        fruits = new ArrayList<>();
        enemies = new ArrayList<>();
        endZones = new ArrayList<>();
        loadMap(mapFile);
    }

    public void checkCollisions() {
        // Déléguer la gestion des collisions au joueur
        player.handleFruitCollision(fruits);
        player.handleEnemyCollision(enemies);
    }

    /**
     * Charge les données de la carte depuis un fichier JSON.
     *
     * @param mapFile Chemin du fichier JSON décrivant la carte.
     */
    private void loadMap(String mapFile) {
        try {
            JsonReader jsonReader = new JsonReader();
            JsonValue mapData = jsonReader.parse(Gdx.files.internal(mapFile));

            // Charger les propriétés de la carte
            mapWidth = mapData.getInt("width");
            mapHeight = mapData.getInt("height");
            tileWidth = mapData.getInt("tilewidth");
            tileHeight = mapData.getInt("tileheight");

            // Charger les tilesets
            JsonValue tilesets = mapData.get("tilesets").get(0);
            String imagePath = tilesets.getString("image");
            tilesetTexture = new Texture(Gdx.files.internal(imagePath));
            tiles = TextureRegion.split(tilesetTexture, tileWidth, tileHeight);

            // Charger les couches
            JsonValue layers = mapData.get("layers");
            for (JsonValue layer : layers) {
                String layerType = layer.getString("type");

                if ("tilelayer".equals(layerType)) {
                    parseTileLayer(layer);
                } else if ("objectgroup".equals(layerType)) {
                    parseObjectLayer(layer);
                }
            }

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de la carte JSON : " + e.getMessage());
        }
    }

    /**
     * Analyse une couche de tuiles (Tile Layer) et initialise les murs.
     *
     * @param layer Les données JSON de la couche de tuiles.
     */
    private void parseTileLayer(JsonValue layer) {
        JsonValue data = layer.get("data");
        int index = 0;

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int tileId = data.getInt(index++) - 1;

                if (tileId == 0) { // ID des murs
                    TextureRegion region = tiles[tileId / tiles[0].length][tileId % tiles[0].length];
                    walls.add(new Tile(x * tileWidth, (mapHeight - y - 1) * tileHeight, region, tileWidth, tileHeight));
                }
            }
        }
    }

    /**
     * Analyse une couche d'objets (Object Layer) et initialise les entités dynamiques.
     *
     * @param layer Les données JSON de la couche d'objets.
     */
    private void parseObjectLayer(JsonValue layer) {
        JsonValue objects = layer.get("objects");
        for (JsonValue object : objects) {
            String type = object.getString("type");
            float x = object.getFloat("x");
            float y = object.getFloat("y");

            float convertedY = (mapHeight * tileHeight) - y - tileHeight;

            if ("player".equals(type)) {
                player = new Player(x, convertedY,audioManager); // Position ajustée
            } else if ("fruit".equals(type)) {
                fruits.add(new Fruit(x, convertedY, AssetPaths.FRUIT_TEXTURE)); // Position ajustée
            } else if ("end".equals(type)) {
                endZones.add(new EndZone(x, convertedY, tileWidth, tileHeight));
            } else if ("enemy".equals(type)) {
                String movementType = "horizontal"; // Valeur par défaut

                // Vérifier si des propriétés existent pour cet objet
                JsonValue properties = object.get("properties");
                if (properties != null) {
                    // Parcourir les propriétés pour trouver "movable"
                    for (JsonValue property : properties) {
                        if ("movable".equals(property.getString("name"))) {
                            movementType = property.getString("value");
                            break; // On a trouvé la propriété, pas besoin de continuer
                        }
                    }
                }

                // Déterminer si l'ennemi se déplace verticalement
                boolean isVertical = "vertical".equals(movementType);
                String horizontalTexture = AssetPaths.HORIZONTAL_ENEMY;
                String verticalTexture = AssetPaths.VERTICAL_ENEMY;

                // Ajouter l'ennemi avec la configuration correcte
                enemies.add(new Enemy(x, convertedY, horizontalTexture, verticalTexture, isVertical));
            }

        }
    }

    /**
     * Vérifie si une position donnée entre en collision avec un mur sur la carte.
     *
     * <p>Cette méthode parcourt tous les murs présents dans la carte et vérifie
     * si les coordonnées spécifiées se chevauchent avec un mur existant.</p>
     *
     * @param x La coordonnée X à vérifier.
     * @param y La coordonnée Y à vérifier.
     * @return {@code true} si une collision avec un mur est détectée, sinon {@code false}.
     */
    public boolean isCollision(float x, float y) {
        for (Tile wall : walls) {
            if (x < wall.getX() + tileWidth && x + tileWidth > wall.getX() &&
                y < wall.getY() + tileHeight && y + tileHeight > wall.getY()) {
                return true; // Collision détectée
            }
        }
        return false; // Pas de collision
    }

    /**
     * Vérifie si le joueur se trouve dans une zone de fin du niveau.
     *
     * <p>Cette méthode parcourt toutes les zones de fin définies sur la carte
     * et vérifie si les coordonnées du joueur se trouvent dans l'une d'elles.</p>
     *
     * @param playerX La coordonnée X du joueur.
     * @param playerY La coordonnée Y du joueur.
     * @return {@code true} si le joueur est dans une zone de fin, sinon {@code false}.
     */
    public boolean isPlayerInEndZone(float playerX, float playerY) {
        for (EndZone endZone : endZones) {
            if (endZone.isPlayerOnEnd(playerX, playerY)) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Tile> getWalls() {
        return walls;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public List<Fruit> getFruits() {
        return fruits;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<EndZone> getEndZones(){
        return endZones;
    }
}
