package main;

import module.Admin;

import java.sql.SQLException;
import java.util.Scanner;

import static db.Connexion.startConnection;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("ma Cnss Bonjour ! ");
        System.out.println("Veuillez choisir votre session");
        System.out.println("""
                1-Admin\s
                2-Agent\s
                3-Patient""");

        Scanner in = new Scanner(System.in);
        startConnection();
        int choixSession = -1;

        while (choixSession != 0) {
            choixSession = in.nextInt();
            switch (choixSession) {
                case 1:
                    Admin.renderAdmin();
                    break;
                case 2:
                    System.out.println("case2");
                    break;
                default:
                    // code block
            }
        }


    }
}
