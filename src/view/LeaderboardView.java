package view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import data.UserRepository;
import data.UserStatisticRepository;
import model.User;
import model.UserStatistic;
import util.constants.FontConstants;
import util.constants.ImagePath;
import util.constants.UIColors;
import util.constants.UITexts;
import util.constants.WindowConstants;
import util.ui.UIUtils;
import view.CustomComponents.ButtonWithImage;

/**
 * The leaderboard page view of the application.
 * This class represents the graphical user interface for the leaderboard functionality.
 */
@SuppressWarnings("serial")
public class LeaderboardView extends BaseFrame {
    /**
     * The main JPanel of the leaderboard page.
     */
	private JPanel leaderboardPanel;
	
	 /**
     * The main font of the leaderboard page.
     */
    private final Font customFont = UIUtils.loadCustomFont(FontConstants.RechargeFontPath);

    /**
     * Constructs a new LeaderboardView.
     */
	public LeaderboardView() {
		super(WindowConstants.LEADERBOARD_WINDOW_TITLE);
		initializeFrame();
	}
	
	 /**
     * Initializes the frame components.
     */
	@Override
	void initializeFrame() {
	    JPanel backgroundPanel = new JPanel(new BorderLayout()) {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);

	            Graphics2D g2d = (Graphics2D) g.create();

	            Color color1 = new Color(20, 136, 204); 
	            Color color2 = new Color(43, 50, 178); 

	            GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);

	            g2d.setPaint(gradient);
	            g2d.fillRect(0, 0, getWidth(), getHeight());

