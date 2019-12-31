package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    private Integer length;
    private Integer width;
    private String[][] shipBoard;
    private String[][] playerBoard;
    private List<Integer> ships;
    private int shipsArea;
    private int hit;
    private int miss;
    static Scanner scan;


    public void initBoard() {

        scan = new Scanner(System.in);
        if (length != null && width != null) {
            shipBoard = new String[length][width];
            playerBoard = new String[length][width];
            ships = new ArrayList<Integer>();
            resetGame();
        }
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;//todo zabezpieczyc
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;//todo zabezpieczyc
    }

    public void showBoard() {
        System.out.print("+");
        for (int j = 0; j < width; j++) {
            System.out.print("-");
        }
        System.out.println("+");

        for (int i = 0; i < length; i++) {
            System.out.print("|");
            for (int j = 0; j < width; j++) {
                System.out.print(playerBoard[i][j]);
            }
            System.out.println("|");
        }

        System.out.print("+");
        for (int j = 0; j < width; j++) {
            System.out.print("-");
        }
        System.out.println("+");
    }

    public void addShip(Integer shipSize) {
        shipsArea += shipSize;
        ships.add(shipSize);
    }

    public void addRandomShips(Integer quantity, Integer maxSize) {
        for (int i = 0; i < quantity; i++) {
            ships.add((int) (Math.random() * maxSize-1)+1);
        }
    }

    public void randomDistributeShipsOnBoard() {
        cleanBoards();
        for (Integer ship : ships) {
            int fail = 0;
            while (fail < 5) {
                //randomNum = minimum + (int)(Math.random() * maximum);
                //random pozioma wsp
                Integer randomWidthCoordinate = (int) (Math.random() * (width - ship));
                //random pionowa wsp
                Integer randomLengthCoordinate = (int) (Math.random() * (length - ship));
                //random poziom/pion //0-hor/1-vert
                boolean isHorizontal = (((int) (Math.random() * 2)) == 1);
                boolean spaceForShip = true;
                for (int i = 0; i < ship; i++) {
                    if (isHorizontal) {
                        //poziom
                        if (shipBoard[randomLengthCoordinate][randomWidthCoordinate + i].equals("*")) {
                            spaceForShip = false;
                            break;
                        }
                    } else {
                        //pion
                        if (shipBoard[randomLengthCoordinate + i][randomWidthCoordinate].equals("*")) {
                            spaceForShip = false;
                            break;
                        }
                    }
                }
                //tak wstaw
                if (spaceForShip) {
                    for (int i = 0; i < ship; i++) {
                        if (isHorizontal) {
                            //poziom
                            shipBoard[randomLengthCoordinate][randomWidthCoordinate + i] = "*";
                        } else {
                            //pion
                            shipBoard[randomLengthCoordinate + i][randomWidthCoordinate] = "*";
                        }
                    }
                    break;
                }
                //nie fail++
                fail++;
                //if fail >=5 then break
                if (fail >= 5) {
                    System.out.println("Failed to set Ship length: " + ship);
                    break;
                }
            }
        }

    }

    public void resetGame() {
        cleanBoards();
        shipsArea = 0;
        hit = 0;
        miss = 0;
    }

    public void cleanBoards() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                playerBoard[i][j] = ".";
                shipBoard[i][j] = ".";
            }
        }
    }

    public void shoot() {
        Integer lengthCoordinate = getLengthCoordinate();
        Integer widthCoordinate = getWidthCoordinate();

        //jak . daj o
        //do statów ze pudlo
        //jak * daj x
        //do statów ze trafiony
        if (shipBoard[lengthCoordinate][widthCoordinate].equals("*")) {
            playerBoard[lengthCoordinate][widthCoordinate] = "x";
            hit++;
        } else {
            playerBoard[lengthCoordinate][widthCoordinate] = "o";
            miss++;
        }

        //zatopiony? moze kiedyś
    }

    public Integer getWidthCoordinate() {
        return getCoordinate("width");
    }

    public Integer getLengthCoordinate() {
        return getCoordinate("length");
    }

    public Integer getCoordinate(String name) {
        String stringValue;
        Integer integerValue;
        int sample = 0;
        while (sample < 5) {
            System.out.println("Write " + name + " coordinate [123..]:");
            stringValue = scan.nextLine();
            integerValue = Integer.parseInt(stringValue);
            if (integerValue > 0 && integerValue <= 20) {
                return integerValue - 1;
            }
            sample++;
        }
        return null;//todo zabezpieczyc
    }

    public void showStats() {
        System.out.println("====================");
        System.out.println("========stats=======");
        System.out.println("Ships to hit: "+shipsArea);
        System.out.println("hits: "+hit);
        System.out.println("miss: "+miss);
        System.out.println("====================");
    }
}

//statkowe
//puste .
//statek *

//puste .
//trafione x
//pudlo o

//  abc
// +---+
//a|   |
//b|   |
//c|   |
// +---+