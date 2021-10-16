package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Conta")
public class Conta {
	/**
	 * 
	 */
	@Id
	@Column(name = "Cod_Conta")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cod_Conta;
	@Column(name = "Nom_Conta")
	private String nom_Conta;
	@Column(name = "Val_Conta")
	private double val_Conta;
	@ManyToOne
    @JoinColumn(name = "Cod_Usuario")
    private Usuario usuario;
	
	public Conta() {
		
	}

	public Conta( String nom_Conta, double val_Conta, Usuario usuario) {		
		this.nom_Conta = nom_Conta;
		this.val_Conta = val_Conta;
		this.usuario = usuario;
	}

	public long getCod_Conta() {
		return cod_Conta;
	}

	public void setCod_Conta(long cod_Conta) {
		this.cod_Conta = cod_Conta;
	}

	public String getNom_Conta() {
		return nom_Conta;
	}

	public void setNom_Conta(String nom_Conta) {
		this.nom_Conta = nom_Conta;
	}

	public double getVal_Conta() {
		return val_Conta;
	}

	public void setVal_Conta(double val_Conta) {
		this.val_Conta = val_Conta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Conta [cod_Conta=" + cod_Conta + ", nom_Conta=" + nom_Conta + ", val_Conta=" + val_Conta + ", usuario="
				+ usuario + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cod_Conta ^ (cod_Conta >>> 32));
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
		Conta other = (Conta) obj;
		if (cod_Conta != other.cod_Conta)
			return false;
		return true;
	}

	
}
