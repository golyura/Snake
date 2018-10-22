package main;

import enums.SnakeDirection;
import helper.KeyboardObserver;
import snake.Snake;
import snake.SnakeSection;

import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Room {
    private static Room game;
    private int width;
    private int height;
    private static Snake snake;
    private Mouse mouse;

    private char[][] field;

    public static Room getGame() {
        return game;
    }

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        field = new char[height][width];
    }

    public static void main(String[] args) {
        Snake snake = new Snake();
        game = new Room(20, 20, snake);
        snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();

    }

    public void run() {
        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();
        //пока змея жива

        while (snake.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если равно символу 'q' - выйти из игры.
                if (event.getKeyChar() == 'q') return;
                //Если "стрелка влево" - сдвинуть фигурку влево
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                    //Если "стрелка вверх" - сдвинуть фигурку вверх
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                    //Если "стрелка вниз" - сдвинуть фигурку вниз
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }
            snake.move();   //двигаем змею//
            game.print();        //отображаем текущее состояние игры\
            sleep();        //пауза между ходами
        }
        //Выводим сообщение "Game Over"
        System.out.println("Game Over!");
    }

    public void print() {
        fillFieldPoint();
        fillFieldMouse();
        fillFieldSnake();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    private void fillFieldMouse() {
        field[mouse.getY()][mouse.getX()] = 'W';
    }

    private void fillFieldSnake() {
try {
    ArrayList<SnakeSection> snakeSections = snake.getSections();
    for (SnakeSection section : snakeSections) {
        field[section.getY()][section.getX()] = 'x';
    }
    SnakeSection headSnake = snakeSections.get(0);
    field[headSnake.getY()][headSnake.getX()] = 'X';
} catch (ArrayIndexOutOfBoundsException e){
    snake.setAlive(false);
}
    }

    private void fillFieldPoint() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = '.';
            }
        }
    }

    public Mouse createMouse() {
        mouse = new Mouse((int) (Math.random() * width), (int) (Math.random() * height));
        return mouse;
    }

    public void eatMouse() {
        createMouse();
    }

    public void sleep() {
        int sizeSnake = snake.getSections().size();
        try {
            if (sizeSnake == 1) {
                Thread.sleep(700);
            } else if (sizeSnake < 5) {
                Thread.sleep(500);
            } else if (sizeSnake < 10) {
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //region Getters and Setters
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
    //endregion
}
