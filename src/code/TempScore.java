package code;

public class TempScore {

    private boolean i1, i2, i3, i4, i5;
    private BackGround backGround;
    private Mario mario;

    // 提供空参构造函数
    public TempScore() {
        this.i1 = false;
        this.i2 = false;
        this.i3 = false;
        this.i4 = false;
        this.i5 = false;
    }

    // 提供更新引用的方法
    public void updateReferences(BackGround newBackGround, Mario newMario) {
        this.backGround = newBackGround;
        this.mario = newMario;
    }

    public void tempScore() {
        checkAndAddObstacle(260, 395, 325, 4, i1, (flag) -> { i1 = flag; });
        checkAndAddObstacle(290, 365, 295, 4, i2, (flag) -> { i2 = flag; });
        checkAndAddObstacle(320, 335, 265, 4, i3, (flag) -> { i3 = flag; });
        checkAndAddObstacle(350, 305, 235, 4, i4, (flag) -> { i4 = flag; });
        checkAndAddObstacle(380, 275, 205, 4, i5, (flag) -> { i5 = flag; });
    }

    private void checkAndAddObstacle(int xCheck, int yCheck, int yNew, int param, boolean flag, java.util.function.Consumer<Boolean> updateFlag) {
        if (backGround != null && mario != null && backGround.getFlag() && mario.getX() == xCheck && mario.getY() == yCheck && !flag) {
            backGround.addObstacleList(new Obstacle(xCheck, yNew, param, backGround));
            updateFlag.accept(true);
        }
    }
}