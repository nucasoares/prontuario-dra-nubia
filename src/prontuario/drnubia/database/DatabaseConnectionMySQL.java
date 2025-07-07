package prontuario.drnubia.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import prontuario.drnubia.util.ConfigLoader;

public class DatabaseConnectionMySQL implements IConnection {

    private final String USERNAME = ConfigLoader.getValor("DB_USER");
    private final String PASSWORD = ConfigLoader.getValor("DB_PASSWORD");
    private final String ADDRESS = ConfigLoader.getValor("DB_ADDRESS");
    private final String PORT = ConfigLoader.getValor("DB_PORT");
    private final String DATABASE = ConfigLoader.getValor("DB_SCHEMA");

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://%s:%s/%s".formatted(ADDRESS, PORT, DATABASE),
                USERNAME,
                PASSWORD
            );
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco:");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeConnection() {
        
    }
}