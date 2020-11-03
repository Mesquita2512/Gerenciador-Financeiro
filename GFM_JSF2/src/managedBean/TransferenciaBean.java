package managedBean;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import dao.ContaDao;
import dao.TransferenciaDao;
import entity.Conta;
import entity.Transferencia;

@ManagedBean
@SessionScoped
public class TransferenciaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Transferencia transf = new Transferencia();
	private Conta conta = new Conta();
	private long cod_ContaDebito;
	private long cod_ContaCredito;
	private double valorAnt;
	private List<Transferencia> lista;
	private List<Transferencia> listaTodos;

	@Inject
	private ContaDao cDao;
	@Inject
	private TransferenciaDao tDao;

	public TransferenciaBean() {
		this.cDao = new ContaDao();
		this.tDao = new TransferenciaDao();
	}

	public String redirecionar() {
		setValorAnt(transf.getVal_transferencia());
		setCod_ContaCredito(transf.getConta_Credito().getCod_Conta());
		setCod_ContaDebito(transf.getConta_Debito().getCod_Conta());
		return "editaTransferencia";
	}

	public void limpar() {
		transf = new Transferencia();
	}

	public String salvarTransferencia() {
		String tela = "cadastroTranferencia";
		conta = cDao.buscarporId(cod_ContaDebito);// busca a conta pelo coidg e armazena no objeto conta
		if (conta.getVal_Conta() < transf.getVal_transferencia()) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!!!",
					"O saldo da conta de débito é inferior ao valor da transação!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return tela;

		} else if (cod_ContaCredito == cod_ContaDebito) {// contas iguais
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!!!",
					"A conta de crédito não pode ser igual a conta de débito!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return tela;
		}

		else if (transf.getVal_transferencia() <= 0) {// valor menor ou iguala zero.
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!!!",
					"O valor deve ser maior que zero!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return tela;
		} else {// salvando a transferencia

			transf.setConta_Debito(conta); // seta a conta de debito na transferencia
			conta.setVal_Conta(conta.getVal_Conta() - transf.getVal_transferencia());// debita da conta o valor da
																						// transferencia
			cDao.atualizarConta(conta); // atualiza a conta

			conta = cDao.buscarporId(cod_ContaCredito);
			transf.setConta_Credito(conta);
			conta.setVal_Conta(conta.getVal_Conta() + transf.getVal_transferencia());
			cDao.atualizarConta(conta);

			if (tDao.salvarTransferencia(transf)) {
				transf = new Transferencia();
				conta = new Conta();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
						"Transferência realizada com sucesso!");
				PrimeFaces.current().dialog().showMessageDynamic(message);
			}
			return tela;
		}
	}

	public String editar() {
		if (transf.getVal_transferencia() <= 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!",
					"O valor deve ser maior que zero!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		} else if (getValorAnt() == transf.getVal_transferencia()) {
			tDao.atualizarTransferencia(transf);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
					"Transferência atualizada com sucesso!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		} else {

			conta = cDao.buscarporId(getCod_ContaCredito());
			conta.setVal_Conta(conta.getVal_Conta() - getValorAnt() + transf.getVal_transferencia());
			cDao.atualizarConta(conta);
			conta = new Conta();

			conta = cDao.buscarporId(getCod_ContaDebito());
			conta.setVal_Conta(conta.getVal_Conta() + getValorAnt() - transf.getVal_transferencia());
			cDao.atualizarConta(conta);
			conta = new Conta();

			tDao.atualizarTransferencia(transf);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
					"Transferência atualizada!");
			PrimeFaces.current().dialog().showMessageDynamic(message);

		}

		return "cadastroTransferencia";
	}

	public void excluir() {

		conta = cDao.buscarporId(transf.getConta_Credito().getCod_Conta());
		conta.setVal_Conta(conta.getVal_Conta() - transf.getVal_transferencia());// devolve o valor recebido
		cDao.atualizarConta(conta);
		conta = new Conta();

		conta = cDao.buscarporId(transf.getConta_Debito().getCod_Conta());
		conta.setVal_Conta(conta.getVal_Conta() + transf.getVal_transferencia());// recebe o valor debitado
		cDao.atualizarConta(conta);
		conta = new Conta();

		tDao.excluir(transf.getCod_Trasnferencia());
		transf = new Transferencia();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!!", "Transferência excluída!");
		PrimeFaces.current().dialog().showMessageDynamic(message);
	}

	public Transferencia getTransf() {
		return transf;
	}

	public void setTransf(Transferencia transf) {
		this.transf = transf;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public long getCod_ContaDebito() {
		return cod_ContaDebito;
	}

	public void setCod_ContaDebito(long cod_ContaDebito) {
		this.cod_ContaDebito = cod_ContaDebito;
	}

	public long getCod_ContaCredito() {
		return cod_ContaCredito;
	}

	public void setCod_ContaCredito(long cod_ContaCredito) {
		this.cod_ContaCredito = cod_ContaCredito;
	}

	public List<Transferencia> getLista() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		lista = tDao.getListaTransferencia();
		int num1 = lista.size();

		while (num1 > 0) {// remove todos as transferencias que nao pertencem a este usuario
			Transferencia transf2 = new Transferencia();// transf2 é uma transferencia criada para resolver problemas
														// com cadastro de nova despesa
			// com valor nulo
			transf2 = lista.get(num1 - 1);

			if (transf2.getConta_Credito().getUsuario().getCpf() != id) {
				lista.remove(num1 - 1);
			}
			num1 -= 1;
		}

		num1 = lista.size();
		if (num1 > 5) {// remove todas transferencias e deixa apenas as 5 ultima
			for (int i = num1; i > 5; i--) {
				lista.remove(num1 - i);
				num1 -= 1;
			}
		}
		return lista;
	}

	public void setLista(List<Transferencia> lista) {
		this.lista = lista;
	}

	public double getValorAnt() {
		return valorAnt;
	}

	public void setValorAnt(double valorAnt) {
		this.valorAnt = valorAnt;
	}

	public List<Transferencia> getListaTodos() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		listaTodos = tDao.getListaTransferencia();
		int num1 = listaTodos.size();

		while (num1 > 0) {// remove todos as transferencias que nao pertencem a este usuario
			Transferencia transf2 = new Transferencia();// transf2 é uma transferencia criada para resolver problemas
														// com cadastro de nova despesa
			// com valor nulo
			transf2 = listaTodos.get(num1 - 1);

			if (transf2.getConta_Credito().getUsuario().getCpf() != id) {
				listaTodos.remove(num1 - 1);
			}
			num1 -= 1;
		}
		return listaTodos;
	}

	public void setListaTodos(List<Transferencia> listaTodos) {
		this.listaTodos = listaTodos;
	}

}