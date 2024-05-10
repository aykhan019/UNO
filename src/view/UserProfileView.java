package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import data.UserStatisticRepository;
import model.user.User;
import model.user.UserStatistic;
import util.constants.FontConstants;
import util.constants.ImagePath;
import util.constants.UITexts;
import util.constants.WindowConstants;
import util.ui.UIUtils;
import view.CustomComponents.ButtonWithImage;
import view.CustomComponents.GradientPanel;

/**
 * The user profile page view of the application. This class represents the
 * graphical user interface for displaying user profile information.
 */
@SuppressWarnings("serial")
public class UserProfileView extends BaseFrame {
	/**
	 * Represents a user in the system.
	 */
	private User user;

	/**
	 * Represents the statistics associated with a user.
	 */
	private UserStatistic userStatistic;

	/**
	 * Represents the previous page class.
	 */
	private Class<?> previousPage;

	/**
	 * The main font of the leaderboard page.
	 */
	private final Font customFont = UIUtils.loadCustomFont(FontConstants.RechargeFontPath);

	/**
	 * Constructs a new UserProfileView.
	 *
	 * @param user The user whose profile is being displayed.
	 */
	public UserProfileView(User user, Class<?> previousPage) {
		super(WindowConstants.USER_PROFILE_WINDOW_TITLE_PREFIX + user.getUsername());

		this.user = user;
		this.previousPage = previousPage;
		
		try {
			this.userStatistic = UserStatisticRepository.getUserStatisticById(user.getId());
		} catch (IOException e) {
			this.userStatistic = new UserStatistic(user.getId());
			// TODO logger
			e.printStackTrace();
		}
		initializeFrame();
	}

