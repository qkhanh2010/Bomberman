package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Tile {

    public FlameItem(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            remove();
            ((Bomber) entity).explosionRange += 0.5;

            return true;
        }
        return false;
    }
}
