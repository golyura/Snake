package snake;

import enums.SnakeDirection;
import main.Mouse;
import main.Room;

import java.util.ArrayList;

public class Snake {
    private ArrayList<SnakeSection> sections;


    private boolean isAlive;
    private SnakeDirection direction;

    public int getHeadSnakeX() {
        return sections.get(0).getX();
    }

    public int getHeadSnakeY() {
        return sections.get(0).getY();
    }

    public Snake() {
        sections = new ArrayList<>();
        sections.add(new SnakeSection(10, 10));
        isAlive = true;
    }

    public void move() {
        SnakeSection newSnakeSection = null;

        switch (direction) {
            case UP:
                newSnakeSection = new SnakeSection(getHeadSnakeX(), getHeadSnakeY() - 1);
                break;
            case DOWN:
                newSnakeSection = new SnakeSection(getHeadSnakeX(), getHeadSnakeY() + 1);
                break;
            case LEFT:
                newSnakeSection = new SnakeSection(getHeadSnakeX() - 1, getHeadSnakeY());
                break;
            case RIGHT:
                newSnakeSection = new SnakeSection(getHeadSnakeX() + 1, getHeadSnakeY());
                break;
        }
        checkBoby(newSnakeSection);
        checkBorders(newSnakeSection);
        sections.add(0, newSnakeSection);
        
        boolean isEat = checkEatMouse(newSnakeSection);
        if (!isEat) {
            sections.remove(sections.size() - 1);

        }

    }

    private boolean checkEatMouse(SnakeSection newSnakeSection) {
        Mouse mouse = Room.getGame().getMouse();
        if (mouse.getY() == newSnakeSection.getY() && mouse.getX() == newSnakeSection.getX()) {
            Room.getGame().eatMouse();
            return true;
        }
        return false;
    }


    private void checkBorders(SnakeSection newSnakeSection) {
        if (newSnakeSection.getX() > Room.getGame().getWidth() || newSnakeSection.getX() < 0
                || newSnakeSection.getY() > Room.getGame().getHeight() || newSnakeSection.getY() < 0) {
            isAlive = false;
        }
    }

    private void checkBoby(SnakeSection newSnakeSection) {
        if (sections.contains(newSnakeSection)) {
            isAlive = false;
        }
    }

    public ArrayList<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
