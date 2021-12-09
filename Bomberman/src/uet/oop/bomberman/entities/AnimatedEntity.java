package uet.oop.bomberman.entities;


import uet.oop.bomberman.graphics.Sprite;

public abstract class AnimatedEntity extends Entity {

    protected int animate = 0;
    protected Direction direction;

    public AnimatedEntity(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
    }

    protected void changeanimate() {
        if(animate < 7500) animate++; else animate = 0;
    }


}
