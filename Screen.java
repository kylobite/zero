import java.util.Random;

public class Screen extends Render {

    private Render test;

    public Screen(int width, int height) {
        super(width,height);
        Random random = new Random();
        test = new Render(256,256);
        for (int i = 0; i < (256*256); i++) {
            test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
        }
    }

    public void render(Game game) {
        for (int i = 0; i < width * height; i++) {
            pixels[i] = 0;
        }

        int x = (width  - 256) / 2;
        int y = (height - 256) / 2;
        for (int i = 0; i < 40; i++) {
            int animX = (int)(Math.sin(((game.time * 5) + i * 12) % 2000.0 / 2000.0 * Math.PI * 2) * 200);
            int animY = (int)(Math.cos(((game.time * 5) + i * 12) % 2000.0 / 2000.0 * Math.PI * 2) * 200);
            draw(test, x + animX, y - animY);
        }
    }

}