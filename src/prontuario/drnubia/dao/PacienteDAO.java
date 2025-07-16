package prontuario.drnubia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import prontuario.drnubia.database.IConnection;
import prontuario.drnubia.exception.PacienteJaCadastradoException;
import prontuario.drnubia.model.Paciente;

public class PacienteDAO implements IEntityDAO<Paciente>{
	private IConnection conn;

    public PacienteDAO(IConnection connection) {
        this.conn = connection;
    }

    @Override
    public void create(Paciente t) {
        if (t.getCpf() == null || t.getNome() == null) {
            throw new IllegalArgumentException("Nome e CPF são obrigatórios.");
        }

        try {
            PreparedStatement pstm = conn.getConnection()
                    .prepareStatement("INSERT INTO PACIENTES (nome, cpf) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, t.getNome());
            pstm.setString(2, t.getCpf());
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            }
            rs.close();
            pstm.close();
        }catch (SQLException e) {
        	
            if (e.getMessage().toLowerCase().contains("duplicate") || e.getErrorCode() == 1062) {
                throw new PacienteJaCadastradoException("Já existe um paciente com o CPF: " + t.getCpf());
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Paciente findById(Long id) {
        try {
            PreparedStatement pstm = conn.getConnection()
                    .prepareStatement("SELECT * FROM PACIENTES WHERE id = ?");
            pstm.setLong(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Paciente p = new Paciente(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("cpf")
                );
                rs.close();
                pstm.close();
                return p;
            }

            rs.close();
            pstm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Paciente t) {
        try {
            PreparedStatement pstm = conn.getConnection()
                    .prepareStatement("UPDATE PACIENTES SET nome = ?, cpf = ? WHERE id = ?");
            pstm.setString(1, t.getNome());
            pstm.setString(2, t.getCpf());
            pstm.setLong(3, t.getId());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Paciente t) {
        try {
            PreparedStatement pstm = conn.getConnection()
                    .prepareStatement("DELETE FROM PACIENTES WHERE id = ?");
            pstm.setLong(1, t.getId());
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Paciente> findAll() {
        List<Paciente> lista = new ArrayList<>();

        try {
            PreparedStatement pstm = conn.getConnection()
                    .prepareStatement("SELECT * FROM PACIENTES");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Paciente p = new Paciente(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("cpf")
                );
                lista.add(p);
            }

            rs.close();
            pstm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
