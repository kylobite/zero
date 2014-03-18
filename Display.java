import java.awt.Canvas;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Dimension;

public class Display extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    
    public static final int WIDTH           = 800;
    public static final int HEIGHT          = 600;
    public static final String TITLE        = "Zero Space :: Alpha";
    public static ArrayList<String> debug   = new ArrayList<String>();

    private Thread thread;
    private Screen screen;
    private Game game;
    private BufferedImage image;

    private boolean running = false;
    private int[] pixels;

    public Display() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        screen  = new Screen(WIDTH, HEIGHT);
        game    = new Game();
        image   = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels  = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    }

    private void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
        debug.add("Thread started");
    }

    public void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();    
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    // Game loop
    public void run() {
        int frames          = 0;
        double delta        = 0;
        long previousTime   = System.nanoTime();
        double frequency    = 1 / 60.0;
        int tickCount       = 0;
        boolean ticked      = false;

        while (running) {
            long currentTime = System.nanoTime();
            long passedTime  = currentTime - previousTime;
            previousTime = currentTime;
            delta += passedTime / 1000000000.0;

            while (delta > frequency) {
                tick();
                delta -= frequency;
                ticked = true;
                tickCount++;
                if (tickCount % 60 == 0) {
                    System.out.println(frames + "fps");
                    previousTime += 1000;
                    frames = 0;
                }
            }

            if (ticked) {
                render();
                frames++;
            }
            render();
            frames++;
        }
    }

    private void tick() {
        game.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.render(game);

        for (int i = 0; i < WIDTH*HEIGHT; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Display game = new Display();
        JFrame frame = new JFrame();

        frame.add(game);
        frame.pack();
        frame.setTitle(TITLE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        System.out.println("Running...");

        game.start();
        for (int i = 0; i < debug.toArray().length; i++) {
            System.out.println((i + 1) + ". " + debug.toArray()[i]);
        }
    }

}