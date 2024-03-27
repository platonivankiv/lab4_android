package by.grsu.guess_the_number;

import java.util.Random;

public class GuessNum {
    static public int rndCompNum(int digit){
        int min = 0;
        int max = 0;
        if (digit == 2) {
            min = 10;
            max = 99;
        }
        else if (digit == 3) {
            min = 100;
            max = 999;
        }
        else {
            min = 1000;
            max = 9999;
        }
        int diff = max-min;
        Random random = new Random();
        return random.nextInt(diff + 1) + min;
    }
}
