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
import Regras.ValidaCPF;
import dao.CategoriaDao;
import dao.ContaDao;
import dao.UsuarioDao;
import entity.Categoria;
import entity.Conta;
import entity.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();
	private Conta conta = new Conta();
	private ContaBean contaBean = new ContaBean();
	private String senha2; // variavel para confirmar a alteração de senha
	UsuarioRegras uRegras = new UsuarioRegras();

	@Inject
	private UsuarioDao udao;
	@Inject
	private ContaDao cDao;
	@Inject
	private CategoriaDao catDao;

	public UsuarioBean() {
		this.udao = new UsuarioDao();
		this.cDao = new ContaDao();
		this.catDao = new CategoriaDao();
	}

	public String logar() {
		this.usuario = udao.getUsuario(usuario.getUsuario(), usuario.getSenha());
		if (usuario == null) {
			usuario = new Usuario();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário não encontrado!", "Erro no Login!"));

			return null;
		} else {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idUsuario", usuario.getCpf());
			return "Sistemas";
		}
	}

	public String cadastrarUsuario() {
		return "cadastroUsuario";
	}

	public String salvar() {
		
		ValidaCPF validacpf = new ValidaCPF();
		String converter = String.valueOf(usuario.getCpf());
		while (converter.length() < 11) {
			converter = "0" + converter;
		}
		if (usuario.getNome().equals("")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"O nome do Usuário nao pode ser Vazio!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return "cadastroUsuario";
		}else if(usuario.getUsuario().equals("")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"O nome de Usuário nao pode ser Vazio!");
					PrimeFaces.current().dialog().showMessageDynamic(message);
			return "cadastroUsuario";
		}
		else if (validacpf.isCPF(converter) == false) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!!!", "CPF Inválido!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return "cadastroUsuario";
		}

		else if (!usuario.getSenha().equals(senha2) || senha2.length() < 4
				|| (usuario.getSenha().length() == 0 && senha2.length() == 0)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"As senhas não conferem ou são nulas!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return "cadastroUsuario";
		} else {
			if (udao.salvar(usuario)) {
				conta.setUsuario(usuario);
				conta.setNom_Conta("Carteira");
				cDao.salvarConta(conta);
				uRegras.inserirCategorias(usuario);

				usuario = new Usuario();
				conta = new Conta();

				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idUsuario",
						usuario.getCpf());

				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
						"Usuário cadastrado com sucesso!");
				PrimeFaces.current().dialog().showMessageDynamic(message);

				return "index";
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!", "Usuário não cadastrado!");
				PrimeFaces.current().dialog().showMessageDynamic(message);
				return "cadastroUsuario";
			}
		}

	}

	public String atualizaAcesso() {
		
		 if (!usuario.getSenha().equals(senha2) || senha2.length() < 4
				|| (usuario.getSenha().length() == 0 && senha2.length() == 0)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"As senhas não conferem ou são nulas!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		} else {
			System.out.println(usuario);
			if (udao.atualizar(usuario)) {
				System.out.println(usuario);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
						"Seus dados foram atualizados!");
				PrimeFaces.current().dialog().showMessageDynamic(message);
			}
		}
		return "gerenciar_Usuario";
	}

	public String nao() {
		return "gerenciar_Usuario";
	}
	public String excluir() {

		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		usuario = udao.buscarPorCpf(id);
		System.out.println(senha2+"pjdknvkkjvzjvbz,jvbzxvbx,vb,bx,bbzxmcvnbxcnvbxmnvbcnb");
			if (usuario.getSenha().equals(getSenha2())) {// verifica se a senha informada é igua a senha do usuario
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!", "A senha não é válida!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return "gerenciar_Usuario";
		}

		else if (!contaBean.getLista().isEmpty()) {// verifica se o usuario possue contas ativas 
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
					"Você deve Excluir sua contas antes de deletar seu usuário!!!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
			return "gerenciar_Conta";

		} else {

			
			List<Categoria> listCat;
			listCat = catDao.listarCategoriasPorUsuario(id);
			listCat.forEach((p) -> {
			catDao.excluir(p.getCod_Categoria());
			});
			if (udao.excluir(id)) {// caso a senha seja valida e nao possua contas ativas, o sistema permite a
									// exclusão
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
						"Usuario Excluido Com sucesso!");
				PrimeFaces.current().dialog().showMessageDynamic(message);
				return "index";
			} else {// caso ocoara algum erro durante a excluão no bd é mostrado a mensagem abaixo
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!",
						"Não foi possivel excluir o seu usuário!!!");
				PrimeFaces.current().dialog().showMessageDynamic(message);
				return "gerenciar_Usuario";
			}
		}

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

}