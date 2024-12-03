package code;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//静态类，从磁盘读取图片到内存
public class StaticValue {
    public static String path = System.getProperty("user.dir") + "\\src\\images\\";
    public static List<BufferedImage> obstaclePictures = new ArrayList<BufferedImage>();//
    public static List<BufferedImage> run_R = new ArrayList();
    public static List<BufferedImage> run_L = new ArrayList();
    public static List<BufferedImage> upMove = new ArrayList();
    //蘑菇敌人
    public static List<BufferedImage> mogu = new ArrayList<>();
    //食人花敌人
    public static List<BufferedImage> flower = new ArrayList<>();
    public static BufferedImage bg,bg2,stand_R,stand_L,jump_R,jump_L,gan,tower;//？
    public static void init()
    {
        try {
            bg = ImageIO.read(new File(path + "bg.png"));
            bg2 = ImageIO.read(new File(path + "bg2.png"));
            stand_R = ImageIO.read(new File(path + "s_mario_stand_R.png"));
            stand_L = ImageIO.read(new File(path + "s_mario_stand_L.png"));
            jump_R = ImageIO.read(new File(path + "s_mario_jump1_R.png"));
            jump_L = ImageIO.read(new File(path + "s_mario_jump1_L.png"));
            gan = ImageIO.read(new File(path+"gan.png"));
            tower = ImageIO.read(new File(path+"tower.png"));
            obstaclePictures.add(ImageIO.read(new File(path + "pipe1.png")));//0
            obstaclePictures.add(ImageIO.read(new File(path + "pipe2.png")));//1
            obstaclePictures.add(ImageIO.read(new File(path + "pipe3.png")));//2
            obstaclePictures.add(ImageIO.read(new File(path + "pipe4.png")));//3
            obstaclePictures.add(ImageIO.read(new File(path + "brick.png")));//4
            obstaclePictures.add(ImageIO.read(new File(path + "brick2.png")));//5
            obstaclePictures.add(ImageIO.read(new File(path + "soil_up.png")));//6
            obstaclePictures.add(ImageIO.read(new File(path + "soil_base.png")));//7
            obstaclePictures.add(ImageIO.read(new File(path+"flag.png")));//8

            run_L.add(ImageIO.read(new File(path+"s_mario_run1_L.png")));
            run_L.add(ImageIO.read(new File(path+"s_mario_run2_L.png")));
            run_R.add(ImageIO.read(new File(path+"s_mario_run1_R.png")));
            run_R.add(ImageIO.read(new File(path+"s_mario_run2_R.png")));
            for (int i = 1;i <= 3;i++) //加载蘑菇敌人
            {
                mogu.add(ImageIO.read(new File(path + "fungus"+i+".png")));
            }
            for (int i = 1;i <= 2;i++) { //加载食人花敌人
                flower.add(ImageIO.read(new File(path + "flower1." + i + ".png")));
            }

            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

