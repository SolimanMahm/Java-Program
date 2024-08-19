package com.mycompany.petadoptiongame;

import java.util.Scanner;

/**
 *
 * @author Soliman Mahmoud
 */
public class PetAdoptionGame {

    public static void main(String[] args) {

        Game game = new Game();
        int op = 0;
        boolean repeat = true;

        String menu = "1. Adopt Pet\n"
                + "2. Feed Pet\n"
                + "3. Play with Pet\n"
                + "4. Check Status\n"
                + "5. End Game";

        while (repeat) {
            System.out.println(menu);
            System.out.print("Choose an option: ");
            op = new Scanner(System.in).nextInt();
            if ((op == 2 || op == 3) && !Game.confirmation) {
                System.out.println("No pet has been adopted.\n");
                continue;
            }
            switch (op) {
                case 1:
                    System.out.print("Enter the name of the pet: ");
                    game = new Game(new Scanner(System.in).nextLine());
                    break;
                case 2:
                    if (game.feedPet()) {
                        repeat = false;
                    }
                    break;
                case 3:
                    if (game.playPet()) {
                        repeat = false;
                    }
                    break;
                case 4:
                    game.checkSate();
                    break;
                case 5:
                    game.endGame();
                    repeat = false;
                    break;
                default:
                    System.out.println("Invalid Option!\n");
            }
        }

    }

}

class Game {

    private String name;
    private int hunger, happiness;
    static boolean confirmation = false;

    public Game() {
    }

    public Game(String name) {
        this.name = name;
        this.hunger = 50;
        this.happiness = 50;
        confirmation = true;
        System.out.println("The pet has been adopted and is ready to be"
                + "cared for.\n");
    }

    public boolean feedPet() {
        if (hunger < 90) {
            hunger += 10;
            happiness -= 10;
            System.out.println("Pet has been fed.\n");
        } else {
            System.out.println("The pet is full, and no further increase is allowed.\n");
        }
        return troubleshooting();
    }

    public boolean playPet() {
        if (happiness < 90) {
            happiness += 10;
            hunger -= 20;
            System.out.println("Pet played and is happier now.\n");
        } else {
            System.out.println("The pet is too happy, and no further increase is allowed.\n");
        }
        return troubleshooting();
    }

    public void checkSate() {
        System.out.println(name + "`s hunger: " + hunger);
        System.out.println(name + "`s happiness: " + happiness);
    }

    public void endGame() {
        System.out.println("The Game ends");
        checkSate();
    }

    private boolean troubleshooting() {
        if (happiness <= 0 || hunger <= 0) {
            System.out.println("Game Over. Your pet`s condition has reched a critical level.");
            checkSate();
            return true;
        }
        return false;
    }

}
