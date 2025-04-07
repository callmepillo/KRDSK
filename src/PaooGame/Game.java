package PaooGame;

import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Graphics.Colors;
import PaooGame.Tiles.Tile;

// For Buffer
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

// For noise
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.util.ConcurrentModificationException;
import java.util.Random;

public class Game implements Runnable {
    private GameWindow      wnd;        /*!< Fereastra in care se va desena tabla jocului*/
    private boolean         runState;   /*!< Flag ce starea firului de executie.*/
    private boolean         pausedState;
    private Thread          gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy  bs;         /*!< Referent catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    private int[][] distortionMapX;
    private int[][] distortionMapY;
    private BufferedImage   frameBuffer;

    public Game(String title, int width, int height) {
        wnd = new GameWindow(title, width, height);
        runState = false;
        frameBuffer = null;
    }

    private void InitGame() {
            /// Este construita fereastra grafica.
        wnd.BuildGameWindow();
        distortionMapX = precomputeDistortionMapX(wnd.GetWndWidth(), wnd.GetWndHeight(), 0.1);
        distortionMapY = precomputeDistortionMapY(wnd.GetWndWidth(), wnd.GetWndHeight(), 0.1);
        /// Se incarca toate elementele grafice (dale)
        Assets.Init();
    }

    public void run() {
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/
        final int framesPerSecond   = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame      = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

        while (runState == true)
        {
                /// Se obtine timpul curent
            curentTime = System.nanoTime();
                /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if((curentTime - oldTime) > timeFrame)
            {
                /// Actualizeaza pozitiile elementelor
                Update();
                /// Deseneaza elementele grafica in fereastra.
                Draw();
                oldTime = curentTime;
            }
        }
        System.out.println("Thread Stopped.");
    }

    public synchronized void StartGame() {
        if(runState == false)
        {
                /// Se actualizeaza flagul de stare a threadului
            runState = true;
                /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
                /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
                /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        }
        else
        {
                /// Thread-ul este creat si pornit deja
            return;
        }
    }

    public synchronized void StopGame() {
        if(runState == true)
        {
            runState = false;
            wnd.Stop();
//            try
//            {
//                gameThread.join();
//                wnd.Stop();
//            }
//            catch(InterruptedException ex)
//            {
//                ex.printStackTrace();
//            }
        }
        else
        {
            return;
        }
    }

    private void Update() {
        if(wnd.GetStop())
            StopGame();


        for(FauxWindow win : wnd.GetWindows())
            win.Update(wnd.GetMouseX(), wnd.GetMouseY());
        if(wnd.GetPlayer() != null)
            wnd.GetPlayer().Update();
    }

    private void Draw() {
        // Get existing buffer strategy or create one
        bs = wnd.GetCanvas().getBufferStrategy();
        if(bs == null)
        {
            try
            {
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        Graphics2D bufferGraphics = (Graphics2D) bs.getDrawGraphics();

        int width = wnd.GetWndWidth();
        int height = wnd.GetWndHeight();

        if (frameBuffer == null) {
            frameBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
        Graphics2D imgGraphics = frameBuffer.createGraphics();

        imgGraphics.setFont(new Font("Adwaita Mono", Font.BOLD, 30));
        imgGraphics.setStroke(new java.awt.BasicStroke(10));
        imgGraphics.setColor(Colors.background);
        imgGraphics.fillRect(0, 0, width, height);
        imgGraphics.setColor(Colors.term);
        imgGraphics.drawRect(0,0, width, height);
        imgGraphics.setColor(Colors.background);
//
//        Tile.mountainTile.Draw(imgGraphics, 0, 0);
//        Tile.mountainTile.Draw(imgGraphics, 0, 1*Tile.TILE_HEIGHT);
//        Tile.mountainTile.Draw(imgGraphics, 0, 2*Tile.TILE_HEIGHT);
//        Tile.mountainTile.Draw(imgGraphics, 0, 3*Tile.TILE_HEIGHT);
//        Tile.mountainTile.Draw(imgGraphics, 0, 4*Tile.TILE_HEIGHT);
        Tile.plantTile.Draw(imgGraphics, 0, 0);

        if(wnd.GetPlayer() != null)
            wnd.GetPlayer().Draw(imgGraphics);
        if(wnd.GetBar() != null)
            wnd.GetBar().Draw(imgGraphics);
        try {
            for (FauxWindow win : wnd.GetWindows())
                win.Draw(imgGraphics);
        }
        catch (ConcurrentModificationException ex) {
            //We will lose a frame, but this is a patch for when someone spams a key
            //that opens or closes a FauxWindow (a.k.a. adds or removes to the windows List<>
            //in GameWindow) triggering an exception.
        }
        //chat ftw
        //applyBarrelDistortionV1(frameBuffer, 0.1);

        applyScanlines(frameBuffer);
        applyNoise(frameBuffer);
        applyBarrelDistortionOpt(frameBuffer);
        //commented because they arent important for the production process
        imgGraphics.dispose();

        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.fillRect(0,0, width, height);
        bufferGraphics.drawImage(frameBuffer, 0, 0, width, height, null);
        bufferGraphics.dispose();

        bs.show();
    }

    private void applyScanlines(BufferedImage image) {
        int width = wnd.GetWndWidth();
        int height = wnd.GetWndHeight();

        Graphics2D g = image.createGraphics();

        // Apply scanlines
        g.setColor(new Color(0, 0, 0, 50));
        for (int y = 0; y < height; y += height / 100) {
            g.drawLine(0, y, width, y+height / 200);
        }
    }
    private void applyNoise(BufferedImage image) {
        int width = wnd.GetWndWidth();
        int height = wnd.GetWndHeight();
        Graphics2D g = (Graphics2D) image.getGraphics();
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.setColor(new Color(150, 150, 150, random.nextInt(100)));
            g.fillRect(x, y, 2, 2);
        }
    }
    private int[][] precomputeDistortionMapX(int width, int height, double distortionFactor) {
        int[][] mapX = new int[height][width];
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double radius = Math.max(centerX, centerY);
        double invRadius = 1.0 / radius;  // Precompute inverse to avoid division in loop

        for (int y = 0; y < height; y++) {
            double normY = (y - centerY) * invRadius;  // Normalize Y once
            for (int x = 0; x < width; x++) {
                double normX = (x - centerX) * invRadius;  // Normalize X once
                double distanceSq = normX * normX + normY * normY;
                double factor = 1.0 + distortionFactor * distanceSq;

                int srcX = (int) ((normX * factor * radius) + centerX);
                mapX[y][x] = srcX;  // Clamp to bounds
            }
        }
        return mapX;
    }
    private int[][] precomputeDistortionMapY(int width, int height, double distortionFactor) {
        int[][] mapY = new int[height][width];
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double radius = Math.max(centerX, centerY);
        double invRadius = 1.0 / radius;  // Precompute inverse

        for (int y = 0; y < height; y++) {
            double normY = (y - centerY) * invRadius;  // Normalize Y once
            for (int x = 0; x < width; x++) {
                double normX = (x - centerX) * invRadius;  // Normalize X once
                double distanceSq = normX * normX + normY * normY;
                double factor = 1.0 + distortionFactor * distanceSq;

                int srcY = (int) ((normY * factor * radius) + centerY);
                mapY[y][x] = srcY;  // Clamp to bounds
            }
        }
        return mapY;
    }
    public void applyBarrelDistortion(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage distorted = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Get the raster data for fast pixel manipulation
        WritableRaster srcRaster = image.getRaster();
        WritableRaster dstRaster = distorted.getRaster();
        int[] pixel = new int[4]; // Store RGB values

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int srcX = distortionMapX[y][x];
                int srcY = distortionMapY[y][x];

                // Get RGB values directly from raster
                if(srcX >= 0 && srcX < width && srcY >= 0 && srcY < height) {
                    srcRaster.getPixel(srcX, srcY, pixel);
                    dstRaster.setPixel(x, y, pixel);
                }
            }
        }

        Graphics2D g = image.createGraphics();
        g.drawImage(distorted, 0, 0, null);
        g.dispose();
    }
    public void applyBarrelDistortionOpt(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int[] outputPixels = new int[pixels.length]; // Buffer for new pixel values

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int srcX = distortionMapX[y][x];
                int srcY = distortionMapY[y][x];

                if(srcX >= 0 && srcX < width && srcY >= 0 && srcY < height) {
                    outputPixels[y * width + x] = pixels[srcY * width + srcX];
                }
            }
        }

        // Copy processed pixels back to the image
        System.arraycopy(outputPixels, 0, pixels, 0, pixels.length);
    }
//    private void applyBarrelDistortionV1(BufferedImage image, double distortionFactor) {
//        int width = wnd.GetWndWidth();
//        int height = wnd.GetWndHeight();
//
//        Graphics2D g = image.createGraphics();
//        BufferedImage distorted = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//
//        double centerX = width / 2.0;
//        double centerY = height / 2.0;
//        double radius = Math.max(centerX, centerY);
//
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                // Normalize coordinates (-1 to 1 range)
//                double normX = (x - centerX) / radius;
//                double normY = (y - centerY) / radius;
//
//                // Calculate distance from center
//                double distance = Math.sqrt(normX * normX + normY * normY);
//
//                // Apply barrel distortion formula
//                double factor = 1.0 + distortionFactor * (distance * distance);
//                int srcX = (int) ((normX * factor * radius) + centerX);
//                int srcY = (int) ((normY * factor * radius) + centerY);
//
//                // Get pixel from source image (only if within bounds)
//                if (srcX >= 0 && srcX < width && srcY >= 0 && srcY < height) {
//                    distorted.setRGB(x, y, image.getRGB(srcX, srcY));
//                }
//            }
//        }
//
//        // Copy back distorted image
//        g.drawImage(distorted, 0, 0, null);
//        g.dispose();
//    }

//    public void applyBarrelDistortionParallel(BufferedImage image) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//        BufferedImage distorted = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//
//        WritableRaster srcRaster = image.getRaster();
//        WritableRaster dstRaster = distorted.getRaster();
//        int cores = Runtime.getRuntime().availableProcessors();
//        ExecutorService executor = Executors.newFixedThreadPool(cores);
//
//        // Split the work among multiple threads
//        int chunkSize = height / cores;
//        for (int i = 0; i < cores; i++) {
//            int startY = i * chunkSize;
//            int endY = (i == cores - 1) ? height : (i + 1) * chunkSize;
//            executor.execute(() -> processChunk(srcRaster, dstRaster, startY, endY, width));
//        }
//
//        executor.shutdown();
//        try { executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); } catch (InterruptedException e) {}
//
//        Graphics2D g = image.createGraphics();
//        g.drawImage(distorted, 0, 0, null);
//        g.dispose();
//    }
//
//    private void processChunk(WritableRaster srcRaster, WritableRaster dstRaster, int startY, int endY, int width) {
//        int[] pixel = new int[4];
//        for (int y = startY; y < endY; y++) {
//            for (int x = 0; x < width; x++) {
//                int srcX = distortionMapX[y][x];
//                int srcY = distortionMapY[y][x];
//                srcRaster.getPixel(srcX, srcY, pixel);
//                dstRaster.setPixel(x, y, pixel);
//            }
//        }
//    }
}

//MODEL -> Baza de date

