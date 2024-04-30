package view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import data.UserRepository;
import data.UserStatisticRepository;
import model.UserStatistic;
import util.constants.FontConstants;
import util.constants.ImagePath;
import util.ui.UIUtils;

@SuppressWarnings("serial")
public class LeaderboardView extends BaseFrame {
    private JScrollPane scrollPane;
    private JPanel leaderboardPanel;
    private final int ROW_HEIGHT = 100; // Constant row height
    private final int ROW_WIDTH = 500; // Constant row width

    public LeaderboardView(String title) {
        super(title);
        initializeFrame();
        
	        
	    
    }

    @Override
    void initializeFrame() {
        // Create a JPanel with gradient background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                Color color1 = new Color(20, 136, 204); // First color of the gradient
                Color color2 = new Color(43, 50, 178); // Second color of the gradient

                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);

                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            }
        };

        // Set the layout for the background panel
        backgroundPanel.setLayout(new BorderLayout());

        // Set the content pane of LeaderboardView to the background panel
        setContentPane(backgroundPanel);

        // Create leaderboard panel
        leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));

        // Create scroll pane and add leaderboard panel to it
        scrollPane = new JScrollPane(leaderboardPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Add scroll pane to background panel
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Load and display leaderboard data
        try {
            displayLeaderboard();
            setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading leaderboard data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void setBackgroundImage(String imagePath) {
        // Code to set background image
    }

    private void displayLeaderboard() throws IOException {
        List<UserStatistic> userStatisticsList = UserStatisticRepository.getUserStatistics();
        
        var counter = 0;
        Font customFont = UIUtils.loadCustomFont(FontConstants.RechargeFontPath); // Load custom font
    	
        
        for (UserStatistic userStatistic : userStatisticsList) {
            var user = UserRepository.getUserById(userStatistic.getUserId());

            JPanel rowPanel = new JPanel();
            rowPanel.setPreferredSize(new Dimension(750, ROW_HEIGHT)); // Reduced width of the row
            rowPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Added border color
            rowPanel.setBackground(counter % 2 == 0 ? new Color(20, 136, 204) : new Color(43, 50, 178)); // Alternating row colors
            rowPanel.setBorder(BorderFactory.createEmptyBorder(20,70, 20, 70)); // Added padding

            rowPanel.setLayout(new BorderLayout()); // Use BorderLayout for rowPanel
            
            // Create a panel for the first three properties (rank, user image, username)
            JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Left-aligned layout with horizontal gap
            leftPanel.setOpaque(false); // Make the panel transparent

            JLabel rankLabel = new JLabel(Integer.toString(counter + 1));
            rankLabel.setForeground(Color.WHITE);
            rankLabel.setFont(customFont.deriveFont(Font.PLAIN, 35)); // Apply custom font
            rankLabel.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 50)); // Added padding
            rankLabel.setSize(75, 75);
            rankLabel.setBackground(Color.gray);
            rankLabel.repaint();a
            leftPanel.add(rankLabel);

            // Load and display user image
            ImageIcon userImageIcon = getUserImage(userStatistic.getUserId());
            if (userImageIcon != null) {
                // Resize the ImageIcon to a fixed size
                int imageSize = 50; // Set the desired size for the user image
                Image scaledImage = userImageIcon.getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
                ImageIcon scaledUserImageIcon = new ImageIcon(scaledImage);

                JLabel userImageLabel = new JLabel(scaledUserImageIcon);
                leftPanel.add(userImageLabel);
            } else {
                // Handle case where user image is not available
                JLabel noImageLabel = new JLabel("No Image");
                leftPanel.add(noImageLabel);
            }

            JLabel nameLabel = new JLabel(user.getUsername());
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(customFont.deriveFont(Font.PLAIN, 20)); // Apply custom font
            leftPanel.add(nameLabel);

            // Add the left panel to the row panel at the BorderLayout.WEST position
            rowPanel.add(leftPanel, BorderLayout.WEST);
            
            // Create a panel for the fourth property (total score)
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Right-aligned layout with horizontal gap
            rightPanel.setOpaque(false); // Make the panel transparent

            JLabel totalScoreLabel = new JLabel(Integer.toString(userStatistic.getTotalScore()));
            totalScoreLabel.setForeground(Color.WHITE);
            totalScoreLabel.setFont(customFont.deriveFont(Font.PLAIN, 20)); // Apply custom font
            rightPanel.add(totalScoreLabel);

            // Add the right panel to the row panel at the BorderLayout.EAST position
            rowPanel.add(rightPanel, BorderLayout.EAST);

            JPanel container = new JPanel();
            container.setSize(ROW_WIDTH, ROW_HEIGHT);
            container.setBackground(Color.red);
            
            container.add(rowPanel);
            
            // Add some spacing between rows
            leaderboardPanel.add(container);
            leaderboardPanel.add(Box.createVerticalStrut(10)); // Add some spacing between rows
            
            counter++;
        }
    }




    private ImageIcon getUserImage(String userId) {
        ImageIcon imageIcon = new ImageIcon(ImagePath.DEFAULT_BACKGROUND_IMAGE_PATH);
        return imageIcon;
    }
}
