package module;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static db.Connexion.con;

public class Patient {
    private String email, password, matricule;
    private int id;


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

    public Patient(String matricule, String password) throws SQLException, ClassNotFoundException {
        this.matricule = matricule;
        this.password = password;

    }

    public void getAllDossierMedicale() throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM dossiermedical d join patient p  on p.id=d.id_patient where p.id = ?");
        stmt.setInt(1, this.id);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Voila  Votre dossier medicale  : ");
        System.out.println(" \t Id \t Matricule \t Mentant Rembourser \t Etat");
        System.out.println("----------------------------------------------");

        while (rs.next()) {
            System.out.println(" \t " + rs.getInt("id") + " \t " + rs.getString("matricule") + " \t " + rs.getDouble("mentantRemboussable") + "DH \t " + " \t\t " + rs.getString("etat"));

        }

    }

    boolean login() throws SQLException {
        PreparedStatement stmt = con.prepareStatement("select * from patient where matricule = ? and password = ?");
        stmt.setString(1, this.matricule);
        stmt.setString(2, this.password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            this.id = rs.getInt("id");
            return true;
        }
        return false;

    }


    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void renderPatient() throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        int tentative = 0;

        System.out.println("Bonjour Patient !");

        do {
            System.out.println("entrer votre Matricule : ");
            String matricule = in.nextLine();
            System.out.println("maintenant enter votre mot de pass");
            String pass = in.nextLine();
            Patient patient = new Patient(matricule, pass);
            if (patient.login()) {
                patient.getAllDossierMedicale();
            } else {

                System.out.println("not");
                tentative++;
            }
        } while (tentative < 3);

    }


}

