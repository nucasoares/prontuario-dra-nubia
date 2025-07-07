package prontuario.drnubia.database;

import java.sql.Connection;

public interface IConnection {
	Connection getConnection();
	void closeConnection();
}
