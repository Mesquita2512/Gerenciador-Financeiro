package managedBean;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import Regras.UsuarioRegras;
import dao.CategoriaDao;
import dao.ContaDao;
import dao.DespesaDao;
import entity.Categoria;
import entity.Conta;
import entity.Despesa;
import entity.Usuario;

@ManagedBean
@SessionScoped
public class DespesaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Despesa despesa = new Despesa();
	private Conta conta = new Conta();
	private long codContaAnt;
	private double valorAnt; // armazena o valor anterior caso o valor da despesa mude.
	private Categoria categoria = new Categoria();
	protected Usuario usuarioSessao = new Usuario();
	private List<Categoria> lista;
	private List<Despesa> listaDespesa;
	private List<Despesa> listaDespesaTodos;

	@Inject
	private DespesaDao dDao;
	@Inject
	private ContaDao cDao;
	@Inject
	private CategoriaDao catDao;

	UsuarioRegras uRegras = new UsuarioRegras();

	public DespesaBean() {
		this.dDao = new DespesaDao();
		this.cDao = new ContaDao();
		this.catDao = new CategoriaDao();
	}

	public void limpar() {
		despesa = new Despesa();
	}

	public String salvarDespesa() {

		if (despesa.getVal_Transacao() <= 0) {

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"Despesa não pode ter valor menor ou igual a zero!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		} else {
			conta = cDao.buscarporId(conta.getCod_Conta());
			if (conta.getVal_Conta() < despesa.getVal_Transacao()) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!",
						"O saldo da Conta é menor do que o valor da despesa!");
				PrimeFaces.current().dialog().showMessageDynamic(message);
				return "cadastroDespesa";
			}
			conta.setVal_Conta(conta.getVal_Conta() - despesa.getVal_Transacao());
			despesa.setConta(conta);
			if (dDao.salvarDespesa(despesa)) {

				cDao.atualizarConta(conta);
				conta = new Conta();
				despesa = new Despesa();

				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
						"Despesa cadastrada com sucesso!");
				PrimeFaces.current().dialog().showMessageDynamic(message);
				return "cadastroDespesa";
			} else {
				conta.setVal_Conta(conta.getVal_Conta() + despesa.getVal_Transacao());
				cDao.atualizarConta(conta);
				conta = new Conta();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
						"A despesa não foi salvo, tente novamente!");
				PrimeFaces.current().dialog().showMessageDynamic(message);
				return "cadastroDespesa";
			}
		}
		return "cadastroDespesa";
	}

	public String redirecionaDespesa(Despesa despesa) {
		codContaAnt = despesa.getConta().getCod_Conta();
		valorAnt = despesa.getVal_Transacao();
		return "editaDespesa";
	}

	public String editarDespesa() {
		conta = cDao.buscarporId(despesa.getConta().getCod_Conta());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
				"Despesa atualizada com sucesso!");

		if (despesa.getVal_Transacao() <= 0) {// testa se o valor é menor ou igual a zero
			FacesMessage messagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"Despesa não pode ter valor menor ou igual a zero!!!");
			PrimeFaces.current().dialog().showMessageDynamic(messagem);
		} else {
			if (getCodContaAnt() == conta.getCod_Conta()) {// testa se a conta nao mudou

				if (despesa.getVal_Transacao() == getValorAnt()) {// testa o valor permanece
					dDao.atualizarDespesa(despesa);// testado - não mudou a conta e nem mudou o valor
					despesa = new Despesa();

					PrimeFaces.current().dialog().showMessageDynamic(message);

				} else {// testa mudando o valor
					conta.setVal_Conta(conta.getVal_Conta() + valorAnt - despesa.getVal_Transacao());
					cDao.atualizarConta(conta);
					dDao.atualizarDespesa(despesa);

					conta = new Conta();
					despesa = new Despesa();

					PrimeFaces.current().dialog().showMessageDynamic(message);

				}
			} else {// a conta mudou

				// devolvendo o valor da despesa para a conta original
				conta = cDao.buscarporId(codContaAnt);
				conta.setVal_Conta(conta.getVal_Conta() + valorAnt);
				cDao.atualizarConta(conta);
				conta = new Conta();
				// debitando o valor para a nova conta
				conta = cDao.buscarporId(despesa.getConta().getCod_Conta());
				conta.setVal_Conta(conta.getVal_Conta() - despesa.getVal_Transacao());
				cDao.atualizarConta(conta);
				// teste - mudando a conta e atualizando
				despesa.setConta(conta);
				dDao.atualizarDespesa(despesa);
				conta = new Conta();
				despesa = new Despesa();

				PrimeFaces.current().dialog().showMessageDynamic(message);
			}
		}
		return "gerenciador";
	}

	public void excluir() {

		conta = cDao.buscarporId(despesa.getConta().getCod_Conta());
		conta.setVal_Conta(conta.getVal_Conta() + despesa.getVal_Transacao());
		cDao.atualizarConta(conta);
		conta = new Conta();

		dDao.excluir(despesa.getCod_Transacao());
		despesa = new Despesa();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
				"despesa excluída com sucesso!");
		PrimeFaces.current().dialog().showMessageDynamic(message);
	}

	public String cadastroCategoria() {
		return "cadastroCategoria";
	}

	public List<Categoria> getLista() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		lista = catDao.listarCategoriaUsuario(id, "'S'");
		return lista;
	}

	public void setLista(List<Categoria> lista) {
		this.lista = lista;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Despesa> getListaDespesa() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		listaDespesa = dDao.getListaDespesas();
		int num1 = listaDespesa.size();

		while (num1 > 0) {// remove todos as despesas que nao pertencem a este usuario
			Despesa des = new Despesa();// des é uma despesa criada para resolver problemas com cadastro de nova despesa
										// com valor nulo
			des = listaDespesa.get(num1 - 1);

			if (des.getConta().getUsuario().getCpf() != id) {
				listaDespesa.remove(num1 - 1);
			}
			num1 -= 1;

		}
		num1 = listaDespesa.size();
		if (num1 > 5) {// remove todas despesa e deixa apenas as 5 ultima
			for (int i = num1; i > 5; i--) {
				listaDespesa.remove(num1 - i);
				num1 -= 1;
			}
		}
		return listaDespesa;
	}

	public void setListaDespesa(List<Despesa> listaDespesa) {
		this.listaDespesa = listaDespesa;
	}

	public long getCodContaAnt() {
		return codContaAnt;
	}

	public void setCodContaAnt(long codContaAnt) {
		this.codContaAnt = codContaAnt;
	}

	public double getValorAnt() {
		return valorAnt;
	}

	public void setValorAnt(double valorAnt) {
		this.valorAnt = valorAnt;
	}

	public List<Despesa> getListaDespesaTodos() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		listaDespesaTodos = dDao.getListaDespesas();
		int cont = listaDespesaTodos.size();

		while (cont > 0) {// remove todos as despesas que nao pertencem a este usuario
			Despesa des = new Despesa();// des é uma despesa criada para resolver problemas com cadastro de nova despesa
										// com valor nulo
			des = listaDespesaTodos.get(cont - 1);

			if (des.getConta().getUsuario().getCpf() != id) {
				listaDespesaTodos.remove(cont - 1);
			}
			cont -= 1;

		}

		return listaDespesaTodos;
	}

	public void setListaDespesaTodos(List<Despesa> listaDespesaTodos) {
		this.listaDespesaTodos = listaDespesaTodos;
	}

}