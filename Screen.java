import java.util.Random;

public class Screen extends Render {

    private Render test;

    public Screen(int width, int height) {
        super(width,height);
        Random random = new Random();
        test = new Render(256,256);
        for (int i = 0; i < (256*256); i++) {
            test.pixels[i] = random.nextInt();
        }
    }

    public void render() {
        for (int i = 0; i < width * height; i++) {
            pixels[i] = 0;
        }

        int x = (width  - 256) / 2;
        int y = (height - 256) / 2;
        for (int i = 0; i < 100; i++) {
            int animX = (int)(Math.sin((System.currentTimeMillis()) % 2000.0 / 2000.0 * Math.PI * 2) * 200);
            int animY = (int)(Math.cos((System.currentTimeMillis()) % 2000.0 / 2000.0 * Math.PI * 2) * 200);
            draw(test, x + animX, y + animY);
        }
    }

}