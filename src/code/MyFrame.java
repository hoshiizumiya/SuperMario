package code;

import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements Runnable, KeyListener {

    // 用于双缓冲的图像对象
    private Image offScreenImage = null;

    // 背景对象
    BackGround backGround = new BackGround();

    private static List<BackGround> allBg = new ArrayList<>();
    // 主角马里奥对象
    Mario mario = new Mario();

    // 线程对象，用于定时更新和重绘界面
    Thread thread = new Thread(this);

    public static void main(String[] args) {
        // 创建并启动游戏窗口
        new MyFrame();//old:MyFrame myFrame = new MyFrame();now is Ok
    }

    public MyFrame() {
        // 设置窗口大小
        this.setSize(800, 600);

        // 将窗口位置设置为屏幕中心
        this.setLocationRelativeTo(null);

        // 设置关闭操作，点击关闭按钮时退出应用程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 显示窗口
        this.setVisible(true);

        // 禁止窗口大小调整
        this.setResizable(false);

        // 设置窗口标题
        this.setTitle("SuperMario");

        // 添加键盘监听器
        this.addKeyListener(this);

        // 初始化静态值
        StaticValue.init();

        // 初始重绘
        repaint();

        // 初始化背景类和马里奥的位置,不涉及背景切换逻辑
        for (int i = 1; i <= 3; i++) {
            backGround = new BackGround(i, i == 3 ? true : false);//背景如何切换？
            allBg.add(backGround);
        }
        backGround = allBg.get(0);
        mario = new Mario(5, 375);
        mario.setBackGround(backGround);
        // all线程启动处
        thread.start();
        try{
            new Music();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (JavaLayerException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 处理按键按下事件
        if (e.getKeyCode() == 39) { // 右箭头键
            mario.rightMove(); // 马里奥向右移动
        }
        if (e.getKeyCode() == 37) { // 左箭头键
            mario.leftMove();
        }
        if (e.getKeyCode() == 38) {//上
            mario.jump();
        }
        //下键？
    }

    //左上右下,分别是37,38,39,40
    @Override
    public void keyReleased(KeyEvent e) {
        // 处理按键释放事件
        if (e.getKeyCode() == 39) { // 右箭头键
            mario.rightStop(); // 马里奥停止向右移动
        }
        if (e.getKeyCode() == 37) {
            mario.leftStop();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 处理键入事件，这里不需要处理
    }

    @Override
    public void run() {
        // 线程的主循环
        while (true) {
            // 重绘界面
            repaint();
            //背景切换
            if (mario.getX() >= 775)//逻辑实现错误，重新的实现
            {
                int currentIndex = allBg.indexOf(backGround);
                if (currentIndex < allBg.size() - 1) {
                    backGround = allBg.get(currentIndex + 1);
                    mario.setBackGround(backGround);
                    mario.setX(10);
                    // 可能需要重置Y坐标到安全位置
                    mario.setY(375); // 或者根据新背景调整
                }
            }
            // 休眠50毫秒，控制刷新频率
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //判断马里奥是否死亡
//            if (mario.isDeath()) {
//                JOptionPane.showMessageDialog(this,"马里奥死亡!!!");
//                System.exit(0);
//            }
            //判断游戏是否结束
            if (mario.isOK()) {
                JOptionPane.showMessageDialog(this,"恭喜你!成功通关了");
                System.exit(0);
            }

        }
    }

    @Override
    public void paint(Graphics g) {
        // 重写paint方法，实现双缓冲绘图

        // 检查offScreenImage是否已初始化
        if (offScreenImage == null) {
            // 第一次调用时创建一个新的Image对象，大小为800x600
            offScreenImage = createImage(800, 600);
        }

        // 获取offScreenImage的Graphics对象
        Graphics graphics = offScreenImage.getGraphics();

        // 清除缓冲区，填充背景色
        graphics.fillRect(0, 0, 800, 600);

        // 绘制背景图像到缓冲区
        graphics.drawImage(StaticValue.bg, 0, 0, this);

        // 绘制马里奥到缓冲区
        graphics.drawImage(mario.getShow(), mario.getX(), mario.getY(), this);
        //N1绘制敌人 添加绘制敌人的代码，放在绘制马里奥的代码之后，两者并列即可
        for (Enemy e : backGround.getEnemyList()) {
            graphics.drawImage(e.getShow(),e.getX(),e.getY(),this);
        }
        // 绘制所有障碍物到缓冲区
        for (Obstacle obstacle : backGround.getObstacleList()) {
            graphics.drawImage(obstacle.getShow(), obstacle.getX(), obstacle.getY(), this);
        }



        //旗杆和城堡
        graphics.drawImage(backGround.getGan(), 500, 220, this);
        graphics.drawImage(backGround.getTower(), 620, 270, this);

        //显示文字
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 30));
        graphics.drawString("Score: " + mario.getScore(), 300, 100);

        // 将缓冲区的内容绘制到屏幕上
        g.drawImage(offScreenImage, 0, 0, this);
    }
}


/*
注释说明
类和成员变量：
MyFrame 类继承自 JFrame，实现了 Runnable 和 KeyListener 接口。
offScreenImage 用于双缓冲绘图，避免闪烁。
backGround 和 mario 分别表示背景和主角马里奥。
thread 用于定时更新和重绘界面。
键盘事件处理：
keyPressed 和 keyReleased 方法分别处理按键按下和释放事件，控制马里奥的移动。
keyTyped 方法在这里不需要处理。
线程的主循环：
run 方法中不断调用 repaint 方法，每50毫秒刷新一次界面。
绘图方法：
paint 方法重写了 JFrame 的 paint 方法，实现双缓冲绘图。
检查并初始化 offScreenImage。
获取 offScreenImage 的 Graphics 对象。
清除缓冲区，绘制背景、马里奥、障碍物和花朵。
将缓冲区的内容绘制到屏幕上。
 */