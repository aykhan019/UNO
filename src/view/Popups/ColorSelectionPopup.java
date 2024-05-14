package view.Popups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Popup dialog for selecting a color.
 */
@SuppressWarnings("serial")
public class ColorSelectionPopup extends JDialog {
    private model.enums.Color selectedColor;

    /**
     * Constructs a color selection popup dialog.
     * 
     * @param parent the parent frame
     */
    public ColorSelectionPopup(JFrame parent) {
        super(parent, "Choose Color", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(2, 2));

        JButton redButton = new JButton(model.enums.Color.RED.toString());
        JButton blueButton = new JButton(model.enums.Color.BLUE.toString());
        JButton greenButton = new JButton(model.enums.Color.GREEN.toString());
        JButton yellowButton = new JButton(model.enums.Color.YELLOW.toString());

        redButton.addActionListener(new ColorButtonListener(model.enums.Color.RED));
        blueButton.addActionListener(new ColorButtonListener(model.enums.Color.BLUE));
        greenButton.addActionListener(new ColorButtonListener(model.enums.Color.GREEN));
        yellowButton.addActionListener(new ColorButtonListener(model.enums.Color.YELLOW));

        add(redButton);
        add(blueButton);
        add(greenButton);
        add(yellowButton);
    }

    /**
     * Gets the selected color.
     * 
     * @return the selected color
     */
    public model.enums.Color getSelectedColor() {
        return selectedColor;
    }

    /**
     * Listener for color selection buttons.
     */
    private class ColorButtonListener implements ActionListener {
        private model.enums.Color color;

        /**
         * Constructs a ColorButtonListener.
         * 
         * @param color the color associated with this listener
         */
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
