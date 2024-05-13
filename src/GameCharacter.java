public abstract class GameCharacter {
    protected int positionX;
    protected int positionY;

    public GameCharacter(int initialX, int initialY) {
        positionX = initialX;
        positionY = initialY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public abstract void move(int[][] environment, int difficultyLevel, String direction);
    public boolean checkCollision(GameCharacter other) {
        return this.positionX == other.positionX && this.positionY == other.positionY;
    }
}