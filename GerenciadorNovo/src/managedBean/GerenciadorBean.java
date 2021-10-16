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
import entity.Categoria;
import entity.Conta;
import entity.Despesa;
import entity.Receita;
import entity.Transferencia;
import entity.Usuario;

@ManagedBean
@SessionScoped
public class GerenciadorBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();
	private List<Conta> lista;
	private Conta conta = new Conta();
	private Despesa despesa = new Despesa();
	private Receita receita = new Receita();
	private Transferencia transf = new Transferencia();
	private Categoria cat = new Categoria();

	@Inject
	private ContaDao cDao;

	public GerenciadorBean() {
		this.cDao = new ContaDao();
	}

	public String sair() {
//		
//		conta = new Conta();
//		despesa = new Despesa();
//		receita = new Receita();
//		transf = new Transferencia();
//		cat = new Categoria();
		return "index";
	}

	public String voltar() {
//		conta = new Conta();
//		despesa = new Despesa();
//		receita = new Receita();
//		transf = new Transferencia();
//		cat = new Categoria();
		return "gerenciador";
	}

	public String criarReceita() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		lista = cDao.listarContasUsuario(id);
		if (lista.isEmpty()) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"Você deve cadastrar uma conta primeiro!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return "gerenciador";
		} else {
			return "cadastroReceita";
		}
	}

	public String criarDespesa() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		lista = cDao.listarContasUsuario(id);
		if (lista.isEmpty()) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"Você deve cadastrar uma conta primeiro!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return "gerenciador";
		} else {
			return "cadastroDespesa";
		}

	}

	public String realizarTransferencia() {
		String retorno = null;
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		lista = cDao.listarContasUsuario(id);
		if (lista.isEmpty()) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"Você deve cadastrar uma conta primeiro!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return "gerenciador";
		} else if (lista.size() == 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!!!",
					"Você possue somente uma conta e não pode realizar transferências!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			retorno = "gerenciador";
		} else {
			retorno = "cadastroTransferencia";
		}
		return retorno;
	}

	public String gerenciarConta() {
		return "gerenciar_Conta";
	}

	public String gerenciarUsuario() {
		return "gerenciar_Usuario";
	}

	public String editarExcluiTransacao() {
		return "editarExcluirTransacao";
	}

	public String relatoriosGraficos() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!!!",
				"Esta função não foi implementada ainda, aguarde!!!");
		PrimeFaces.current().dialog().showMessageDynamic(message);
		return "gerenciador";
	}

	public List<Conta> getLista() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		lista = cDao.listarContasUsuario(id);
		return lista;
	}

	public void setLista(List<Conta> lista) {
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

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public Transferencia getTransf() {
		return transf;
	}

	public void setTransf(Transferencia transf) {
		this.transf = transf;
	}

	public Categoria getCat() {
		return cat;
	}

	public void setCat(Categoria cat) {
		this.cat = cat;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}