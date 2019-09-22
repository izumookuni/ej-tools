package cc.domovoi.tools.utils;

import org.jooq.lambda.tuple.Tuple2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GraphicCodeUtils {

    private static final int width = 180;

    private static final int height = 40;

    private static final int codeAmount = 4;

    private static final int spacing = 30;

    private static final int fontSize = 36;

    private static final int codeY = 32;

    private static final String fontName = "Fixedsys";

    private static final char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final int codeSequenceLength = codeSequence.length;

    public static Tuple2<String, BufferedImage> generateCodeAndPic() {
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Graphics2D gd = buffImg.createGraphics();
        // Graphics2D gd = (Graphics2D) buffImg.getGraphics();
        Graphics gd = buffImg.getGraphics();
        Random random = new Random();
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        Font font = new Font(fontName, Font.BOLD, fontSize);
        gd.setFont(font);

        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        gd.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        StringBuilder randomCode = new StringBuilder();

        for (int i = 0; i < codeAmount; i++) {
            String code = String.valueOf(codeSequence[random.nextInt(codeSequenceLength)]);
            int red = 255, green = 255, blue = 255;
            while (red >= 223 && green >= 223 && blue >= 223) {
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);
            }
//            red = random.nextInt(255);
//            green = random.nextInt(255);
//            blue = random.nextInt(255);

            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * spacing, codeY);

            randomCode.append(code);
        }
        return new Tuple2<>(randomCode.toString(), buffImg);
    }
}
