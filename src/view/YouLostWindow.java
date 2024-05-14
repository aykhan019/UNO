package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import util.constants.FontConstants;
import util.constants.ImagePath;
import util.ui.UIUtils;
import view.CustomComponents.ButtonWithImage;
import view.CustomComponents.GradientPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YouLostWindow extends BaseFrame {
	private String _message;
	private final Font customFont = UIUtils.loadCustomFont(FontConstants.RechargeFontPath);

	public YouLostWindow(String message) {
		super(util.constants.WindowConstants.YOU_LOST_WINDOW_TITLE);
		_message = message;
		initializeFrame();
	}

	@Override
	void initializeFrame() {
		JPanel mainPanel = new GradientPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(util.constants.WindowConstants.DEFAULT_WINDOW_WIDTH,
				util.constants.WindowConstants.DEFAULT_WINDOW_HEIGHT));
		mainPanel.setOpaque(false);

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setOpaque(false);
		buttonPanel.setBorder(new EmptyBorder(20, -1180, 0, 0));

		ImageIcon icon = new ImageIcon(ImagePath.BACK_ICON);
		Image image = icon.getImage();
		Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		ButtonWithImage backButton = new ButtonWithImage(scaledIcon, 50, 50);

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MainMenu();
			}
		});

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		buttonPanel.add(backButton, gbc);

		mainPanel.add(buttonPanel, BorderLayout.NORTH);

		ImageIcon imageIcon = new ImageIcon(ImagePath.YOU_LOST_IMAGE);
		Image bgImage = imageIcon.getImage();
		Image bgScaledImage = bgImage.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
		ImageIcon bgScaledImageIcon = new ImageIcon(bgScaledImage);

		JLabel imageLabel = new JLabel(bgScaledImageIcon);
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

		mainPanel.add(imageLabel, BorderLayout.CENTER);

		JLabel messageLabel = new JLabel(_message);
		messageLabel.setFont(customFont.deriveFont(Font.PLAIN, 22));
		messageLabel.setForeground(Color.white);
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setBorder(new EmptyBorder(0, 0, 70, 0));
		mainPanel.add(messageLabel, BorderLayout.SOUTH);

		add(mainPanel);
		setVisible(true);
	}
}
