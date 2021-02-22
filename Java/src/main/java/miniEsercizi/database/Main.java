package miniEsercizi.database;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String indirizzoDatabase = "jdbc:postgresql://localhost/test";
        String username = "giuliopime";
        String password = "password";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(indirizzoDatabase, username, password);

            /*Statement comando = connection.createStatement();
            String query = """
                    CREATE TABLE IF NOT EXISTS tabella_prova (
                        ID serial PRIMARY KEY,
                        prodotto VARCHAR(50),
                        prezzo FLOAT(4)
                    );""";

            ResultSet risposta = comando.executeQuery(query);
            System.out.println(risposta);*/
            System.out.println("1");
            Statement comando2 = connection.createStatement();

            String queryInserimento = """
                    INSERT INTO tabella_prova (prodotto, prezzo)
                    VALUES ('libreria', '99.99');
                    """;

            ResultSet rispostaInserimento = comando2.executeQuery(queryInserimento);
            System.out.println(rispostaInserimento);
            System.out.println("2");
            Statement comando3 = connection.createStatement();

            String queryVisualizzazione = """
                    TABLE tabella_prova;
                    """;

            ResultSet rispostaVisualizzazione = comando3.executeQuery(queryVisualizzazione);
            System.out.println(rispostaVisualizzazione);
            System.out.println("3");
        } catch (SQLException | ClassNotFoundException exception) {
            System.out.println(exception);
        }
    }
}
