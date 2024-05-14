package view;

import javax.imageio.ImageIO;

import javax.swing.*;

import data.UserRepository;
import data.UserStatisticRepository;
import model.user.User;
import model.user.UserStatistic;
import util.constants.*;
import util.constants.WindowConstants;
import util.helpers.Logger;
import util.session.CurrentUserManager;
import util.ui.UIUtils;
import util.ui.toaster.Toaster;
import view.CustomComponents.GradientPanel;
import view.CustomComponents.HyperlinkText;
import view.CustomComponents.TextField;
import view.CustomComponents.TextFieldPassword;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * The login page view of the application.
 * This class represents the graphical user interface for the login functionality.
 */
@SuppressWarnings("serial")
public class RegistrationView extends BaseFrame {
	
	/**
	 * The toaster object for displaying notifications.
	 */
    private final Toaster toaster;
    
    /**
     * The main JPanel of the login page.
     */
    JPanel mainJPanel = getMainJPanel();
    
    /**
     * The password field for the login page.
     */
    private TextFieldPassword passwordField;

    /**
     * The username field for the login page.
     */
    private TextField usernameField;
    /**
     * 
     * The email field for the login page.
     */
    private TextField emailField;
    
    /**
     * Constructs a new LoginPageView.
     *
     * @param backgroundImage The path to the background image.
     */
    public RegistrationView() {
        super(WindowConstants.REGISTRATION_WINDOW_TITLE);
        toaster = new Toaster(mainJPanel);
        initializeFrame();
    }

    /**
     * Initializes the frame components.
     */
	@Override
    public void initializeFrame() {
     	addLogo();
     	addRegistrationText();
        addSeparator();
        addEmailTextField();
        addUsernameTextField();
	    addPasswordTextField();
        addLoginButton();
	    addRegisterButton();	   
	    
	    this.add(mainJPanel);
    	
        setVisible(true);

        getContentPane().requestFocusInWindow();
        getContentPane().requestFocusInWindow(); // requesting 1 time does not help :(
    }
    
    /**
     * Creates the main panel for the login page.
     *
     * @return The main JPanel for the login page.
     */
	private JPanel getMainJPanel() {
	    JPanel panel1 = new GradientPanel();

	    Dimension size = new Dimension(WindowConstants.DEFAULT_WINDOW_WIDTH, WindowConstants.DEFAULT_WINDOW_HEIGHT);
	    panel1.setPreferredSize(size);
	    panel1.setSize(size);
	    panel1.setPreferredSize(size);
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
    private void addRegistrationText() {
        JLabel loginLabel = new JLabel(UITexts.REGISTRATION_PAGE_TEXT);
        loginLabel.setFont(UIFonts.FONT_GENERAL_UI);
        loginLabel.setForeground(UIColors.OFFWHITE);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setBounds(670, 155, 300, 50);
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
        separator1.setBounds(640, 154, 1, 340);
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
			Logger.log(ErrorConstants.BACKGROUND_IMAGE_ERROR, FileConstants.ERROR_LOGS_FILE_PATH);
    	}
    }
   
