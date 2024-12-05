package code;

import java.awt.image.BufferedImage;

public class Obstacle implements Runnable//封装，使用private进行修饰,需要声明方法修改变量。为数据类型，JAVA并
{
    private int x;
    private int y;
    private int type;//障碍物类型
    private BufferedImage show;


    //实现 Runnable 接口:定义当前的场景对象
    private BackGround bg = null;

    public Obstacle(int x, int y, int type, BackGround bg)//全部参数构造
    {
        this.x = x;
        this.y = y;
        this.type = type;
        this.show = StaticValue.obstaclePictures.get(type);//类型
        if (this.type == 8)
        {
            this.bg = bg;
            //定义一个线程对象
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    public Obstacle() //构造方法，空参构造
    {
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (this.bg != null && this.bg.getReach())//获取Mario到达旗的x信号，旗开始自动下降（非Mario的y，为对应bg=8的y）
            {
                if (this.y < 374) {
                    this.y += 5;
                } else //到底完成，返回结果，让Mario开始向右移动
                {
                    this.bg.setBaseFlag(true);
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public int getX()//通过调用getX获得值
    {
        return x;
    }

    public void setX(int x)//带参数
    {
        this.x = x;//this.x指成员变量的x，x是形参
    }

    public int getY()//通过调用getX获得值
    {
        return y;
    }

    public void setY(int y) //带参数
    {
        this.y = y;//this.x指成员变量的x，x是形参
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BufferedImage getShow() {
        return show;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

}
