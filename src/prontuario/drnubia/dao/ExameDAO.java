package prontuario.drnubia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import prontuario.drnubia.database.IConnection;
import prontuario.drnubia.model.Exame;
import prontuario.drnubia.model.Paciente;

public class ExameDAO implements IEntityDAO<Exame> {
	 private IConnection conn;

	    public ExameDAO(IConnection connection) {
	        this.conn = connection;
	    }

	    @Override
	    public void create(Exame exame) {
	        if (exame.getDescricao() == null || exame.getDataExame() == null 
	            || exame.getPaciente() == null || exame.getPaciente().getId() == null) {
	            throw new IllegalArgumentException("Descrição, Data do exame e Paciente são obrigatórios.");
	        }

	        try {
	            PreparedStatement pstm = conn.getConnection()
	                .prepareStatement(
	                    "INSERT INTO EXAMES (descricao, data_exame, paciente_id) VALUES (?, ?, ?)", 
	                    Statement.RETURN_GENERATED_KEYS);

	            pstm.setString(1, exame.getDescricao());
	            pstm.setTimestamp(2, Timestamp.valueOf(exame.getDataExame()));
	            pstm.setLong(3, exame.getPaciente().getId());

	            pstm.executeUpdate();

	            ResultSet rs = pstm.getGeneratedKeys();
	            if (rs.next()) {
	                exame.setId(rs.getLong(1));
	            }

	            rs.close();
	            pstm.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public Exame findById(Long id) {
	        try {
	            PreparedStatement pstm = conn.getConnection()
	                .prepareStatement("SELECT * FROM EXAMES WHERE id = ?");
	            pstm.setLong(1, id);

	            ResultSet rs = pstm.executeQuery();

	            if (rs.next()) {
	                Exame exame = new Exame();
	                exame.setId(rs.getLong("id"));
	                exame.setDescricao(rs.getString("descricao"));
	                exame.setDataExame(rs.getTimestamp("data_exame").toLocalDateTime());

	                // Preenche apenas o ID do paciente associado, pode completar o paciente depois
	                Paciente paciente = new Paciente();
	                paciente.setId(rs.getLong("paciente_id"));
	                exame.setPaciente(paciente);

	                rs.close();
	                pstm.close();

	                return exame;
	            }

	            rs.close();
	            pstm.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return null;
	    }

	    @Override
	    public void update(Exame exame) {
	        try {
	            PreparedStatement pstm = conn.getConnection()
	                .prepareStatement(
	                    "UPDATE EXAMES SET descricao = ?, data_exame = ?, paciente_id = ? WHERE id = ?");

	            pstm.setString(1, exame.getDescricao());
	            pstm.setTimestamp(2, Timestamp.valueOf(exame.getDataExame()));
	            pstm.setLong(3, exame.getPaciente().getId());
	            pstm.setLong(4, exame.getId());

	            pstm.executeUpdate();
	            pstm.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void delete(Exame exame) {
	        try {
	            PreparedStatement pstm = conn.getConnection()
	                .prepareStatement("DELETE FROM EXAMES WHERE id = ?");
	            pstm.setLong(1, exame.getId());
	            pstm.execute();
	            pstm.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public List<Exame> findAll() {
	        List<Exame> lista = new ArrayList<>();

	        try {
	            PreparedStatement pstm = conn.getConnection()
	                .prepareStatement("SELECT * FROM EXAMES");

	            ResultSet rs = pstm.executeQuery();

	            while (rs.next()) {
	                Exame exame = new Exame();
	                exame.setId(rs.getLong("id"));
	                exame.setDescricao(rs.getString("descricao"));
	                exame.setDataExame(rs.getTimestamp("data_exame").toLocalDateTime());

	                Paciente paciente = new Paciente();
	                paciente.setId(rs.getLong("paciente_id"));
	                exame.setPaciente(paciente);

	                lista.add(exame);
	            }

	            rs.close();
	            pstm.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return lista;
	    }
	


}
