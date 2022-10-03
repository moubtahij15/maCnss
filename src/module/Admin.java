package module;

import db.Connexion;

import java.sql.*;
import java.util.Scanner;

import static db.Connexion.con;
import static db.Connexion.startConnection;

public class Admin {
    private String email, password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin(String email, String password) throws SQLException, ClassNotFoundException {
        this.email = email;
        this.password = password;

    }

    boolean login() throws SQLException {
        PreparedStatement stmt = con.prepareStatement("select count(*) from admin where email = ? and password = ?");
        stmt.setString(1, this.email);
        stmt.setString(2, this.password);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            return rs.getInt(1) == 1;
        }
        return false;
    }

//    static void readAllAgent() throws SQLException, ClassNotFoundException {
//        Agent agent = new Agent();
//        agent.readAllAgent();
//    }

    public static void renderAdmin() throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        int tentatif = 0;
        do {
            System.out.println("Bonjour Admin !");
            System.out.println("entrer votre email : ");
            String email = in.nextLine();
            System.out.println("maintenant enter votre mot de pass");
            String pass = in.nextLine();
            Admin admin = new Admin(email, pass);
            if (admin.login()) {
                System.out.println("bien");
                int choixOperation = -1;
                while (choixOperation != 0) {

                    Agent.readAllAgent();
                    //choix d'operation souhaiter
                    System.out.println("Veuillez choisir votre operation");
                    System.out.println("""
                            1-Ajouter un nouveau Agent\s
                            2-modifier un Agent\s
                            3-supprimé un Agent""");

                    choixOperation = in.nextInt();

                    switch (choixOperation) {
                        case 1:
                            if (Agent.insertAgentView()) {
                                System.out.println("Bien Ajouter");
                            }
                            break;
                        case 2:
                            if (Agent.updateAgentView()) {
                                System.out.println("Bien Modifier");
                            }
                            break;
                        case 3:
                            if (Agent.deleteAgentView()) {
                                System.out.println("Bien Supprimé");
                            }
                            break;
                        default:
                            // code block
                    }
                }

                break;
            } else {
                tentatif++;
                System.out.println("not ok");
            }
        } while (tentatif <= 3);


    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        int tentatif = 0;
        do {
            System.out.println("Bonjour Admin !");
            System.out.println("entrer votre email : ");
            String email = in.nextLine();
            System.out.println("maintenant enter votre mot de pass");
            String pass = in.nextLine();
            Admin admin = new Admin(email, pass);
            if (admin.login()) {
                System.out.println("Bien Connecté");
                System.out.println("Veuillez choisir votre session");
                System.out.println("""
                        1-Admin\s
                        2-Agent\s
                        3-Patient""");
                break;
            } else {
                tentatif++;
                System.out.println("not ok");
            }
        } while (tentatif <= 3);


    }

}
