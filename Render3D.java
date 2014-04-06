
public class Render3D extends Render {

    public Render3D(int width, int height) {
        super(width,height);
    }

    double time = 0;

    public void floor() {
        for (int y = 0; y < height; y++) {
            double yDepth = (y - height / 2.0) / height;

            if (yDepth < 0) yDepth = -yDepth;

            double z      = 8.0 / yDepth;
            time         += 0.0005;

            for (int x = 0; x < width; x++) {
                double xDepth = (x - width / 2.0) / height;
                int xx        = (int)(xDepth * z) & 15;
                int yy        = (int)(z + time) & 15;

                pixels[x + y * width] = (xx << 4) | ((yy << 4) << 8);
            }
        }
    }

}