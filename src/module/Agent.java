package module;

import db.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static db.Connexion.con;
import static db.Connexion.startConnection;

public class Agent {
    private String email, pass, prenom, nom;
    private int id;

    public Agent(String email, String pass, String prenom, String nom, int id) {
        this.email = email;
        this.pass = pass;
        this.prenom = prenom;
        this.nom = nom;
        this.id = id;
    }

    private ArrayList<Agent> allAgents;

    public Agent() throws SQLException, ClassNotFoundException {
    }

    public Agent(String email, String pass, String prenom, String nom) throws SQLException, ClassNotFoundException {
        this.email = email;
        this.pass = pass;
        this.prenom = prenom;
        this.nom = nom;
        this.allAgents = new ArrayList<>();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Agent readAgent(int idAgent) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = con.prepareStatement("select * from agent  where id =?");
        stmt.setInt(1, idAgent);
        ResultSet rs = stmt.executeQuery();

        Agent agent = null;
        while (rs.next()) {
            agent = new Agent(rs.getString("email"), rs.getString("password"), rs.getString("prenom"), rs.getString("nom"), rs.getInt("id"));

        }
        return agent;

    }

    public static boolean deleteAgent(int idAgent) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE\n" +
                "FROM macnss.agent\n" +
                "WHERE id = ?;\n");
        stmt.setInt(1, idAgent);
        return stmt.executeUpdate() == 1;
    }
    public static boolean deleteAgentView() throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);

        System.out.println("enter le numero du agent pour supprimer :");
        int idAgent = in.nextInt();

        return Agent.deleteAgent(idAgent);
    }


        public boolean updateAgent() throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE macnss.agent t\n" +
                "SET t.prenom   = ?,\n" +
                "    t.nom      = ?,\n" +
                "    t.email    = ?,\n" +
                "    t.password = ?\n" +
                "WHERE t.id = ?;");
        stmt.setString(1, this.prenom);
        stmt.setString(2, this.nom);
        stmt.setString(3, this.email);
        stmt.setString(4, this.pass);
        stmt.setInt(5, this.id);
        return stmt.executeUpdate() == 1;
    }

    public static boolean updateAgentView() throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);

        System.out.println("enter le numero du agent :");
        int idAgent = in.nextInt();
        Agent agent = new Agent();
        agent = agent.readAgent(idAgent);
        System.out.println("Vous voulez modifier :");
        System.out.println("""
                1-nom\s
                2-prenom\s
                2-email\s
                3-mot de pass""");
        int choix = in.nextInt();
        switch (choix) {
            case 1:
                System.out.println("enter le nouveau nom du agent :");
                String nom = in.next();
                agent.setNom(nom);
                if (agent.updateAgent()) {
                    return true;
                }

                break;
            case 2:
                System.out.println("enter le nouveau prenom du agent :");
                String prenom = in.next();
                agent.setPrenom(prenom);
                if (agent.updateAgent()) {
                    return true;
                }
                break;
            case 3:
                System.out.println("enter le nouveau email du agent :");
                String email = in.next();
                agent.setEmail(email);
                if (agent.updateAgent()) {
                    return true;
                }
                break;
            case 4:
                System.out.println("enter le nouveau mot de pass du agent :");
                String pass = in.next();
                agent.setPass(pass);
                if (agent.updateAgent()) {
                    return true;
                }
                break;
            default:
        }

        return false;

    }

    public static void readAllAgent() throws SQLException {
        PreparedStatement stmt = con.prepareStatement("select * from agent ");
        ResultSet rs = stmt.executeQuery();
        System.out.println("Voila tous les agents : ");
        System.out.println(" \t id \t nom \t prenom \t email");
        System.out.println("----------------------------------------------");
        while (rs.next()) {

            System.out.println(" \t " + rs.getInt("id") + " \t " + rs.getString("prenom") + " \t " + rs.getString("nom") + " \t " + rs.getString("email"));
        }

    }

    public boolean insertAgent() throws SQLException {

        PreparedStatement stmt = con.prepareStatement("INSERT INTO macnss.agent (prenom, nom, email, password)  VALUES (?, ?, ?, ?)");
        stmt.setString(1, this.prenom);
        stmt.setString(2, this.nom);
        stmt.setString(3, this.email);
        stmt.setString(4, this.pass);
        return stmt.executeUpdate() == 1;


    }

    public static boolean insertAgentView() throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("enter le nom du agent :");
        String nom = in.nextLine();
        System.out.println("enter le prenom du agent :");
        String prenom = in.nextLine();
        System.out.println("enter l'email du agent :");
        String email = in.nextLine();
        System.out.println("enter le mot de pass du agent :");
        String pass = in.nextLine();
        Agent newAgent = new Agent(email, pass, prenom, nom);
        return newAgent.insertAgent();

    }

}
