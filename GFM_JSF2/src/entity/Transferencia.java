package entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Transferencia {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "Cod_Transferencia")
	private long cod_Trasnferencia;
	@ManyToOne
	private Conta conta_Debito;
	@ManyToOne
	private Conta conta_Credito;
	@Column(name = "Val_Transferencia")
	private double val_transferencia;
	@Column(name = "Data_Transferencia")
	@Temporal(TemporalType.DATE)
	private Date data_Tansferencia;

	public Transferencia() {
		super();
	}

	public Transferencia(Conta conta_Debito, Conta conta_Credito, double val_transferencia, Date data_Tansferencia) {
		this.conta_Debito = conta_Debito;
		this.conta_Credito = conta_Credito;
		this.val_transferencia = val_transferencia;
		this.data_Tansferencia = data_Tansferencia;
	}

	public long getCod_Trasnferencia() {
		return cod_Trasnferencia;
	}

	public void setCod_Trasnferencia(long cod_Trasnferencia) {
		this.cod_Trasnferencia = cod_Trasnferencia;
	}

	public Conta getConta_Debito() {
		return conta_Debito;
	}

	public void setConta_Debito(Conta conta_Debito) {
		this.conta_Debito = conta_Debito;
	}

	public Conta getConta_Credito() {
		return conta_Credito;
	}

	public void setConta_Credito(Conta conta_Credito) {
		this.conta_Credito = conta_Credito;
	}

	public double getVal_transferencia() {
		return val_transferencia;
	}

	public void setVal_transferencia(double val_transferencia) {
		this.val_transferencia = val_transferencia;
	}

	public Date getData_Tansferencia() {
		return data_Tansferencia;
	}

	public void setData_Tansferencia(Date data_Tansferencia) {
		this.data_Tansferencia = data_Tansferencia;
	}

	@Override
	public String toString() {
		return "Transferencia [cod_Trasnferencia=" + cod_Trasnferencia + ", conta_Debito=" + conta_Debito
				+ ", conta_Credito=" + conta_Credito + ", val_transferencia=" + val_transferencia
				+ ", data_Tansferencia=" + data_Tansferencia + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cod_Trasnferencia ^ (cod_Trasnferencia >>> 32));
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
		Transferencia other = (Transferencia) obj;
		if (cod_Trasnferencia != other.cod_Trasnferencia)
			return false;
		return true;
	}

}
