package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class View extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JPanel imagePanel;
    private JPanel buttonPanel;
    private JLabel imageLabel;
    private Controller controller;
    private boolean isInterrupted = false;
    private BufferedImage image;

    public View(Controller controller) {
        this.controller = controller;
        controller.initCamera();
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        imageLabel = new JLabel();
        add(imageLabel, BorderLayout.CENTER);
        add(initButtonPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 80, 50, this);
    }

    private JPanel initButtonPanel() {
        JButton translateVideoButton = new JButton("Запись с камеры");
        translateVideoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        isInterrupted = false;
                        while (!isInterrupted) {
                            getPhoto();
                        }
                    }
                }).start();
            }
        });
        JButton makeScreenshotButton = new JButton("Сделать снимок");
        makeScreenshotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isInterrupted = true;
                getPhoto();

            }
        });
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(translateVideoButton);
        panel.add(makeScreenshotButton);
        return panel;
    }

    private void getPhoto() {
        controller.deletePhoto();
        controller.makePhoto();
        image = controller.createImage();
        repaint();
        setVisible(true);
    }
}