    /**
     * Adds the email text field to the login page.
     */
    private void addEmailTextField() {
    	TextField emaailField = new TextField();

    	emaailField.setBounds(670, 204, 300, 50); 
    	emaailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emaailField.getText().equals(UITexts.PLACEHOLDER_TEXT_EMAIL)) {
                	emaailField.setText(UITexts.STRING_EMPTY);
                }
                emaailField.setForeground(UIColors.OFFBLACK);
                emaailField.setBorderColor(UIColors.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emaailField.getText().isEmpty()) {
                	emaailField.setText(UITexts.PLACEHOLDER_TEXT_EMAIL);
                }
                emaailField.setForeground(UIColors.COLOR_OUTLINE);
                emaailField.setBorderColor(UIColors.COLOR_OUTLINE);
            }
        });
    	
    	emaailField.setForeground(UIColors.COLOR_OUTLINE);
    	emaailField.setText(UITexts.PLACEHOLDER_TEXT_EMAIL);
    	emaailField.setBorderColor(UIColors.COLOR_OUTLINE);
    	
        mainJPanel.add(emaailField);
        
        this.emailField = emaailField;
    }      
    /**
     * Adds the username text field to the login page.
     */
    private void addUsernameTextField() {
    	TextField usernameField = new TextField();

        usernameField.setBounds(670, 269, 300, 50); 
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals(UITexts.PLACEHOLDER_TEXT_USERNAME)) {
                    usernameField.setText(UITexts.STRING_EMPTY);
                }
                usernameField.setForeground(UIColors.OFFBLACK);
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
        
        this.usernameField = usernameField;
    }

    /**
     * Adds the password text field to the login page.
     */
    private void addPasswordTextField() {
        TextFieldPassword passwordField = new TextFieldPassword();
	    passwordField.setOpaque(false);
        passwordField.setBounds(670, 334, 300, 50);
        passwordField.setText(UITexts.PLACEHOLDER_TEXT_PASSWORD);
        passwordField.addFocusListener(new FocusAdapter() {
            @SuppressWarnings("deprecation")
			@Override
            public void focusGained(FocusEvent e) {      
            	if (passwordField.getText().equals(UITexts.PLACEHOLDER_TEXT_PASSWORD)) {
            		passwordField.setText(UITexts.STRING_EMPTY);
            	}
                passwordField.setForeground(UIColors.OFFBLACK);
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
                	registerEventHandler();
                }
            }
        });
        
        passwordField.setText(UITexts.PLACEHOLDER_TEXT_PASSWORD);
		passwordField.setEchoChar((char) 0);
		passwordField.setForeground(UIColors.COLOR_OUTLINE);
        passwordField.setBorderColor(UIColors.COLOR_OUTLINE);
          
        mainJPanel.add(passwordField);
        
        this.passwordField = passwordField;
    }


    /**
     * Adds the login button to the login page.
     */
    private void addRegisterButton() {
    	final Color[] registerButtonColors = {UIColors.COLOR_INTERACTIVE, Color.white};

        JLabel registerButton = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                super.paintComponent(g2);

                Insets insets = getInsets();
                int w = getWidth() - insets.left - insets.right;
                int h = getHeight() - insets.top - insets.bottom;
                g2.setColor(registerButtonColors[0]);
                g2.fillRoundRect(insets.left, insets.top, w, h, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);

                FontMetrics metrics = g2.getFontMetrics(UIFonts.FONT_GENERAL_UI);
                int x2 = (getWidth() - metrics.stringWidth(UITexts.BUTTON_TEXT_REGISTER)) / 2;
                int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.setFont(UIFonts.FONT_GENERAL_UI);
                g2.setColor(registerButtonColors[1]);
                g2.drawString(UITexts.BUTTON_TEXT_REGISTER, x2, y2);
            }
        };

        registerButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	registerEventHandler();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            	registerButtonColors[0] = UIColors.COLOR_INTERACTIVE_DARKER;
                registerButtonColors[1] = UIColors.OFFWHITE;
                registerButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	registerButtonColors[0] = UIColors.COLOR_INTERACTIVE;
                registerButtonColors[1] = Color.white;
                registerButton.repaint();
            }
        });

        registerButton.setOpaque(false);
        registerButton.setForeground(Color.white);
        registerButton.setHorizontalAlignment(SwingConstants.CENTER); 
        registerButton.setFont(UIFonts.FONT_GENERAL_UI);
        registerButton.setText(UITexts.BUTTON_TEXT_REGISTER); 

        registerButton.setBounds(670, 403, 300, 50);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainJPanel.add(registerButton);
    }

    /**
     * Adds the register button to the login page.
     */
    private void addLoginButton() {
        HyperlinkText registerLink = new HyperlinkText(UITexts.BUTTON_TEXT_LOGIN, 940, 469, () -> {
            try {
                this.dispose();
                new LoginPageView();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });

        registerLink.setForeground(UIColors.OFFWHITE); 

        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerLink.setForeground(UIColors.COLOR_INTERACTIVE); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLink.setForeground(UIColors.OFFWHITE); 
            }
        });

        mainJPanel.add(registerLink);
    }
    
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    /**
     * Handles the login event.
     */
    private void registerEventHandler() {
        try {
            var email = emailField.getText().trim();
            var username = usernameField.getText().trim();
            var password = new String(passwordField.getPassword()).trim();

            if (email.equals(UITexts.PLACEHOLDER_TEXT_EMAIL)
                    || username.equals(UITexts.PLACEHOLDER_TEXT_USERNAME)
                    || password.equals(UITexts.BUTTON_TEXT_FORGOT_PASS)) {
                toaster.warn(WarningConstants.FILL_INPUTS_WARNING);
                return;
            }

            if (email.isEmpty() ||
                    username.isEmpty() ||
                    password.isEmpty()) {
                toaster.error(ErrorConstants.EMPTY_FIELD);
                return;
            }

            if (!Pattern.matches(EMAIL_REGEX, email)) {
                toaster.error(ErrorConstants.INVALID_EMAIL_FORMAT);
                return;
            }

            if (email.contains(FileConstants.USER_DATA_SEPARATOR)
                    || username.contains(FileConstants.USER_DATA_SEPARATOR)
                    || password.contains(FileConstants.USER_DATA_SEPARATOR)) {
                toaster.error(ErrorConstants.INVALID_CHARACTER);
                return;
            }

            if (UserRepository.emailExists(email)) {
                toaster.error(ErrorConstants.EMAIL_USED);
                return;
            }

            if (UserRepository.usernameTaken(username)) {
                toaster.error(ErrorConstants.USERNAME_TAKEN);
                return;
            }

            var newUser = new User(username, email, password);
            UserRepository.addUser(newUser);
            CurrentUserManager.getInstance().setCurrentUser(newUser);
            
            var newUserStatistic = new UserStatistic(newUser.getId());
            UserStatisticRepository.addUserStatistic(newUserStatistic);
            
            toaster.success(UITexts.WELCOME);
            
            dispose();
            
        	new LeaderboardView(); 
        } catch (IOException e) {
            toaster.error(ErrorConstants.UNKNOWN_ERROR);
			Logger.log(e.getMessage(), FileConstants.ERROR_LOGS_FILE_PATH);
        }
    }

}
