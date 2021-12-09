package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.entities.Direction;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileNotFoundException;

public class Balloom extends Enemy {
    private Sprite balloom = Sprite.movingSprite(Sprite.balloom_right1,
            Sprite.balloom_right2, Sprite.balloom_right3, animate, 60);

    public Balloom(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        direction = Direction.RIGHT;
    }

    @Override
    protected void afterkilled() {
        setSprite(Sprite.finalMovingSprite(Sprite.balloom_dead, Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, vanishTime, 40));
    }

    @Override
    public void update() {
        if (!isKilled) {
            changeanimate();
            try {
                value_navigation += 1;
                if (value_navigation / time_navigation == 1) {
                    Ramdom_navigation("balloom");
                    value_navigation = 0;
                    return;
                }
                if (!move(direction, true,"balloom")) {
                    Ramdom_navigation("balloom");
                }
            } catch (FileNotFoundException e) {
                System.out.println("alfjakfajlkfa");
            }
        } else {
            afterkilled();
            if (vanishTime > 0) {
                vanishTime--;
            } else {
                remove();
            }
        }
    }
}
