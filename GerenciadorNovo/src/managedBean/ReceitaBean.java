package managedBean;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import dao.CategoriaDao;
import dao.ContaDao;
import dao.ReceitaDao;
import entity.Categoria;
import entity.Conta;
import entity.Receita;
import entity.Usuario;

@ManagedBean
@SessionScoped
public class ReceitaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Receita receita = new Receita();
	private Conta conta = new Conta();
	private ContaBean contaBean = new ContaBean();
	private long codContaAnt;
	private double valorAnt; // armazena o valor anterior caso o valor da despesa mude.
	private Categoria categoria = new Categoria();
	protected Usuario usuarioSessao = new Usuario();
	private List<Categoria> lista;
	private List<Receita> listaTodos;
	private List<Receita> listaReceitas;
	

	@Inject
	private ReceitaDao rDao;
	@Inject
	private ContaDao cDao;
	@Inject
	private CategoriaDao catDao;

	public ReceitaBean() {
		this.rDao = new ReceitaDao();
		this.cDao = new ContaDao();
		this.catDao = new CategoriaDao();
	}

	public void limpar() {
		receita = new Receita();
	}

	public String salvar() {

		conta = cDao.buscarporId(conta.getCod_Conta());
		receita.setConta(conta);

		if (receita.getVal_Transacao() <= 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"Receita não pode ter valor menor ou igual a zero!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		} else {
			if (rDao.salvarReceita(receita)) {

				conta.setVal_Conta(conta.getVal_Conta() + receita.getVal_Transacao());
				cDao.atualizarConta(conta);
				conta = new Conta();
				receita = new Receita();

				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
						"Receita cadastrada com sucesso!");
				PrimeFaces.current().dialog().showMessageDynamic(message);
			}
		}
		return "cadastroReceita";
	}

	public String redirecionaReceita() {
		codContaAnt = receita.getConta().getCod_Conta();
		valorAnt = receita.getVal_Transacao();
		return "editaReceita";
	}

	public String editarReceita() {
		conta = cDao.buscarporId(receita.getConta().getCod_Conta());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
				"Receita atualizada com sucesso!");

		if (receita.getVal_Transacao() <= 0) {// testa se o valor é menor ou iguala a zero
			FacesMessage messagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"Receita não pode ter valor menor ou igual a zero!!!");
			PrimeFaces.current().dialog().showMessageDynamic(messagem);
		} else {// o valor é maior que zero
			if (getCodContaAnt() == conta.getCod_Conta()) {// testa se a conta nao mudou

				if (receita.getVal_Transacao() == getValorAnt()) {// testa se o valor nao mudou
					rDao.atualizarReceita(receita);// testado - não mudou a conta e nem mudou o valor
					receita = new Receita();
					PrimeFaces.current().dialog().showMessageDynamic(message);

				} else {// neste caso o valor mudou
					conta.setVal_Conta(conta.getVal_Conta() - valorAnt + receita.getVal_Transacao());
					cDao.atualizarConta(conta);
					rDao.atualizarReceita(receita);

					conta = new Conta();
					receita = new Receita();
					PrimeFaces.current().dialog().showMessageDynamic(message);

				}
			} else {// neste caso a conta mudou

				// debita o valor da receita da a conta original
				conta = cDao.buscarporId(getCodContaAnt());
				conta.setVal_Conta(conta.getVal_Conta() - getValorAnt());
				cDao.atualizarConta(conta);
				conta = new Conta();
				// credita o valor para a nova conta
				conta = cDao.buscarporId(receita.getConta().getCod_Conta());
				conta.setVal_Conta(conta.getVal_Conta() + receita.getVal_Transacao());
				cDao.atualizarConta(conta);
				// mudando a conta e atualizando a conta e a receita
				receita.setConta(conta);
				rDao.atualizarReceita(receita);
				conta = new Conta();
				receita = new Receita();
				PrimeFaces.current().dialog().showMessageDynamic(message);
			}
		}
		return "gerenciador";
	}

	public void excluir() {

		conta = cDao.buscarporId(receita.getConta().getCod_Conta());
		conta.setVal_Conta(conta.getVal_Conta() - receita.getVal_Transacao());
		cDao.atualizarConta(conta);
		conta = new Conta();

		rDao.excluir(receita.getCod_Transacao());
		receita = new Receita();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
				"Receita excluída com sucesso!");
		PrimeFaces.current().dialog().showMessageDynamic(message);
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuarioSessao() {
		return usuarioSessao;
	}

	public void setUsuarioSessao(Usuario usuarioSessao) {
		this.usuarioSessao = usuarioSessao;
	}

	public List<Categoria> getLista() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		lista = catDao.listarCategoriaUsuario(id, "'E'");
		return lista;
	}

	public void setLista(List<Categoria> lista) {
		this.lista = lista;
	}

	public List<Receita> getListaReceitas() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		listaReceitas = rDao.getListaReceitas();
		int num = listaReceitas.size();
		
		while (num > 0) {// remove todos as despesas que nao pertencem a este usuario
			Receita rec = new Receita();// des é uma despesa criada para resolver problemas com cadastro de nova receita
										// com valor nulo, erro ocorre quando se usa a variavel receita
			rec = listaReceitas.get(num - 1);

			if (rec.getConta().getUsuario().getCpf() != id) {
				listaReceitas.remove(num - 1);
			}
			num -= 1;

		}
		num = listaReceitas.size();
		if (num > 5) {
			for (int i = num; i > 5; i--) {
				listaReceitas.remove(num - i);
				num -= 1;
			}
		}
		return listaReceitas;
	}

	public void setListaReceitas(List<Receita> listaReceitas) {
		this.listaReceitas = listaReceitas;
	}

	public ContaBean getContaBean() {
		return contaBean;
	}

	public void setContaBean(ContaBean contaBean) {
		this.contaBean = contaBean;
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

	public List<Receita> getListaTodos() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		listaTodos = rDao.getListaReceitas();
		int num = listaTodos.size();
		
		while (num > 0) {// remove todos as despesas que nao pertencem a este usuario
			Receita rec = new Receita();// des é uma despesa criada para resolver problemas com cadastro de nova receita
										// com valor nulo, erro ocorre quando se usa a variavel receita
			rec = listaTodos.get(num - 1);

			if (rec.getConta().getUsuario().getCpf() != id) {
				listaTodos.remove(num - 1);
			}
			num -= 1;

		}
		return listaTodos;
	}

	public void setListaTodos(List<Receita> listaTodos) {
		this.listaTodos = listaTodos;
	}

}