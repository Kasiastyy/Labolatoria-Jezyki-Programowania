package org.example;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import org.example.toys.*;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        List<Zabawka> Toys = new ArrayList<>();

        int ilosc = 20; //Ilość losowych dźwięków które chcemy wydać

        for (int i=0; i<ilosc; i++){
            int toyType = rand.nextInt(9);
            switch (toyType) {
                case 0:
                    Toys.add(new Auto());
                    break;
                case 1:
                    Toys.add(new Dinozaur());
                    break;
                case 2:
                    Toys.add(new Foka());
                    break;
                case 3:
                    Toys.add(new Kaczka());
                    break;
                case 4:
                    Toys.add(new Kogut());
                    break;
                case 5:
                    Toys.add(new Kon());
                    break;
                case 6:
                    Toys.add(new Kot());
                    break;
                case 7:
                    Toys.add(new Robot());
                    break;
                case 8:
                    Toys.add(new Samolot());
                    break;
                case 9:
                    Toys.add(new Slon());
                    break;
            }
        }
        for (Zabawka toy : Toys) {
            toy.wydajDzwiek();
        }
    }
}