package framework.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RobotUtil {
    private static final String OUTPUT_PATH = "src/main/java/test/resources/";

    public static void takeScreenshot(String screenshotPlace) {
        Logger.info("making a screenshot");
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "png", new File(String.format("%s%s",OUTPUT_PATH, screenshotPlace)));
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}