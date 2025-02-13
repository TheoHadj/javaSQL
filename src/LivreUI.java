import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class LivreUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField titleField, descriptionField, dateField, genreField;
    private ArrayList<Livre> livres = new ArrayList<>();

    public LivreUI(Connection connection) {
        livres = Livre.findAll(connection);


        frame = new JFrame("Gestion des Livres");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());




        String[] columnNames = {"ID", "Titre", "Description", "Date", "Genres"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);


        for (Livre livre : livres) {
            tableModel.addRow(livre.toTableRow()); //à revoir c'est moche
        }


        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Titre:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        formPanel.add(descriptionField);

        formPanel.add(new JLabel("Date de publication:"));
        dateField = new JTextField();
        formPanel.add(dateField);

        formPanel.add(new JLabel("Genres (séparés par ,):"));
        genreField = new JTextField();
        formPanel.add(genreField);

        JButton addButton = new JButton("Ajouter Livre");
        JButton deleteButton = new JButton("Supprimer Livre");

        formPanel.add(addButton);
        formPanel.add(deleteButton);

        frame.add(formPanel, BorderLayout.SOUTH);




        addButton.addActionListener(e -> addLivre(connection));
        deleteButton.addActionListener(e -> deleteLivre(connection));



        frame.setVisible(true);
    }

    private void addLivre(Connection connexion) {
        String titre = titleField.getText();
        String description = descriptionField.getText();
        String datePublication = dateField.getText();
        ArrayList<String> genres = new ArrayList<>(List.of(genreField.getText().split(",")));

        Livre livre = new Livre(titre, description, datePublication, genres);
        livres.add(livre);
        livre.add(connexion);


        tableModel.addRow(new Object[]{0, titre, description, datePublication, String.join(", ", genres)}); //à revoir
    }

    private void deleteLivre(Connection connection) {
        int selectedRow = table.getSelectedRow();
        Livre livre = livres.get(selectedRow);
        if (selectedRow != -1) {
            livres.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        }
        Livre.remove(connection, livre.getId());
    }
}
