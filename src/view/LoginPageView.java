package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import util.constants.*;
import util.constants.WindowConstants;
import util.ui.UIUtils;
import util.ui.toaster.Toaster;
import view.CustomComponents.HyperlinkText;
import view.CustomComponents.TextFieldPassword;
import view.CustomComponents.TextField;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * The login page view of the application.
 * This class represents the graphical user interface for the login functionality.
 */
@SuppressWarnings("serial")
public class LoginPageView extends BaseFrame {
	
	/**
	 * The toaster object for displaying notifications.
	 */
    private final Toaster toaster;
    
    /**
     * The main JPanel of the login page.
     */
    JPanel mainJPanel = getMainJPanel();
    
    /**
     * Constructs a new LoginPageView.
     *
     * @param backgroundImage The path to the background image.
     */
    public LoginPageView() {
        super(WindowConstants.LOGIN_WINDOW_TITLE);
        toaster = new Toaster(mainJPanel);
        initializeFrame();
    }

    /**
     * Initializes the frame components.
     */
	@Override
    public void initializeFrame() {
     	addLogo();
        addLoginText();
        addSeparator();
        addUsernameTextField();
	    addPasswordTextField();
        addLoginButton();
	    addForgotPasswordButton();
	    addRegisterButton();	   
	    
	    this.add(mainJPanel);
    	
        setVisible(true);
        
        getContentPane().requestFocusInWindow();
        getContentPane().requestFocusInWindow(); // requesting 1 time does not help :(
        getContentPane().requestFocusInWindow();
    }
    
    /**
     * Creates the main panel for the login page.
     *
     * @return The main JPanel for the login page.
     */
    private JPanel getMainJPanel() {
	    Dimension size = new Dimension(WindowConstants.DEFAULT_WINDOW_WIDTH, WindowConstants.DEFAULT_WINDOW_HEIGHT);
	    JPanel panel1 = new JPanel(new BorderLayout()); 
	    panel1.setPreferredSize(size);
        panel1.setSize(size);
        panel1.setPreferredSize(size);
        panel1.setBackground(UIColors.COLOR_BACKGROUND);
        panel1.setLayout(null);

        MouseAdapter ma = new MouseAdapter() {
            int lastX, lastY;

            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getXOnScreen();
                lastY = e.getYOnScreen();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(getLocationOnScreen().x + x - lastX, getLocationOnScreen().y + y - lastY);
                lastX = x;
                lastY = y;
            }
        };

