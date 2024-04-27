package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import util.constants.ImagePath;

public class LoginPageView extends BaseFrame {

    public LoginPageView() {
        super(util.constants.WindowConstants.LOGIN_WINDOW_TITLE);
        
        JPanel mainPanel = new JPanel(new BorderLayout()); // Main panel using BorderLayout
        mainPanel.setPreferredSize(new Dimension(800, 600));

        // Left section with only image
        JPanel leftPanel = new JPanel(new BorderLayout());
        try {
            Image backgroundImage = ImageIO.read(new File(ImagePath.LOGO_PATH));
            ImageIcon icon = new ImageIcon(backgroundImage);
            JLabel logoLabel = new JLabel(icon);
            leftPanel.add(logoLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Right section divided into 4 parts vertically
        JPanel rightPanel = new JPanel(new GridLayout(4, 1));

        // First part with text input
        JPanel firstPart = new JPanel();
        JTextField usernameField = new JTextField(20);
        firstPart.add(usernameField);
        rightPanel.add(firstPart);

        // Second part with text input
        JPanel secondPart = new JPanel();
        JPasswordField passwordField = new JPasswordField(20);
        secondPart.add(passwordField);
        rightPanel.add(secondPart);

        // Third part with button
        JPanel thirdPart = new JPanel();
        JButton loginButton = new JButton("Login");
        thirdPart.add(loginButton);
        rightPanel.add(thirdPart);

        // Fourth part split into two horizontally
        JPanel fourthPart = new JPanel(new GridLayout(1, 2));

        // Left section of fourth part with button
        JPanel fourthLeft = new JPanel();
        JButton forgotPasswordButton = new JButton("Forgot Password?");
        fourthLeft.add(forgotPasswordButton);
        fourthPart.add(fourthLeft);

        // Right section of fourth part with button
        JPanel fourthRight = new JPanel();
        JButton registerButton = new JButton("Register");
        fourthRight.add(registerButton);
        fourthPart.add(fourthRight);

        rightPanel.add(fourthPart);

        // Add left and right panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Add main panel to the content pane of the frame
        mainPanel.setVisible(true);
        this.add(mainPanel);
    }

}
