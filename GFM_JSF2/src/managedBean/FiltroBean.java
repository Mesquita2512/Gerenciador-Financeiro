package managedBean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class FiltroBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean mfDespesa = false;//mostra formulario despesa
	private boolean mfReceita = false;//mostra formulario receita
	private boolean mfTransf = false;//mostra formulario transferencia
	private boolean mfBtVS = true;//mostra botoes de voltar e sair  

	public void mfDespesa() {
		setMfReceita(false);
		setMfTransf(false);
		setMfDespesa(true);
		setMfBtVS(false);
	}

	public void mfReceita() {
		setMfReceita(true);
		setMfTransf(false);
		setMfDespesa(false);
		setMfBtVS(false);
	}

	public void mfTransf() {
		setMfReceita(false);
		setMfTransf(true);
		setMfDespesa(false);
		setMfBtVS(false);
	}
	public String voltar() {
		setMfReceita(false);
		setMfTransf(false);
		setMfDespesa(false);
		setMfBtVS(true);
		return "gerenciador";
	}

	public void filtraDespesa() {

	}

	public void filtaReceita() {

	}

	public void filtraTransf() {

	}

	public boolean isMfDespesa() {
		return mfDespesa;
	}

	public void setMfDespesa(boolean mfDespesa) {
		this.mfDespesa = mfDespesa;
	}

	public boolean isMfReceita() {
		return mfReceita;
	}

	public void setMfReceita(boolean mfReceita) {
		this.mfReceita = mfReceita;
	}

	public boolean isMfTransf() {
		return mfTransf;
	}

	public void setMfTransf(boolean mfTransf) {
		this.mfTransf = mfTransf;
	}

	public boolean isMfBtVS() {
		return mfBtVS;
	}

	public void setMfBtVS(boolean mfBtVS) {
		this.mfBtVS = mfBtVS;
	}
	
}