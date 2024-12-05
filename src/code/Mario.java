package code;

import java.awt.image.BufferedImage;

public class Mario implements Runnable {
    // 横坐标，纵坐标 x、y
    private int x = 0, y = 0;
    // 移动、跳跃速度 xSpeed、ySpeed
    private int xSpeed = 0, ySpeed = 0;
    // 上升时间 upTime，下降时间 downTime
    private int upTime = 0, downTime = 0;
    // 运动状态 status
    private String status;
    // 存活状态 isDeath
    private boolean isDeath = false;
    // 图片 show
    private BufferedImage show;
    // 得分 score
    private int score = 0;
    //用于判断马里奥是否走到了城堡的门口
    private boolean isOK;
    // 动画帧索引
    private int index;
    // 背景对象
    BackGround backGround = new BackGround(); // 获取 BackGround 信息
    // 线程对象
    Thread thread = new Thread(this);

    @Override
    public void run() {
        while (true) {
            boolean canRight = true;
            boolean canLeft = true;
            boolean onObstacle = false;

            //判断马里奥是否到达旗杆的x位置，Logical Problem!
            if (backGround.getFlag() && this.x >= 500)//条件：第三关（true)且达到位置
            {
                this.backGround.setReach(true);//发出到达信号给旗子
                //Mario下落至完成的切换
                if (this.backGround.getBaseFlag()) //旗下落完成，Mario开始自动向右平移
                {
                    status = "move--Right";
                    if (x < 690) {
                        x += 5;
                    }else {
                        isOK = true;//Game Over
                    }
                }else //Mario开始随旗下落
                {
                    System.out.println("x: " + x + ", y: " + y + ", status: " + status + ", xSpeed: " + xSpeed + ", ySpeed: " + ySpeed);

                    xSpeed = 0;
                    if (y < 395) {//395
                        this.y += 5;
                        status = "jump--Right";//此处含有jump，莫非是检测到jump后fail()还在追我？
                    }
                    else {//395
                        this.y = 395;
                        status = "stop--Right";
                    }
                }
            }else // 遍历所有障碍物，判断是否与障碍物碰撞
            {
                for (int i = 0; i < backGround.getObstacleList().size(); i++) {
                    Obstacle ob = backGround.getObstacleList().get(i);
                    // 判断是否在障碍物上
                    if (this.y + 25 == ob.getY() && this.x + 25 > ob.getX() && ob.getX() + 30 > this.x) {
                        onObstacle = true;
                    }
                    // 判断是否可以向右移动
                    if (this.x + 25 == ob.getX() && ob.getY() + 30 > this.y && this.y + 25 > ob.getY()) {
                        canRight = false;
                    }
                    // 判断是否可以向左移动
                    if (this.x == ob.getX() + 30 && ob.getY() > this.y - 30 && ob.getY() < this.y + 25) {
                        canLeft = false;
                    }
                    //判断是否跳起来顶到了砖块
                    if ((ob.getY() >= this.y - 30 && ob.getY() <= this.y - 20) && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                        if (ob.getType() == 5)//砖块
                        {
                            backGround.getObstacleList().remove(ob);//顶到特定移除砖块,此时ob为for循环到type=5的值
                            score += 1;
                        }
                        upTime = 0;
                    }
                }
            }



            // 更新动画帧索引
            if (status.contains("move")) {
                index = index == 0 ? 1 : 0;
            }

            // 根据运动状态设置显示的图片
            if (status.equals("move--Right")) {
                show = StaticValue.run_R.get(index);
            }
            if (status.equals("move--Left")) {
                show = StaticValue.run_L.get(index);
            }
            if (status.equals("move--Up")) {
                show = StaticValue.upMove.get(index);
            }
            if (status.equals("stop--Right")) {
                show = StaticValue.stand_R;
            }
            if (status.equals("stop--Left")) {
                show = StaticValue.stand_L;
            }
            if (status.equals("jump--Right")) {
                show = StaticValue.jump_R;
            }
            if (status.equals("jump--Left")) {
                show = StaticValue.jump_L;
            }

            // 左侧空气墙，防止马里奥走出屏幕外
            if (this.x <= 0) {
                this.x = 0;
            }

            // 再次检查是否可以向右移动
            for (Obstacle ob : backGround.getObstacleList()) {
                if (this.x + 25 == ob.getX() && ob.getY() + 30 > this.y && this.y + 25 > ob.getY()) {
                    canRight = false;
                }
            }

            //NEW判断马里奥是否碰到敌人死亡或者踩死蘑菇敌人
            for (int i = 0;i < backGround.getEnemyList().size();i++) {
                Enemy e = backGround.getEnemyList().get(i);
                if (e.getY() == this.y + 20 && (e.getX() - 25 <= this.x &&
                        e.getX() + 35 >= this.x)) {
                    if (e.getType() == 1) {
                        e.death();
                        score += 2;
                        upTime = 3;
                        ySpeed = -10;
                    }else if (e.getType() == 2) {
                        //马里奥死亡
                        death();
                    }
                }
                if ((e.getX() + 35 > this.x && e.getX() - 25 < this.x) && (e.getY()
                        + 35 > this.y && e.getY() - 20 < this.y)) {
                    //马里奥死亡
                    death();
                }
            }



            // 如果可以移动，更新马里奥的位置
            if ((canRight && xSpeed > 0) || (canLeft && xSpeed < 0)) {
                this.x += xSpeed;
                if (this.x <= 0) {
                    this.x = 0;
                }
            }

            // 处理跳跃和下落逻辑
            if (onObstacle && upTime == 0) { // 不上升也不下落
                if (status.contains("Left")) { // 正常状态的左
                    if (xSpeed != 0) {
                        status = "move--Left";
                    } else {
                        status = "stop--Left";
                    }
                } else {
                    if (xSpeed != 0) {
                        status = "move--Right";
                    } else {
                        status = "stop--Right";
                    }
                }
            } else {
                if (upTime != 0) { // 在上升状态
                    upTime--;
                } else { // 若 uptime=0，该下落了
                    fail();
                }
                this.y += ySpeed; // 不管什么状态做下落处理，加上下落速度，ySpeed=0即不上升且不下落
            }

            // 控制线程睡眠时间
            try {
                Thread.sleep(5);//50是标准值，减小以加快
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void death() {
        isDeath = true;
    }


    // 构造方法
    public Mario() {
    }

    // 带参数的构造方法
    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        this.show = StaticValue.stand_R;
        this.status = "stand--Right";
        thread.start(); // 启动线程
    }

    // 向右移动
    public void rightMove() {
        this.xSpeed = 5;
        if (status.contains("jump")) {
            status = "jump--Right";
        } else {
            status = "move--Right";
        }
    }

    // 向左移动
    public void leftMove() {
        this.xSpeed = -5;
        if (status.contains("jump")) {
            status = "jump--Left";
        } else {
            status = "move--Left";
        }
    }

    // 跳跃
    public void jump() {
        if (!status.contains("jump")) {
            if (status.contains("Left")) {
                status = "jump--Left";
            } else {
                status = "jump--Right";
            }
            this.ySpeed = -10;//-10
            upTime = 10;//7 is bad.10 is OK
        }
        //马里奥碰到旗子后停止操作
        if (backGround.getReach()) {
            this.ySpeed = 0;
        }
    }

    // 下落
    public void fail() {
        this.ySpeed = 10;//10
        if (status.contains("Left")) {
            status = "jump--Left";
        } else {
            status = "jump--Right";
        }
    }

    // 停止向右移动
    public void rightStop() {
        this.xSpeed = 0;
        if (status.contains("jump")) {
            status = "jump--Right";
        } else {
            status = "stop--Right";
        }
    }

    // 停止向左移动
    public void leftStop() {
        this.xSpeed = 0;
        if (status.contains("jump")) {
            status = "jump--Left";
        } else {
            status = "stop--Left";
        }
    }

    // 获取和设置方法
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getUpTime() {
        return upTime;
    }

    public void setUpTime(int upTime) {
        this.upTime = upTime;
    }

    public int getDownTime() {
        return downTime;
    }

    public void setDownTime(int downTime) {
        this.downTime = downTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public BufferedImage getShow() {
        return show;
    }

    public BackGround getBackGround() {
        return backGround;
    }

    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}