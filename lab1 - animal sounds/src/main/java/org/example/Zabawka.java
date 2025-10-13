package org.example;

public abstract class Zabawka {
    String nazwa;
    String dzwiek;

    public Zabawka(String nazwa, String dzwiek){
        this.nazwa = nazwa;
        this.dzwiek = dzwiek;
    }

    public void wydajDzwiek(){
        System.out.println(dzwiek);
    }
}
