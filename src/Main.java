import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static Connection dbConnection() {

        String url = "jdbc:mysql://localhost:3306/Bibliotheque";
        String username = "root";
        String password = "";

        try {
            Connection dbConnection = DriverManager.getConnection(url, username,password);
            return dbConnection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
//        String url = "jdbc:mysql://localhost:3306/Bibliotheque";
//        String username = "root";
//        String password = "";

        Connection dbConnection = dbConnection();
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        new LivreUI(dbConnection);

//        Livre livre = new Livre("abc","abc","abc", new ArrayList<>(Arrays.asList("q","a")));

//        livre.add(dbConnection);
//        System.out.println(livre.getId());
//        ArrayList<Livre> livres = Livre.findAll(dbConnection);
//        System.out.println(livres);

//
//        while(continuer){
//            System.out.println("\nMenu :");
//            System.out.println("1. Ajouter un livre");
//            System.out.println("2. Afficher tous les livres");
//            System.out.println("3. Supprimer un livre");
//            System.out.println("4. Quitter");
//            System.out.print("Choisissez une option : ");
//            int choix = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choix) {
//                case 1 ->{
//                    Livre.ajouterLivre(scanner, dbConnection);
//                }
//                case 2 ->{
//                    Livre.afficherLivres(dbConnection);
//                }
//                case 3->{
//                    Livre.supprimerLivre(dbConnection,scanner);
//
//                }
//                case 4->{
//                    continuer = false;
//
//                }
//                default->{
//                    System.out.println("Option invalide. Veuillez réessayer.");
//                }
//            }
//        }


    }

}