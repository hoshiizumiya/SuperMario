package code;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
    private BufferedImage bgImage = null;//要显示的背景图片

    private int sort = 1;//判断是第几关

    private boolean flag ;//判断场景是否是最后一个场景

    private List<Obstacle> obstacleList = new ArrayList<Obstacle>();//结构

    //用于显示旗杆
    private BufferedImage gan = null;
    //用于显示城堡
    private BufferedImage tower = null;
    //判断马里奥是否到达旗杆位置
    private boolean isReach = false;
    //判断旗子是否落地
    private boolean baseFlag = false;
    //存放敌人的集合成员变量
    private List<Enemy> enemyList = new ArrayList<>();

    //Alt+Insert
    public BackGround() {
    }

    public BackGround(int sort, boolean flag) {
        this.sort = sort;
        this.flag = flag;
        //给对应关卡赋值相应的背景图片
        if (flag) {
            bgImage = StaticValue.bg2;
        } else {
            bgImage = StaticValue.bg;
        }
        //加载障碍物
        if (sort == 1) {
            //加载地皮，所有的障碍物都从obstacleList统一画出
            for (int x = 0; x < 800; x += 29) {
                for (int j = 400; j < 600; j += 29)//从高是400处开始处理
                {
                    if (j == 400) {
                        obstacleList.add(new Obstacle(x, 400, 6, this));
                    } else {
                        obstacleList.add(new Obstacle(x, j, 7, this));
                    }
                }
            }
            //加载砖块
            for (int x = 200; x < 570; x += 30) {
                if (x == 290 || x == 320) {
                    obstacleList.add(new Obstacle(x, 300, 5, this));
                } else {
                    obstacleList.add(new Obstacle(x, 300, 4, this));
                }
                if (x == 350 || x == 380) {
                    obstacleList.add(new Obstacle(x, 200, 5, this));
                }
            }
            obstacleList.add(new Obstacle(100, 300, 5, this));
            obstacleList.add(new Obstacle(130, 300, 5, this));
            //加载管道
            for (int i = 360; i < 600; i += 29) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(620, i, 0, this));
                    obstacleList.add(new Obstacle(645, i, 1, this));
                } else {
                    obstacleList.add(new Obstacle(620, i, 2, this));
                    obstacleList.add(new Obstacle(645, i, 3, this));
                }

            }
            //绘制第一关的蘑菇敌人
            enemyList.add(new Enemy(580,365,true,1,this));
            //绘制第一关的食人花敌人
            enemyList.add(new Enemy(635,420,true,2,328,428,this));
        }
        if (sort == 2)
        {
            //绘制第二关的地面
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 6, this));
            }
            for (int j = 0; j <= 120; j += 30) {
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i * 30, 570 - j, 7, this));
                }
            }
            //绘制第一个水管
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(60, i, 0, this));
                    obstacleList.add(new Obstacle(85, i, 1, this));
                } else {
                    obstacleList.add(new Obstacle(60, i, 2, this));
                    obstacleList.add(new Obstacle(85, i, 3, this));
                }
            }
            //绘制第二个水管
            for (int i = 330; i <= 600; i += 25) {
                if (i == 330) {
                    obstacleList.add(new Obstacle(620, i, 0, this));
                    obstacleList.add(new Obstacle(645, i, 1, this));
                } else {
                    obstacleList.add(new Obstacle(620, i, 2, this));
                    obstacleList.add(new Obstacle(645, i, 3, this));
                }
            }
            //绘制砖块C
            obstacleList.add(new Obstacle(300, 330, 4, this));
            //绘制砖块B,E,G
            for (int i = 270; i <= 330; i += 30) {
                if (i == 270 || i == 330) {
                    obstacleList.add(new Obstacle(i, 360, 4, this));
                } else {
                    obstacleList.add(new Obstacle(i, 360, 5, this));
                }
            }

            //绘制砖块A,D,F,H,I
            for (int i = 240; i <= 360; i += 30) {
                if (i == 240 || i == 360) {
                    obstacleList.add(new Obstacle(i, 390, 4, this));
                } else {
                    obstacleList.add(new Obstacle(i, 390, 5, this));
                }
            }

            //绘制妨碍1砖块
            obstacleList.add(new Obstacle(240, 300, 4, this));

            //绘制空1-4砖块
            for (int i = 360; i <= 540; i += 60) {
                obstacleList.add(new Obstacle(i, 270, 5, this));
            }
            //绘制第二关的第一个食人花敌人
            enemyList.add(new Enemy(75,420,true,2,328,418,this));
            //绘制第二关的第二个食人花敌人
            enemyList.add(new Enemy(635,420,true,2,298,388,this));
            //绘制第二关的第一个蘑菇敌人
            enemyList.add(new Enemy(200,385,true,1,this));
            //绘制第二关的第二个蘑菇敌人
            enemyList.add(new Enemy(500,385,true,1,this));


        }
        if (sort == 3)
        {
            //绘制第三关的地面,上地面type=1,下地面type=2
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 6, this));
            }

            for (int j = 0; j <= 120; j += 30) {
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i * 30, 570 - j, 7, this));
                }
            }

            //绘制第三个背景的A-O砖块
            int temp = 290;
            for (int i = 390; i >= 270; i -= 30) {
                for (int j = temp; j <= 410; j += 30) {
                    obstacleList.add(new Obstacle(j, i, 5, this));
                }
                temp += 30;
            }
            //绘制第三个背景的P-R砖块
            temp = 60;
            for (int i = 390; i >= 360; i -= 30) {
                for (int j = temp; j <= 90; j += 30) {
                    obstacleList.add(new Obstacle(j, i, 5, this));
                }
                temp += 30;
            }
            //绘制旗杆
            gan = StaticValue.gan;
            //绘制城堡
            tower = StaticValue.tower;
            //添加旗子到旗杆上
            obstacleList.add(new Obstacle(515, 220, 8, this));

            //绘制第三关的蘑菇敌人
            enemyList.add(new Enemy(150,385,true,1,this));
        }


    }

    public BufferedImage getBgImage() {
        return bgImage;
    }

    public void setBgImage(BufferedImage bgImage) {
        this.bgImage = bgImage;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public BufferedImage getGan() {
        return gan;
    }

    public void setGan(BufferedImage gan) {
        this.gan = gan;
    }

    public BufferedImage getTower() {
        return tower;
    }

    public void setTower(BufferedImage tower) {
        this.tower = tower;
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public void setObstacleList(List<Obstacle> obstacleList) {
        this.obstacleList = obstacleList;
    }

    public boolean getReach() {
        return isReach;
    }

    public void setReach(boolean isReach) {
        this.isReach = isReach;
    }

    public boolean getBaseFlag() {
        return this.baseFlag;
    }

    public void setBaseFlag(boolean baseFlag)//设置旗子到底情况
    {
        this.baseFlag = baseFlag;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(List<Enemy> enemyList) {
        this.enemyList = enemyList;
    }
}
