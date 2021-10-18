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
import dao.DespesaDao;
import dao.ReceitaDao;
import dao.TransferenciaDao;
import dao.UsuarioDao;
import entity.Conta;
import entity.Despesa;
import entity.Receita;
import entity.Transferencia;
import entity.Usuario;

@ManagedBean
@SessionScoped
public class ContaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Conta conta = new Conta();
	protected Usuario usuarioSessao = new Usuario();
	private List<Conta> lista;
	boolean mostra = true;
	boolean oculta = false;

	@Inject
	private ContaDao cDao;
	@Inject
	private UsuarioDao uDao;
	@Inject
	private DespesaDao dDao;
	@Inject
	private ReceitaDao rDao;
	@Inject
	private TransferenciaDao tDao;

	public ContaBean() {
		this.cDao = new ContaDao();
		this.uDao = new UsuarioDao();
		this.dDao = new DespesaDao();
		this.rDao = new ReceitaDao();
		this.tDao = new TransferenciaDao();
	}

	public void mostraOculta() {
		oculta = true;
		mostra = false;
	}

	public void limpar() {
		oculta = false;
		mostra = true;
		conta = new Conta();
	}

	public String salvarConta() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		this.usuarioSessao = uDao.buscarPorCpf(id);
		conta.setUsuario(usuarioSessao);

		if (conta.getVal_Conta() < 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!!!",
					"Conta não pode ter valor menor que zero!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		} else if (conta.getNom_Conta().equals("")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!!!",
					"O nome da conta não pode ser nulo!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		} else {
			if (cDao.salvarConta(conta)) {
				conta = new Conta();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ok!!!", "Conta Cadastrada!!!");
				PrimeFaces.current().dialog().showMessageDynamic(message);
			}
		}
		return null;
	}

	public String atualizarconta() {

		if (cDao.atualizarConta(conta)) {
			mostra = true;
			oculta = false;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!!", "Conta atualizada!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		}
		return "gerenciar_Conta";
	}

	public void excluirConta() {

		// busca e exclui todas as depesa assosciada a conta
		
		List<Despesa> listDespesas;
		listDespesas = dDao.listarDespesasPorConta(conta.getCod_Conta());
		listDespesas.forEach((p) -> {
			dDao.excluir(p.getCod_Transacao());
		});

		// busca e exclui todas as receitas assosciada a conta
		
		List<Receita> listRreceitas;
		listRreceitas = rDao.listarReceitasPorConta(conta.getCod_Conta());
		listRreceitas.forEach((p) -> {
			rDao.excluir(p.getCod_Transacao());
		});

		// busca e exclui todas as transferencias assosciada a conta
		
		List<Transferencia> listTransf;
		listTransf = tDao.listarTransfPorConta(conta.getCod_Conta());
		listTransf.forEach((p) -> {
			tDao.excluir(p.getCod_Trasnferencia());
		});

		// deletando a conta pelo id
		cDao.excluir(conta.getCod_Conta());
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Usuario getUsuarioSessao() {
		return usuarioSessao;
	}

	public void setUsuarioSessao(Usuario usuarioSessao) {
		this.usuarioSessao = usuarioSessao;
	}

	public List<Conta> getLista() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		lista = cDao.listarContasUsuario(id);
		return lista;
	}

	public void setLista(List<Conta> lista) {
		this.lista = lista;
	}

	public boolean isMostra() {
		return mostra;
	}

	public void setMostra(boolean mostra) {
		this.mostra = mostra;
	}

	public boolean isOculta() {
		return oculta;
	}

	public void setOculta(boolean oculta) {
		this.oculta = oculta;
	}

}