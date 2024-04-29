package util.ui.toaster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class Toaster {
    private static final int STARTING_Y_POS = 15;
    private static ToasterBody currentToast = null;
    private final JPanel panelToToastOn;
    private final AtomicBoolean isShowingToast = new AtomicBoolean(false);
    private Thread displayThread;

    public Toaster(JPanel panelToToastOn) {
        this.panelToToastOn = panelToToastOn;
    }

    public void error(String... messages) {
        showToast(messages, new Color(181, 59, 86));
    }

    public void success(String... messages) {
        showToast(messages, new Color(33, 181, 83));
    }

    public void info(String... messages) {
        showToast(messages, new Color(13, 116, 181));
    }

    public void warn(String... messages) {
        showToast(messages, new Color(181, 147, 10));
    }

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
                // Interrupt the thread responsible for displaying the old toast
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
                    // Thread interrupted, just exit
                    return;
                }
            });
            displayThread.start();
        }
    }

    private synchronized void removeToast(ToasterBody toasterBody) {
        if (!toasterBody.getStopDisplaying()) {
            toasterBody.setStopDisplaying(true);
            isShowingToast.set(false);

            panelToToastOn.remove(toasterBody);
            panelToToastOn.repaint();
        }
    }
}
