package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static int animate = 0;
    public static int time = 60;

    private static GraphicsContext gc;
    private Canvas canvas;
    private static Group root;
    public static List<Entity> players = new ArrayList<>();
    private static List<Entity> grasses = new ArrayList<>();
    public static List<Entity> enemies = new ArrayList<Entity>();
    public static List<Entity> stillObjects = new ArrayList<Entity>();
    public static List<Entity> coveredObjects = new ArrayList<>();
    public static List<Entity> bombs = new ArrayList<Entity>();
    private static Bomber bomberman = new Bomber(1, 1, Sprite.player_down);
    public static int level = 1;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        try {
            loadMap(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Keyboard.keyHandle(scene);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                if (!bomberman.isKilled()) {
                    try {
                        bomberman.movePlayer(scene);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.start();
    }

    public static void createMap(int r, int c) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                Entity object;
                object = new Grass(i, j, Sprite.grass);
                grasses.add(object);
            }
        }
    }

    public static void loadMap(int n) throws FileNotFoundException {
        clearMap();
        FileInputStream fileInputStream = new FileInputStream("C:/OneDrive/OneDrive - vnu.edu.vn/Documents/GitHub/Bomberman/res/levels/Level" + n + ".txt");
        DataInputStream dis = new DataInputStream(fileInputStream);
        Scanner scanner = new Scanner(fileInputStream);
        int l = scanner.nextInt();
        int r = scanner.nextInt();
        int c = scanner.nextInt();
        createMap(c, r);
        scanner.nextLine();
        for (int j = 0; j < r; j++) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                Entity object;
                switch (line.charAt(i)) {
                    case '#':
                        object = new Wall(i, j, Sprite.wall);
                        stillObjects.add(object);
                        break;
                    case '*':
                        object = new Brick(i, j, Sprite.brick);
                        stillObjects.add(object);
                        break;
                    case 'x':
                        object = new Portal(i, j, Sprite.portal);
                        coveredObjects.add(object);
                        stillObjects.add(new Brick(i, j, Sprite.brick));
                        break;
                    case 'p':
                        if (bomberman.isKilled()) {
                            bomberman = new Bomber(i, j, Sprite.player_down);
                        } else {
                            bomberman.setX(i * Sprite.SCALED_SIZE);
                            bomberman.setY(j * Sprite.SCALED_SIZE);
                            bomberman.setKilled(false);
                            bomberman.set_remove(false);
                        }
                        players.add(bomberman);
                        break;
                    case '1':
                        Sprite balloom = Sprite.movingSprite(Sprite.balloom_right1,
                                Sprite.balloom_right2, Sprite.balloom_right3, animate, time);
                        object = new Balloom(i, j, balloom);
                        enemies.add(object);
                        break;
                    case '2':
                        object = new Oneal(i, j, Sprite.oneal_right1);
                        enemies.add(object);
                        break;
                    case 'b':
                        object = new BombItem(i, j, Sprite.powerup_bombs);
                        coveredObjects.add(object);
                        stillObjects.add(new Brick(i, j, Sprite.brick));
                        break;
                    case 'f':
                        object = new FlameItem(i, j, Sprite.powerup_flames);
                        coveredObjects.add(object);
                        stillObjects.add(new Brick(i, j, Sprite.brick));
                        break;
                    case 's':
                        object = new SpeedItem(i, j, Sprite.powerup_speed);
                        coveredObjects.add(object);
                        stillObjects.add(new Brick(i, j, Sprite.brick));
                        break;
                }
            }
        }
    }

    public void update() {
        try {
            players.forEach(Entity::update);
        } catch (Exception exception) {

        }
        stillObjects.forEach(Entity::update);
        enemies.forEach(Entity::update);
        bombs.forEach(Entity::update);
        coveredObjects.forEach(Entity::update);
        removeEntity(coveredObjects);
        removeEntity(players);
        removeEntity(enemies);
        removeEntity(bombs);
        removeEntity(stillObjects);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight() );
        grasses.forEach(g -> g.render(gc));
        coveredObjects.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        bombs.forEach(g-> g.render(gc));
        players.forEach(g-> g.render(gc));
        enemies.forEach(g -> g.render(gc));
    }

    //tra về kiểu của entity
    public static Entity getStillObjects(int x, int y, Entity entity) {
        Entity bomb = getEntity(x, y, bombs);
        if (bomb != null && !(entity instanceof Bomb)) return bomb;
        Entity cur = getEntity(x, y, stillObjects);
        if (cur != null) return cur;

        if (!(entity instanceof Enemy)) {
            Iterator<Entity> enemiesIterator = enemies.iterator();
            Entity enemy;
            while(enemiesIterator.hasNext()) {
                enemy = enemiesIterator.next();
                int _x = (enemy.getX() + enemy.getSprite()._realWidth * 2) / Sprite.SCALED_SIZE;
                int _y = (enemy.getY() + enemy.getSprite()._realHeight * 2) / Sprite.SCALED_SIZE;
                if((enemy.getxUnit() == x && enemy.getyUnit() == y)
                        || (_x == x && _y == y))
                    return enemy;
            }
        }

        if (!(entity instanceof Bomber)) {
            Iterator<Entity> playersIterator = players.iterator();
            Entity character;
            while(playersIterator.hasNext()) {
                character = playersIterator.next();
                int _x = (character.getX() + 20) / Sprite.SCALED_SIZE;
                int _y = (character.getY() + 30) / Sprite.SCALED_SIZE;
                if((character.getxUnit() == x && character.getyUnit() == y)
                        || (_x == x && _y == y))
                    return character;
            }
        }

        Entity pu1 = getEntity(x, y, coveredObjects);
        if (pu1 != null) return pu1;

        Entity gr1 = getEntity(x, y, grasses);
        if (gr1 != null) return gr1;
        return null;

    }

    private static Entity getEntity(int x, int y, List<Entity> entities) {
        Iterator<Entity> entityItr = entities.iterator();
        Entity gr;
        while(entityItr.hasNext()) {
            gr = entityItr.next();
            if(gr.getX() == x * Sprite.SCALED_SIZE && gr.getY() == y * Sprite.SCALED_SIZE)
                return gr;
        }
        return null;
    }

    public void removeEntity(List<Entity> list) {
        Iterator<Entity> itr = list.iterator();
        Entity entity;
        while (itr.hasNext()) {
            entity = itr.next();
            if (entity.is_remove()) {
                if (entity instanceof Bomb) {
                    bomberman.maxBomb++;
                }
                itr.remove();
            }
        }
    }

    public static void clearMap() {
        grasses.clear();
        stillObjects.clear();
        players.clear();
        enemies.clear();
        bombs.clear();
        coveredObjects.clear();
    }
}