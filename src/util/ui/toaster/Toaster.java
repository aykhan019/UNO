package util.ui.toaster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a toaster utility for displaying notifications on a specified JPanel.
 * The toaster can display error, success, info, and warning messages.
 */
public class Toaster {
    /**
     * The starting y-coordinate position for displaying the toast.
     */
    private static final int STARTING_Y_POS = 15;

    /**
     * The currently displayed toast.
     */
    private static ToasterBody currentToast = null;

    /**
     * The JPanel on which to display the toast.
     */
    private final JPanel panelToToastOn;

    /**
     * Atomic boolean flag indicating whether a toast is currently being displayed.
     */
    private final AtomicBoolean isShowingToast = new AtomicBoolean(false);

    /**
     * The thread responsible for displaying the toast.
     */
    private Thread displayThread;

    /**
     * Constructs a new Toaster instance with the specified JPanel to display toasts on.
     *
     * @param panelToToastOn The JPanel to display toasts on.
     */
    public Toaster(JPanel panelToToastOn) {
        this.panelToToastOn = panelToToastOn;
    }

    /**
     * Displays an error toast with the provided messages.
     *
     * @param messages The messages to be displayed in the toast.
     */
    public void error(String... messages) {
        showToast(messages, new Color(181, 59, 86));
    }

    /**
     * Displays a success toast with the provided messages.
     *
     * @param messages The messages to be displayed in the toast.
     */
    public void success(String... messages) {
        showToast(messages, new Color(33, 181, 83));
    }

    /**
     * Displays an info toast with the provided messages.
     *
     * @param messages The messages to be displayed in the toast.
     */
    public void info(String... messages) {
        showToast(messages, new Color(13, 116, 181));
    }

    /**
     * Displays a warning toast with the provided messages.
     *
     * @param messages The messages to be displayed in the toast.
     */
    public void warn(String... messages) {
        showToast(messages, new Color(181, 147, 10));
    }

    /**
     * Displays a toast with the provided messages and background color.
     *
     * @param messages The messages to be displayed in the toast.
     * @param bgColor  The background color of the toast.
     */
    private synchronized void showToast(String[] messages, Color bgColor) {
        if (!isShowingToast.get()) {
            isShowingToast.set(true);

            String message = String.join(" ", messages);
            ToasterBody toasterBody = new ToasterBody(panelToToastOn, message, bgColor, STARTING_Y_POS);

            toasterBody.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    removeToast(toasterBody);
                }
            });

            if (currentToast != null) {
                removeToast(currentToast);
                if (displayThread != null && displayThread.isAlive()) {
                    displayThread.interrupt();
                }
            }

            currentToast = toasterBody;

            displayThread = new Thread(() -> {
                panelToToastOn.add(toasterBody, 0);
                panelToToastOn.repaint();

                try {
                    Thread.sleep(2500);
                    removeToast(toasterBody);
                } catch (InterruptedException e) {
                    return;
                }
            });
            displayThread.start();
        }
    }

    /**
     * Removes the specified toast from the panel.
     *
     * @param toasterBody The toaster body representing the toast to be removed.
     */
    private synchronized void removeToast(ToasterBody toasterBody) {
        if (!toasterBody.getStopDisplaying()) {
            toasterBody.setStopDisplaying(true);
            isShowingToast.set(false);

            panelToToastOn.remove(toasterBody);
            panelToToastOn.repaint();
        }
    }
}