        panel1.addMouseListener(ma);
        panel1.addMouseMotionListener(ma);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        return panel1;
    }
    
    /**
     * Adds the login text label to the login page.
     */
    private void addLoginText() {
        JLabel loginLabel = new JLabel(UITexts.LOGIN_PAGE_TEXT);
        loginLabel.setFont(UIFonts.FONT_GENERAL_UI);
        loginLabel.setForeground(UIColors.OFFWHITE);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setBounds(670, 190, 300, 50);
        mainJPanel.add(loginLabel);
    }

    /**
     * Adds the separator to the login page.
     */
    private void addSeparator() {
        JSeparator separator1 = new JSeparator();
        separator1.setOrientation(SwingConstants.VERTICAL);
        separator1.setForeground(UIColors.COLOR_OUTLINE);
        mainJPanel.add(separator1);
        separator1.setBounds(640, 184, 1, 300);
    }

    /**
     * Adds the logo to the login page.
     */
    private void addLogo() {
    
    	try {
    	    Image backgroundImage = ImageIO.read(new File(ImagePath.LOGO_PATH));
    	    int originalWidth = backgroundImage.getWidth(null);
    	    int originalHeight = backgroundImage.getHeight(null);

    	    int targetWidth = 500;
    	    int targetHeight = 250;
    	    
    	    int newWidth = originalWidth;
    	    int newHeight = originalHeight;

    	    if (originalWidth > targetWidth) {
    	        newWidth = targetWidth;
    	        newHeight = (newWidth * originalHeight) / originalWidth;
    	    }


    	    if (newHeight > targetHeight) {
    	        newHeight = targetHeight;
    	        newWidth = (newHeight * originalWidth) / originalHeight;
    	    }

    	    Image scaledImage = backgroundImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    	    ImageIcon scaledIcon = new ImageIcon(scaledImage);

    	    JLabel label1 = new JLabel(scaledIcon);
    	    label1.setBounds(250, 215, newWidth, newHeight); 
    	    label1.setFocusable(false);

    	    label1.setPreferredSize(new Dimension(newWidth, newHeight));
    	    
    	    mainJPanel.setLayout(null);
    	    mainJPanel.add(label1);
    	} catch (IOException e) { 
    	    // TODO: Log error if image loading fails
    	    System.err.println("Error loading background image: " + e.getMessage());
    	}
    }
    
    /**
     * Adds the username text field to the login page.
     */
    private void addUsernameTextField() {
    	TextField usernameField = new TextField();

        usernameField.setBounds(670, 239, 300, 50); 
        usernameField.setText(UITexts.STRING_EMPTY);
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals(UITexts.PLACEHOLDER_TEXT_USERNAME)) {
                    usernameField.setText(UITexts.STRING_EMPTY);
                }
                usernameField.setForeground(Color.white);
                usernameField.setBorderColor(UIColors.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText(UITexts.PLACEHOLDER_TEXT_USERNAME);
                }
                usernameField.setForeground(UIColors.COLOR_OUTLINE);
                usernameField.setBorderColor(UIColors.COLOR_OUTLINE);
            }
        });

        usernameField.setForeground(UIColors.COLOR_OUTLINE);
        usernameField.setText(UITexts.PLACEHOLDER_TEXT_USERNAME);
        usernameField.setBorderColor(UIColors.COLOR_OUTLINE);
        
        mainJPanel.add(usernameField);
    }

    /**
     * Adds the password text field to the login page.
     */
    private void addPasswordTextField() {
        TextFieldPassword passwordField = new TextFieldPassword();
	    passwordField.setOpaque(false);
        passwordField.setBounds(670, 304, 300, 50);
        passwordField.setText(UITexts.PLACEHOLDER_TEXT_PASSWORD);
        passwordField.addFocusListener(new FocusAdapter() {
            @SuppressWarnings("deprecation")
			@Override
            public void focusGained(FocusEvent e) {      
            	if (passwordField.getText().equals(UITexts.PLACEHOLDER_TEXT_PASSWORD)) {
            		passwordField.setText(UITexts.STRING_EMPTY);
            	}
                passwordField.setForeground(Color.white);
                passwordField.setBorderColor(UIColors.COLOR_INTERACTIVE);
            }

            @SuppressWarnings("deprecation")
			@Override
            public void focusLost(FocusEvent e) {
            	if (passwordField.getText().equals(UITexts.STRING_EMPTY))
            		passwordField.setText(UITexts.PLACEHOLDER_TEXT_PASSWORD);
            	
            	if (passwordField.getText().equals(UITexts.PLACEHOLDER_TEXT_PASSWORD))
            		passwordField.setEchoChar((char) 0);
            	else	
            		passwordField.setEchoChar('*');
            	
                passwordField.setForeground(UIColors.COLOR_OUTLINE);
                passwordField.setBorderColor(UIColors.COLOR_OUTLINE);
            }
            
            
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
        		passwordField.setEchoChar('*');

                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    loginEventHandler();
                }
            }
        });
        
        passwordField.setText(UITexts.PLACEHOLDER_TEXT_PASSWORD);
		passwordField.setEchoChar((char) 0);
		passwordField.setForeground(UIColors.COLOR_OUTLINE);
        passwordField.setBorderColor(UIColors.COLOR_OUTLINE);
          
        mainJPanel.add(passwordField);
    }


    /**
     * Adds the login button to the login page.
     */
    private void addLoginButton() {
    	 final Color[] loginButtonColors = {UIColors.COLOR_INTERACTIVE, Color.white};

         JLabel loginButton = new JLabel() {
             @Override
             protected void paintComponent(Graphics g) {
                 Graphics2D g2 = (Graphics2D) g;
                 super.paintComponent(g2);

                 Insets insets = getInsets();
                 int w = getWidth() - insets.left - insets.right;
                 int h = getHeight() - insets.top - insets.bottom;
                 g2.setColor(loginButtonColors[0]);
                 g2.fillRoundRect(insets.left, insets.top, w, h, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);

                 FontMetrics metrics = g2.getFontMetrics(UIFonts.FONT_GENERAL_UI);
                 int x2 = (getWidth() - metrics.stringWidth(UITexts.BUTTON_TEXT_LOGIN)) / 2;
                 int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                 g2.setFont(UIFonts.FONT_GENERAL_UI);
                 g2.setColor(loginButtonColors[1]);
                 g2.drawString(UITexts.BUTTON_TEXT_LOGIN, x2, y2);
             }
         };

         loginButton.addMouseListener(new MouseAdapter() {

             @Override
             public void mousePressed(MouseEvent e) {
                 loginEventHandler();
             }

             @Override
             public void mouseEntered(MouseEvent e) {
                 loginButtonColors[0] = UIColors.COLOR_INTERACTIVE_DARKER;
                 loginButtonColors[1] = UIColors.OFFWHITE;
                 loginButton.repaint();
             }

             @Override
             public void mouseExited(MouseEvent e) {
                 loginButtonColors[0] = UIColors.COLOR_INTERACTIVE;
                 loginButtonColors[1] = Color.white;
                 loginButton.repaint();
             }
         });

         loginButton.setOpaque(false); 
         loginButton.setForeground(Color.white); 
         loginButton.setHorizontalAlignment(SwingConstants.CENTER); 
         loginButton.setFont(UIFonts.FONT_GENERAL_UI); 
         loginButton.setText(UITexts.BUTTON_TEXT_REGISTER); 

         loginButton.setBounds(670, 373, 300, 50);
         loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         mainJPanel.add(loginButton);
    }

    /**
     * Adds the forgot password button to the login page.
     */
    private void addForgotPasswordButton() {
        mainJPanel.add(new HyperlinkText(UITexts.BUTTON_TEXT_FORGOT_PASS, 670, 439, () -> {
            toaster.error("There is nothing I can do.");
        }));
    }

    /**
     * Adds the register button to the login page.
     */
    private void addRegisterButton() {
        mainJPanel.add(new HyperlinkText(UITexts.BUTTON_TEXT_REGISTER, 925, 439, () -> {
            toaster.success("Register event");
            try {
				this.dispose();
				new RegistrationView();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }));
    }

    /**
     * Handles the login event.
     */
    private void loginEventHandler() {
        toaster.warn("Login event");
    }
}
