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

    /**
     * 生成一个map集合
     * code为生成的验证码
     * codePic为生成的验证码BufferedImage对象
     * @return 验证码
     */
    public static Tuple2<String, BufferedImage> generateCodeAndPic() {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Graphics2D gd = buffImg.createGraphics();
        // Graphics2D gd = (Graphics2D) buffImg.getGraphics();
        Graphics gd = buffImg.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font(fontName, Font.BOLD, fontSize);
        // 设置字体。
        gd.setFont(font);

        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuilder randomCode = new StringBuilder();

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeAmount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(codeSequenceLength)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            int red = 255, green = 255, blue = 255;
            while (red >= 223 && green >= 223 && blue >= 223) {
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);
            }
//            red = random.nextInt(255);
//            green = random.nextInt(255);
//            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * spacing, codeY);

            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        return new Tuple2<>(randomCode.toString(), buffImg);
    }
}
