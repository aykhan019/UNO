package util.ui;

import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class UIUtils {
	public static final int ROUNDNESS = 8;

    public static Graphics2D get2dGraphics(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.addRenderingHints(new HashMap<RenderingHints.Key, Object>() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        }});
        return g2;
    }
    
    public static Font loadCustomFont(String path) {
        try {
            File fontFile = new File(path);
            return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 14);
        } catch (Exception e) {
        	// TODO logger
            e.printStackTrace();
            return null;	
        }
    }
    
    
}