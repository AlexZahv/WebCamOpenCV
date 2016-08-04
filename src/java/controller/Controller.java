package controller;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import view.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller {
    private VideoCapture videoCapture;
    private View view;
    private static final String FILE_PATH="current.jpg";
    int i=1;

    public void makePhoto() {

        Mat frame = new Mat();
        if (videoCapture.read(frame)) {
            Imgcodecs.imwrite(FILE_PATH, frame);
        }
    }

    public void deletePhoto() {

        try {
            Files.delete(Paths.get(FILE_PATH));
        } catch (IOException e) {
            return;
        }
    }

    public BufferedImage createImage() {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(FILE_PATH));

        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon illustration = new ImageIcon(new ImageIcon(myPicture).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
        JLabel picLabel = new JLabel(illustration);
        return myPicture;
    }

    public void initCamera() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        videoCapture = new VideoCapture(0);
        videoCapture.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 800);
        videoCapture.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 600);

        if (! videoCapture.isOpened()) {
            System.out.println("Error");
        }
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public static void main(String[] args) {
        Controller controller=new Controller();
        View view1=new View(controller);
        controller.setView(view1);
    }
}
