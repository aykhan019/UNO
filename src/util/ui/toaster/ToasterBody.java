package util.ui.toaster;

import javax.swing.*;

import util.constants.UIFonts;
import util.ui.UIUtils;

import java.awt.*;

/**
 * Represents the body of the toaster.
 */
class ToasterBody extends JPanel {
	/**
	 * The serial version UID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The padding value for the toaster.
	 */
	private static final int TOAST_PADDING = 15;

	/**
	 * The width of the toaster.
	 */
	private final int toastWidth;

	/**
	 * The message to be displayed on the toaster.
	 */
	private final String message;

	/**
	 * The background color of the toaster.
	 */
	private final Color backgroundColor;

	/**
	 * A boolean flag indicating whether the toaster should stop displaying.
	 */
	private volatile boolean stopDisplaying;

	/**
	 * The height of the toaster body.
	 */
	private int heightOfToast;

	/**
	 * The x-coordinate position for drawing the string message.
	 */
	private int stringPosX;

	/**
	 * The y-coordinate position for drawing the string message.
	 */
	private int stringPosY;

	/**
	 * The y-coordinate position of the toaster.
	 */
	private int yPos;

	/**
	 * The panel on which the toaster is displayed.
	 */
	private JPanel panelToToastOn;

	/**
	 * Constructs a ToasterBody object.
	 *
	 * @param panelToToastOn The panel on which the toaster body will be displayed.
	 * @param message        The message to be displayed.
	 * @param bgColor        The background color of the toaster body.
	 * @param yPos           The y-coordinate position of the toaster body.
	 */
	public ToasterBody(JPanel panelToToastOn, String message, Color bgColor, int yPos) {
		this.panelToToastOn = panelToToastOn;
		this.message = message;
		this.yPos = yPos;
		this.backgroundColor = bgColor;

		FontMetrics metrics = getFontMetrics(UIFonts.FONT_GENERAL_UI);
		int stringWidth = metrics.stringWidth(this.message);

		toastWidth = stringWidth + (TOAST_PADDING * 2);
		heightOfToast = metrics.getHeight() + TOAST_PADDING;
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setOpaque(false);
		setBounds((panelToToastOn.getWidth() - toastWidth) / 2, (int) -(Math.round(heightOfToast / 10.0) * 10),
				toastWidth, heightOfToast);

		stringPosX = (getWidth() - stringWidth) / 2;
		stringPosY = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

		new Thread(() -> {
			while (getBounds().y < yPos) {
				int i1 = (yPos - getBounds().y) / 10;
				i1 = i1 <= 0 ? 1 : i1;
				setBounds((panelToToastOn.getWidth() - toastWidth) / 2, getBounds().y + i1, toastWidth, heightOfToast);
				repaint();
				try {
					Thread.sleep(5);
				} catch (Exception ignored) {
				}
			}
		}).start();
	}

	/**
	 * Overrides the paintComponent method to customize the appearance of the
	 * toaster body.
	 *
	 * @param g The Graphics object used for painting.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = UIUtils.get2dGraphics(g);
		super.paintComponent(g2);

		g2.setColor(backgroundColor);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);

		g2.setFont(UIFonts.FONT_GENERAL_UI);
		g2.setColor(Color.white);
		g2.drawString(message, stringPosX, stringPosY);
	}

	/**
	 * Returns the height of the toaster body.
	 *
	 * @return The height of the toaster body.
	 */
	public int getHeightOfToast() {
		return heightOfToast;
	}

	/**
	 * Retrieves the current status of whether the toaster body should stop
	 * displaying.
	 *
	 * @return True if the toaster body should stop displaying, false otherwise.
	 */
	public synchronized boolean getStopDisplaying() {
		return stopDisplaying;
	}

	/**
	 * Sets the status of whether the toaster body should stop displaying.
	 *
	 * @param hasStoppedDisplaying True if the toaster body should stop displaying,
	 *                             false otherwise.
	 */
	public synchronized void setStopDisplaying(boolean hasStoppedDisplaying) {
		this.stopDisplaying = hasStoppedDisplaying;
	}

	/**
	 * Sets the y-coordinate position of the toaster body.
	 *
	 * @param yPos The y-coordinate position to set.
	 */
	public void setyPos(int yPos) {
		this.yPos = yPos;
		new Thread(() -> {
			while (getBounds().y > yPos) {
				int i1 = Math.abs((yPos - getBounds().y) / 10);
				i1 = i1 <= 0 ? 1 : i1;
				setBounds((panelToToastOn.getWidth() - toastWidth) / 2, getBounds().y - i1, toastWidth, heightOfToast);
				repaint();
				try {
					Thread.sleep(5);
				} catch (Exception ignored) {
				}
			}
		}).start();
	}

	/**
	 * Retrieves the y-coordinate position of the toaster body.
	 *
	 * @return The y-coordinate position of the toaster body.
	 */
	public int getyPos() {
		return yPos;
	}
}
