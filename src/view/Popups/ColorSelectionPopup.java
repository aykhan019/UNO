package view.Popups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.enums.Color;

public class ColorSelectionPopup extends JDialog {
	private model.enums.Color selectedColor;

	public ColorSelectionPopup(JFrame parent) {
		super(parent, "Choose Color", true);
		setSize(300, 150);
		setLocationRelativeTo(parent);
		setLayout(new GridLayout(2, 2));

		JButton redButton = new JButton("Red");
		JButton blueButton = new JButton("Blue");
		JButton greenButton = new JButton("Green");
		JButton yellowButton = new JButton("Yellow");

		redButton.addActionListener(new ColorButtonListener(model.enums.Color.RED));
		blueButton.addActionListener(new ColorButtonListener(model.enums.Color.BLUE));
		greenButton.addActionListener(new ColorButtonListener(model.enums.Color.GREEN));
		yellowButton.addActionListener(new ColorButtonListener(model.enums.Color.YELLOW));

		add(redButton);
		add(blueButton);
		add(greenButton);
		add(yellowButton);
	}

	public model.enums.Color getSelectedColor() {
		return selectedColor;
	}

	private class ColorButtonListener implements ActionListener {
		private model.enums.Color color;

		public ColorButtonListener(model.enums.Color color) {
			this.color = color;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedColor = color;
			dispose();
		}
	}
}
