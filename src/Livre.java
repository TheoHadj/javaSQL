import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Livre {
    private int id;
    private String titre;
    private String description;
    private String datePublication;
    private ArrayList<String> genres;

    Livre(){
        System.out.println("new livre");
    }

    Livre (String titre, String description, String datePublication, ArrayList<String> genres){
        this.setTitre(titre);
        this.setDescription(description);
        this.setDatePublication(datePublication);
        this.setGenres(genres);
        System.out.println("new livre");

    }

    Livre (int id, String titre, String description, String datePublication, ArrayList<String> genres){
        this.setId(id);
        this.setTitre(titre);
        this.setDescription(description);
        this.setDatePublication(datePublication);
        this.setGenres(genres);
        System.out.println("new livre");

    }

    public void add(Connection dbConnection){
        String sql = "INSERT INTO Livre (titre, description, datePublication, genres) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = dbConnection.prepareStatement(sql)){
            statement.setString(1, titre);
            statement.setString(2, description);
            statement.setString(3, datePublication);
            statement.setString(4, String.join(", ", genres));

            statement.executeUpdate();
            System.out.println("Livre ajouté avec succès !");
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void remove(Connection dbConnection, int id){
        if (id == 0) { //petit tricks, java initialise les int a 0 quand ils n'ont pas de valeur
            System.out.println("Erreur ce livre ne vient pas de la bdd!");
            return;
        }

        String sql = "DELETE FROM Livre WHERE id = ?";

        try (PreparedStatement statement = dbConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Livre supprimé avec succès !");
            } else {
                System.out.println("Livre introuvable.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static ArrayList<Livre> findAll(Connection dbConnection){
        ArrayList<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM Livre";
        try (PreparedStatement statement = dbConnection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery();){


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titre = resultSet.getString("titre");
                String description = resultSet.getString("description");
                String datePublication = resultSet.getString("datePublication");
                String genresStr = resultSet.getString("genres");
                ArrayList<String> genres = new ArrayList<>(List.of(genresStr.split(", ")));

                Livre livre = new Livre(id, titre, description, datePublication, genres);
                livres.add(livre);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return livres;

    }



    public static void ajouterLivre(Scanner sc, Connection dbConnection) {
        ArrayList<String> genres = new ArrayList<>();
        System.out.print("Entrez le titre du livre : ");
        String titre = sc.nextLine();
        System.out.print("Entrez la description du livre : ");
        String description = sc.nextLine();
        System.out.print("Entrez la date de publication : ");
        String datePublication = sc.nextLine();
        System.out.print("Entrez les genres un par un : ");
        while(true){
            System.out.print("Entrez un genre ou ! quand vous avez fini : ");
            String genresStr = sc.nextLine();
            if(genresStr.equals("!")){
                break;
            }
            genres.add(genresStr);

        }
        Livre livre = new Livre(titre, description, datePublication, genres);

        livre.add(dbConnection);
    }

    public static void afficherLivres(Connection dbConnection) {
        List<Livre> livres = findAll(dbConnection);
        for (Livre livre : livres) {
            System.out.println("Titre : " + livre.titre);
            System.out.println("Description : " + livre.description);
            System.out.println("Date de publication : " + livre.datePublication);
            System.out.println("Genres : " + livre.genres);
            System.out.println("---------------------------");
        }
    }

    public static void supprimerLivre(Connection dbConnection, Scanner scanner) {
        int id;
        List<Livre> livres = findAll(dbConnection);
        for (Livre livre : livres) {
            System.out.println("------- id : " + livre.id + " -------");
            System.out.println("Titre : " + livre.titre);
            System.out.println("Description : " + livre.description);
            System.out.println("Date de publication : " + livre.datePublication);
            System.out.println("Genres : " + livre.genres);
            System.out.println("---------------------------");
        }
        System.out.println("Entrez l'id du livre à supprimer: ");
        while(true){
            try{
                id = scanner.nextInt();
                break;
            }
            catch (Exception e){
                System.out.println("l'id doit être un nombre ");

            }
        }
        remove(dbConnection,id);


    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(String datePublication) {
        this.datePublication = datePublication;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

}
