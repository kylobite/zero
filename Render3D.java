
public class Render3D extends Render {

    public Render3D(int width, int height) {
        super(width,height);
    }

    public void floor(Game game) {
        double rotation = 0; //game.time / 100.0;
        double forward  = game.time / 5.0;
        double right    = game.time / 5.0;
        double cosine   = Math.cos(rotation);
        double sine     = Math.sin(rotation);

        for (int y = 0; y < height; y++) {
            double ceiling = (y - height / 2.0) / height;

            double z      = 8.0 / ceiling;

            if (ceiling < 0) z = 8.0 / -ceiling;

            for (int x = 0; x < width; x++) {
                double xDepth = (x - width / 2.0) / height;
                double depth  = xDepth * z;
                int xx        = (int)(depth * cosine + z * sine + right) & 15;
                int yy        = (int)(z * cosine - depth * sine + forward) & 15;

                pixels[x + y * width] = (xx << 4) | ((yy << 4) << 8);
            }
        }
    }

}