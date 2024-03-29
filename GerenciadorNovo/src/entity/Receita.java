package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Receita extends transacao {
	@Column(name = "Categoria")
	private String categoria;
	@ManyToOne
	@JoinColumn(name = "Cod_Conta")
	private Conta conta;

	public Receita() {

	}

	public Receita(long cod_Transacao, String desc_Transacao, double val_Transa��o, Date date, String categoria,
			Conta conta) {
		super(cod_Transacao, desc_Transacao, val_Transa��o, date);
		this.categoria = categoria;
		this.conta = conta;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public String toString() {
		return "Entrada [Categoria=" + categoria + ", conta=" + conta + ", cod_Transacao=" + cod_Transacao
				+ ", desc_Transacao=" + desc_Transacao + ", val_Transacao=" + val_Transacao + ", date=" + date + "]";
	}

}
