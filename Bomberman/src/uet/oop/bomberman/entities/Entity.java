package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Render;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;

public abstract class Entity implements Render {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;
    protected Sprite sprite;
    protected boolean _remove = false;



    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Sprite sprite) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.sprite = sprite;
        this.img = this.sprite.getFxImage();
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.img = sprite.getFxImage();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean is_remove() {
        return _remove;
    }

    public void remove() {
        _remove = true;
    }

    public abstract boolean collide(Entity entity);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getxUnit() {
        return x / Sprite.SCALED_SIZE;
    }

    public int getyUnit() {
        return y / Sprite.SCALED_SIZE;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set_remove(boolean _remove) {
        this._remove = _remove;
    }


}