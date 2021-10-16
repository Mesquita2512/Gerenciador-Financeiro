package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class transacao {
	@Id
	@Column(name = "Cod_Transacao")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long cod_Transacao;
	@Column(name = "Desc_Transacao")
	protected String desc_Transacao;
	@Column(name = "Val_Transacao")
	protected double val_Transacao;
	@Column(name = "Dat_Transacao")
	@Temporal(TemporalType.DATE)
	protected Date date;
	


	public transacao() {
		
	}

	public transacao(long cod_Transacao, String desc_Transacao, double val_Transacao, Date date) {
		
		this.cod_Transacao = cod_Transacao;
		this.desc_Transacao = desc_Transacao;
		this.val_Transacao = val_Transacao;
		this.date = date;
	}

	public long getCod_Transacao() {
		return cod_Transacao;
	}

	public void setCod_Transacao(long cod_Transacao) {
		this.cod_Transacao = cod_Transacao;
	}

	public String getDesc_Transacao() {
		return desc_Transacao;
	}

	public void setDesc_Transacao(String desc_Transacao) {
		this.desc_Transacao = desc_Transacao;
	}

	public double getVal_Transacao() {
		return val_Transacao;
	}

	public void setVal_Transacao(double val_Transacao) {
		this.val_Transacao = val_Transacao;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cod_Transacao ^ (cod_Transacao >>> 32));
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
		transacao other = (transacao) obj;
		if (cod_Transacao != other.cod_Transacao)
			return false;
		return true;
	}

}
