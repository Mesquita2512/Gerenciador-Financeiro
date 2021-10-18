package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Cod_Categoria")
	private long cod_Categoria;
	@Column( name = "Den_Categoria")
	private String den_Categoria;
	@Column( name = "Tip_Categoria")
	private char tip_Categoria;
	@ManyToOne
	@JoinColumn(name = "Cod_Usuario")
	private Usuario usuario;

	public Categoria() {
		
	}

	public Categoria(String den_Categoria, char tip_Categoria, Usuario usuario) {

		this.den_Categoria = den_Categoria;
		this.tip_Categoria = tip_Categoria;
		this.usuario = usuario;
	}

	public long getCod_Categoria() {
		return cod_Categoria;
	}

	public void setCod_Categoria(long cod_Categoria) {
		this.cod_Categoria = cod_Categoria;
	}

	public String getDen_Categoria() {
		return den_Categoria;
	}

	public void setDen_Categoria(String den_Categoria) {
		this.den_Categoria = den_Categoria;
	}

	
	public char getTip_Categoria() {
		return tip_Categoria;
	}

	public void setTip_Categoria(char tip_Categoria) {
		this.tip_Categoria = tip_Categoria;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cod_Categoria ^ (cod_Categoria >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (cod_Categoria != other.cod_Categoria)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Categoria [cod_Categoria=" + cod_Categoria + ", den_Categoria=" + den_Categoria + ", tip_Categoria="
				+ tip_Categoria + ", usuario=" + usuario + "]";
	}

}
