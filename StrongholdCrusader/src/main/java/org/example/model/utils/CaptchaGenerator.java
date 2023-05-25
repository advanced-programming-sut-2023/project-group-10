package org.example.model.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Scanner;

public class CaptchaGenerator {

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String key = randomNumberGenerator();
            captchaGenerator(key);
            String answer = scanner.nextLine();
            if (answer.equals(key))
                break;
            if (answer.equals("change captcha")) continue;
            System.out.println("Wrong! Complete captcha again");
        }
    }

    public static void captchaGenerator(String captcha) {
        int width, height = 16;

        if (captcha.length() == 4) width = 57;
        else if (captcha.length() == 5) width = 72;
        else if (captcha.length() == 6) width = 87;
        else if (captcha.length() == 7) width = 102;
        else width = 117;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.TYPE1_FONT, 20));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString(captcha, 3, 15);

        for (int y = 0; y < height; y++) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int x = 0; x < width; x++) {
                Random random = new Random();
                int number = random.nextInt(11);
                if (number == 10) {
                    stringBuilder.append(".");
                    continue;
                }
                stringBuilder.append(image.getRGB(x, y) == -16777216 ? " " : "#");
            }

            if (stringBuilder.toString().trim().isEmpty())
                continue;

            System.out.println(stringBuilder);
        }
    }

    public static String randomNumberGenerator() {
        Random random = new Random();
        int length = random.nextInt(5) + 4;
        String producer = "0123456789";
        String captcha = "";

        for (int i = 0; i < length; i++) {
            captcha += producer.charAt(random.nextInt(10));
        }
        return captcha;
    }
}
