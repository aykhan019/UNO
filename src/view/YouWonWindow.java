package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import util.constants.ImagePath;
import view.CustomComponents.ButtonWithImage;
import view.CustomComponents.GradientPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YouWonWindow extends BaseFrame {
	public YouWonWindow() {
		super(util.constants.WindowConstants.YOU_WON_WINDOW_TITLE);
		initializeFrame();
	}

	@Override
	void initializeFrame() {
		JPanel mainPanel = new GradientPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(util.constants.WindowConstants.DEFAULT_WINDOW_WIDTH,
				util.constants.WindowConstants.DEFAULT_WINDOW_HEIGHT));
		mainPanel.setOpaque(false);
		mainPanel.setPreferredSize(new Dimension(util.constants.WindowConstants.DEFAULT_WINDOW_WIDTH,
				mainPanel.getPreferredSize().height));
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

		ImageIcon imageIcon = new ImageIcon(ImagePath.YOU_WON_IMAGE);
		Image bgImage = imageIcon.getImage();
		Image bgScaledImage = bgImage.getScaledInstance(400, 500, Image.SCALE_SMOOTH);
		ImageIcon bgScaledImageIcon = new ImageIcon(bgScaledImage);

		JLabel imageLabel = new JLabel(bgScaledImageIcon);
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

		mainPanel.add(imageLabel, BorderLayout.CENTER);

		add(mainPanel);
		setVisible(true);
	}
}