	            g2d.dispose();
	        }
	    };
	    backgroundPanel.setOpaque(false);
	    setContentPane(backgroundPanel);

	    leaderboardPanel = new JPanel(new BorderLayout());

	    ImageIcon icon = new ImageIcon(ImagePath.BACK_ICON);
	    Image image = icon.getImage();
	    Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	    ImageIcon scaledIcon = new ImageIcon(scaledImage);

	    ButtonWithImage backButton = new ButtonWithImage(scaledIcon, 50, 50);

	    backButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
		         // TODO
	        }
	    });

	    JLabel titleLabel = new JLabel(UITexts.LEADERBOARD_HEADLINE.toUpperCase());
	    titleLabel.setFont(customFont.deriveFont(Font.PLAIN, 32));
	    titleLabel.setForeground(UIColors.OFFWHITE);
	    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 390, 0, 0));

	    JPanel titlePanel = new JPanel(new BorderLayout());
	    titlePanel.setOpaque(false);
	    titlePanel.setForeground(UIColors.OFFBLACK);    
	    titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

	    titlePanel.add(backButton, BorderLayout.WEST);
	    titlePanel.add(titleLabel, BorderLayout.CENTER);

	    backgroundPanel.add(titlePanel, BorderLayout.NORTH);

	    try {
	        displayLeaderboard();
	        setVisible(true);
	    } catch (IOException e) {
	    	// TODO logger
	        e.printStackTrace();
	    }
	}

	 /**
     * Display the leaderboard table.
     */
	private void displayLeaderboard() throws IOException {
	    leaderboardPanel.removeAll();

	    List<UserStatistic> userStatisticsList = UserStatisticRepository.getUserStatistics();
	    Collections.sort(userStatisticsList, Comparator.comparingInt(UserStatistic::getTotalScore).reversed());

	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn(UITexts.LEADERBOARD_COLUMN_RANK);
	    model.addColumn(UITexts.LEADERBOARD_COLUMN_USERNAME);
	    model.addColumn(UITexts.LEADERBOARD_COLUMN_SCORE);

	    int rank = 1;
	    for (UserStatistic userStatistic : userStatisticsList) {
	        var user = UserRepository.getUserById(userStatistic.getUserId());
	        String username = user != null ? user.getUsername() : UITexts.UNKNOWN;

	        model.addRow(new Object[]{rank, username, userStatistic.getTotalScore()});
	        rank++;
	    }

	    JTable leaderboardTable = new JTable(model) {
	        @Override
	        public boolean getScrollableTracksViewportWidth() {
	            return getPreferredSize().width < getParent().getWidth();
	        }

	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }

	        @Override
	        public TableCellRenderer getCellRenderer(int row, int column) {
	            return new DefaultTableCellRenderer() {
	                @Override
	                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);

                       if (row == 0) {
	                        c.setForeground(new Color(249,161,20)); // Gold
	                    } else if (row == 1) {
	                        c.setForeground(new Color(192,192,192)); // Silver
	                    } else if (row == 2) {
	                        c.setForeground(new Color(205,127,50)); // Bronze
	                    } else {
	                        c.setForeground(Color.WHITE); 
	                    }
	                    
	                    return c;
	                }
	            };
	        }
	    };
	    
	    leaderboardTable.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            if (e.getClickCount() == 2) {
	                JTable target = (JTable)e.getSource();
	                int row = target.getSelectedRow();

	                UserStatistic selectedUserStatistic = userStatisticsList.get(row);

	                try {
	                    User selectedUser = UserRepository.getUserById(selectedUserStatistic.getUserId());
	                    new UserProfileView(selectedUser);
	                } catch (IOException ex) {
	                    // TODO: Handle exception
	                    ex.printStackTrace();
	                }
	            }
	        }

	        @Override
	        public void mouseEntered(MouseEvent e) {
	            leaderboardTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	            leaderboardTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	        }
	    });

	    leaderboardTable.setFont(customFont.deriveFont(Font.PLAIN, 20));
	    leaderboardTable.setForeground(Color.WHITE);
	    leaderboardTable.setBackground(new Color(20, 136, 204));
	    leaderboardTable.setRowHeight(50);
	    leaderboardTable.getTableHeader().setFont(customFont.deriveFont(Font.BOLD, 25));
	    leaderboardTable.getTableHeader().setForeground(Color.WHITE);
	    leaderboardTable.getTableHeader().setBackground(Color.BLACK);
	    leaderboardTable.getTableHeader()
	            .setPreferredSize(new Dimension(leaderboardTable.getColumnModel().getTotalColumnWidth(), 50));

	    JScrollPane leaderboardScrollPane = new JScrollPane(leaderboardTable);
	    leaderboardScrollPane.setPreferredSize(new Dimension(800, 500));
	    leaderboardScrollPane.setBackground(new Color(20, 136, 204));

	    leaderboardScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
	        @Override
	        protected void configureScrollBarColors() {
	            this.thumbColor = new Color(255, 255, 255, 100);
	            this.thumbDarkShadowColor = new Color(255, 255, 255, 150);
	            this.thumbHighlightColor = new Color(255, 255, 255, 100);
	            this.thumbLightShadowColor = new Color(255, 255, 255, 100);
	            this.trackColor = new Color(20, 136, 204);
	            this.trackHighlightColor = new Color(43, 50, 178);
	        }

	        @Override
	        protected JButton createDecreaseButton(int orientation) {
	            return createZeroButton();
	        }

	        @Override
	        protected JButton createIncreaseButton(int orientation) {
	            return createZeroButton();
	        }

	        private JButton createZeroButton() {
	            JButton button = new JButton();
	            button.setPreferredSize(new Dimension(0, 0));
	            button.setMinimumSize(new Dimension(0, 0));
	            button.setMaximumSize(new Dimension(0, 0));
	            return button;
	        }
	    });

	    JPanel tablePanel = new JPanel(new BorderLayout()) {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);

	            Graphics2D g2d = (Graphics2D) g.create();

	            Color color1 = new Color(20, 136, 204); // First color of the gradient
	            Color color2 = new Color(43, 50, 178); // Second color of the gradien

	            GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);

	            g2d.setPaint(gradient);
	            g2d.fillRect(0, 0, getWidth(), getHeight());

	            g2d.dispose();
	        }
	    };
	    
	    tablePanel.add(leaderboardScrollPane, BorderLayout.CENTER);
	    tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));


	    leaderboardPanel.add(tablePanel, BorderLayout.CENTER);

	    getContentPane().add(leaderboardPanel, BorderLayout.CENTER);

	    revalidate();
	    repaint();
	}
}
