package prontuario.drnubia.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Exame {
	private Long id;
	private String descricao;
	private LocalDateTime dataExame;
	private Paciente  paciente;
	

	public Exame() {
		super();
	}



	public Exame(Long id, String descricao, LocalDateTime dataExame, Paciente paciente) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.dataExame = dataExame;
		this.paciente = paciente;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public LocalDateTime getDataExame() {
		return dataExame;
	}



	public void setDataExame(LocalDateTime dataExame) {
		this.dataExame = dataExame;
	}



	public Paciente getPaciente() {
		return paciente;
	}



	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}



	@Override
	public String toString() {
		return "Exame [id=" + id + ", descricao=" + descricao + ", dataExame=" + dataExame + ", paciente=" + paciente
				+ "]";
	}



	@Override
	public int hashCode() {
		return Objects.hash(id);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exame other = (Exame) obj;
		return Objects.equals(id, other.id);
	}




}