package com.valdisdot.customersupport.util;

import com.valdisdot.customersupport.Start;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;

//simple util-class for getting of necessary properties
public final class Resources {
    private static final Properties properties;

    static {
        properties = new Properties();
        try (InputStreamReader reader = new InputStreamReader(Resources.class.getResourceAsStream("/app.properties"), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
        }
    }

    private Resources() {
    }

    public static String getSaveButtonText() {
        return properties.getProperty("button.save.label." + Locale.getDefault().getLanguage(), "Save to") + " \"" + getResultFilePath().getFileName() + "\"";
    }

    public static String getFrameTitle() {
        return properties.getProperty("frame.title." + Locale.getDefault().getLanguage(), "Counter");
    }

    public static Image getFrameIco() {
        try (InputStream stream = Start.class.getResourceAsStream("/logo.png")) {
            return ImageIO.read(stream);
        } catch (Exception e) {
            //file logo.png will be included in JAR file, no Exceptions will happen
            return null;
        }
    }

    public static String getInputFieldToolTipText() {
        return properties.getProperty("input.field.tooltip." + Locale.getDefault().getLanguage(), "Ctrl+Enter");
    }

    public static int getBiggestElementWidth() {
        return Integer.parseInt(properties.getProperty("element.width.max"));
    }

    public static Path getResultFilePath() {
        return Path.of(properties.getProperty("path.result.file")).normalize();
    }
}
