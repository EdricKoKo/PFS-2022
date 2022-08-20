import java.util.Scanner;
import java.util.Random;

public class Assignment1 {
    static final String[][] ocean = new String[10][10];
    static final int[][] playerShipLocations = new int[5][2];
    static final int[][] computerShipLocations = new int[5][2];

    public static void main(String[] args) {

        System.out.println("****** Welcome to Battle Ship Game ****** ");
        System.out.println("Right now, the sea is empty");
        drawOcean();
        deployUserShips();
        deployComputerShips();
        drawOcean();

        int iWinner = 0;
        //Start moving ships
        while (iWinner==0) {
            PlayerMove();
            ComputerMove();
            drawOcean();
            iWinner = getWinner();
            if (iWinner == 1) {
                System.out.println("Game Over. You Win!");
            } else if (iWinner == 2) {
                System.out.println("Game Over. Computer Win!");
            }
        }
    }

    public static void drawOcean() {
        //Map Header
        System.out.print("  ");
        for (int i = 0; i < ocean.length; i++) {
            System.out.print(i);
        }
        System.out.println();
        for (int row = 0; row < ocean.length; row++) {
            System.out.print(row + "|");
            for (int col = 0; col < ocean[row].length; col++) {
                if (ocean[row][col] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(ocean[row][col]);
                }
            }
            System.out.println("|" + row);
        }
        //Map Footer
        System.out.print("  ");
        for (int i = 0; i < ocean.length; i++) {
            System.out.print(i);
        }
        printShipStatus();
    }

    public static void deployUserShips() {

        for (int i = 1; i < 6; i++) {

            System.out.print("Enter x coordinate for your ship " + i + ".");
            int x = getValidKeyInCoordinate();
            System.out.print("Enter y coordinate for your ship " + i + ".");
            int y = getValidKeyInCoordinate();

            setShip(x, y, "@");
            playerShipLocations[i - 1][0] = x;
            playerShipLocations[i - 1][1] = y;
        }
    }

    public static void deployComputerShips() {
        int iIndex = 1;
        int[] location;
        int x, y;
        do {
            location = getRandomLocation();
            while (!isEmptyLocation(location)) //If location already occupied
            {
                //Get Next random Location
                location = getRandomLocation();
            }

            x = location[0];
            y = location[1];

            //*** Comment not to show locations of computer ships ***/
            //setShip(x, y, "0");

            computerShipLocations[iIndex - 1][0] = x;
            computerShipLocations[iIndex - 1][1] = y;

            System.out.println("Computer deployed for ship " + iIndex);

            iIndex++;
        } while (iIndex < 6);
    }
    public static int getValidKeyInCoordinate()
    {
        Scanner sc = new Scanner(System.in);
        int x;
        boolean bValid = false;

        do {
            while (!sc.hasNextInt()) {
                System.out.println("That's not number!");
                sc.next();
            }
            x = sc.nextInt();
            if(x > -1 && x < 10) {bValid=true;}
            else {
                bValid= false;
                System.out.println("That's not valid coordinate. Coordinate must between 0 and  9!");
            }
        } while (!bValid);
        return x;
    }

    public static void PlayerMove() {

        System.out.println("Your turn to move...");
        System.out.print("Enter x coordinate : ");
        int x = getValidKeyInCoordinate();

        System.out.print("Enter y coordinate : ");
        int y = getValidKeyInCoordinate();

        if(isShipFound(computerShipLocations,x,y))
        {
            System.out.println("BOOM! you sunk the ship! ");
            for (int l = 0; l < computerShipLocations.length; l++) {
                if (computerShipLocations[l][0] == x && computerShipLocations[l][1] == y) {
                    computerShipLocations[l][0] =-1;
                    computerShipLocations[l][1] =-1;
                }
            }
            setShip(x, y, "*"); //Place player sunk computer's ship
        }
        else
        {
            if(isShipFound(playerShipLocations,x,y)) {
                System.out.println("Oh! you sunk your ship! ");
                for (int l = 0; l < playerShipLocations.length; l++) {
                    if (playerShipLocations[l][0] == x && playerShipLocations[l][1] == y) {
                        playerShipLocations[l][0] =-1;
                        playerShipLocations[l][1] =-1;
                    }
                }
                setShip(x, y, "^"); //Place player sunk own ship
            }
            else {
                System.out.println("You missed.");
                setShip(x, y, "x");//Place player missed
            }

        }
    }

    public static void ComputerMove() {

        System.out.println("Computer move.. ");

        int[] location;
        location = getRandomLocation();

        int x = location[0];
        int y = location[1];

        if(isShipFound(playerShipLocations,x,y))
        {
            System.out.println("BOOM! Computer sunk your  ship! ");
            for (int l = 0; l < playerShipLocations.length; l++) {
                if (playerShipLocations[l][0] == x && playerShipLocations[l][1] == y) {
                    playerShipLocations[l][0] =-1;
                    playerShipLocations[l][1] =-1;
                }
            }
            setShip(x, y, "*"); //Place where computer sunk player's ship
        }
        else
        {
            if(isShipFound(computerShipLocations,x,y)) {
                System.out.println("Oh! Computer sunk own ship! ");
                for (int l = 0; l < computerShipLocations.length; l++) {
                    if (computerShipLocations[l][0] == x && computerShipLocations[l][1] == y) {
                        computerShipLocations[l][0] =-1;
                        computerShipLocations[l][1] =-1;
                    }
                }
                setShip(x, y, "v"); //Place where computer sunk own ship
            }
            else {
                System.out.println("Computer missed.");
                setShip(x, y, "x"); //Place where computer missed
            }

        }
    }
    public static boolean isShipFound(int[][] arrShips,int x, int y) {
        for (int i = 0; i < arrShips.length; i++) {
            if (arrShips[i][0] ==x &&  arrShips[i][1] == y ) {
                return true;
            }
        }
        return false;
    }

    public static void setShip(int x, int y, String s) {
        ocean[x][y] = s;
    }

    public static int[] getRandomLocation() {
        int[] location = new int[2];
        // create random object
        Random ran = new Random();
        int x = ran.nextInt(10); // between 0 and 9
        int y = ran.nextInt(10); // between 0 and 9
        location[0] = x;
        location[1] = y;
        return location;
    }

    public static boolean isEmptyLocation(int[] lxy) {
        for (int i = 0; i < playerShipLocations.length; i++) {
            if (playerShipLocations[i][0] == lxy[0] && playerShipLocations[i][0] == lxy[1]) {
                return false;
            }
        }
        return true;
    }

    public static void printShipStatus() {
        int iPlayerShips = getNumberOfShips(playerShipLocations);
        int iComputerShips = getNumberOfShips(computerShipLocations);
        System.out.println("\n Your ships : " + iPlayerShips + " |  Computer ships: " + iComputerShips);
        System.out.println("-----------------------------------------------------");
    }

    public static int getNumberOfShips(int[][] arrShips) {
        int iCount = arrShips.length;
        for (int i = 0; i < arrShips.length; i++) {
            if (arrShips[i][0] < 0) {
                iCount--;
            }
        }
        return iCount;
    }
    public static int getWinner() {
        // return 0 mean not finish
        // return 1 mean Player is Winner
        // return 2 mean Computer is Winner
        int iPlayerShips = getNumberOfShips(playerShipLocations);
        int iComputerShips = getNumberOfShips(computerShipLocations);
        if (iPlayerShips == 0 || iComputerShips == 0) {
            if (iPlayerShips > iComputerShips) return 1;
            else return 2;
        }
        return 0;
    }


}
