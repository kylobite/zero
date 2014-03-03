import java.awt.Canvas;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;

public class Display extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    
    public static final int WIDTH           = 800;
    public static final int HEIGHT          = 600;
    public static final String TITLE        = "Zero Space :: Alpha";
    public static ArrayList<String> debug   = new ArrayList<String>();

    private Thread thread;
    private Screen screen;
    private BufferedImage image;

    private boolean running = false;
    private int[] pixels;

    public Display() {
        screen  = new Screen(WIDTH, HEIGHT);
        image   = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
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
        while (running) {
            update();
            render();
        }
    }

    private void update() {
        
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.render();

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
        frame.setSize(WIDTH,HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        System.out.println("Running...");

        game.start();
        for (int i = 0; i < debug.toArray().length; i++) {
            System.out.println((i + 1) + ". " + debug.toArray()[i]);
        }
    }

}