	/**
	 * Initializes the frame with user details and graphical components.
	 * Displays user information including username, email, game statistics, and profile picture.
	 * Allows navigation to the previous page.
	 */
	@Override
	void initializeFrame() {
	    JPanel mainPanel = new GradientPanel();
	    mainPanel.setLayout(new BorderLayout());
	    
	    JLabel usernameLabel = new JLabel(UITexts.USER_DETAILS_USERNAME + user.getUsername());
	    JLabel emailLabel = new JLabel(UITexts.USER_DETAILS_EMAIL + user.getEmail());
	    JLabel gamesPlayedLabel = new JLabel(UITexts.USER_DETAILS_GAMES_PLAYED + userStatistic.getNumberOfGamesPlayed());
	    JLabel winsLabel = new JLabel(UITexts.USER_DETAILS_WINS + userStatistic.getNumberOfWins());
	    JLabel lossesLabel = new JLabel(UITexts.USER_DETAILS_LOSSES + userStatistic.getNumberOfLosses());
	    JLabel totalScoreLabel = new JLabel(UITexts.USER_DETAILS_TOTAL_SCORE + userStatistic.getTotalScore());
	    JLabel avgScoreLabel = new JLabel(UITexts.USER_DETAILS_AVERAGE_SCORE + String.format("%.2f", userStatistic.getAverageScore()));
	    JLabel winLossRatioLabel = new JLabel(UITexts.USER_DETAILS_WIN_LOSS_RATIO + String.format("%.2f",userStatistic.getWinLossRatio()));

	    ImageIcon profileImageIcon = new ImageIcon(ImagePath.DEFAULT_USER_PROFILE);
	    Image profileImage = profileImageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); 
	    ImageIcon scaledProfileImageIcon = new ImageIcon(profileImage);
	    JLabel profileImageLabel = new JLabel(scaledProfileImageIcon);
	    profileImageLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0)); 

	    JPanel profileImagePanel = new JPanel(new BorderLayout());
	    profileImagePanel.setOpaque(false);
	    profileImagePanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0)); 
	    profileImagePanel.add(profileImageLabel, BorderLayout.CENTER);
	    
	    JPanel userInfoTextPanel = new JPanel(new GridLayout(8, 1, 0, 0)); 
	    userInfoTextPanel.setPreferredSize(new Dimension(700, 300));
	    userInfoTextPanel.setOpaque(false);
	    userInfoTextPanel.setBorder(BorderFactory.createEmptyBorder(0,50,0,0));

	    var derivedFont = customFont.deriveFont(21f);
	    Color textColor = Color.WHITE;
	    usernameLabel.setFont(derivedFont);
	    usernameLabel.setForeground(textColor);
	    emailLabel.setFont(derivedFont);
	    emailLabel.setForeground(textColor);
	    gamesPlayedLabel.setFont(derivedFont);
	    gamesPlayedLabel.setForeground(textColor);
	    winsLabel.setFont(derivedFont);
	    winsLabel.setForeground(textColor);
	    lossesLabel.setFont(derivedFont);
	    lossesLabel.setForeground(textColor);
	    totalScoreLabel.setFont(derivedFont);
	    totalScoreLabel.setForeground(textColor);
	    avgScoreLabel.setFont(derivedFont);
	    avgScoreLabel.setForeground(textColor);
	    winLossRatioLabel.setFont(derivedFont);
	    winLossRatioLabel.setForeground(textColor);

	    userInfoTextPanel.add(usernameLabel);
	    userInfoTextPanel.add(emailLabel);
	    userInfoTextPanel.add(gamesPlayedLabel);
	    userInfoTextPanel.add(winsLabel);
	    userInfoTextPanel.add(lossesLabel);
	    userInfoTextPanel.add(totalScoreLabel);
	    userInfoTextPanel.add(avgScoreLabel);
	    userInfoTextPanel.add(winLossRatioLabel);

	    mainPanel.add(profileImagePanel, BorderLayout.CENTER); 
	    mainPanel.add(userInfoTextPanel, BorderLayout.EAST);

	    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
	    
	    ImageIcon icon = new ImageIcon(ImagePath.BACK_ICON);
	    Image image = icon.getImage();
	    Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	    ImageIcon scaledIcon = new ImageIcon(scaledImage);

	    ButtonWithImage goBackButton = new ButtonWithImage(scaledIcon, 50, 50);

	    goBackButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	           	goBack();
	        }
	    });

	    goBackButton.setSize(50, 50);

	
	    JPanel goBackButtonPanel = new GradientPanel();	    

	    goBackButtonPanel.setOpaque(false);
	    goBackButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
	    goBackButtonPanel.add(goBackButton, BorderLayout.NORTH);

	    mainPanel.add(goBackButtonPanel, BorderLayout.WEST);

	    getContentPane().add(mainPanel, BorderLayout.CENTER);

	    JPanel southPanel = new JPanel();
	    southPanel.setBackground(new Color(0,0,139)); 
	    southPanel.setPreferredSize(new Dimension(getWidth(), 50)); 
	    
	    JPanel northPanel = new JPanel();
	    northPanel.setBackground(new Color(0,0,139));
	    northPanel.setPreferredSize(new Dimension(getWidth(), 50));
	    
	    JLabel userDetailsLabel = new JLabel(UITexts.USER_DETAILS_TITLE.toUpperCase());
	    userDetailsLabel.setFont(customFont.deriveFont(Font.BOLD, 30)); 
	    userDetailsLabel.setForeground(Color.cyan); 
	    userDetailsLabel.setHorizontalAlignment(SwingConstants.CENTER); 
	    userDetailsLabel.setBorder(BorderFactory.createEmptyBorder(2,0,0,0));
	    
	    getContentPane().add(southPanel, BorderLayout.SOUTH);
	    getContentPane().add(northPanel, BorderLayout.NORTH);
	    
	    northPanel.add(userDetailsLabel);

	    setVisible(true);
	}

	/**
	 * Method to navigate back to the leaderboard view.
	 */
	private void goBack() {
	    dispose();
	    try {
	        this.previousPage.getConstructor().newInstance();
	    } catch (Exception e) {
	    	// TODO logger
	        e.printStackTrace(); 
	    }
	}
}
