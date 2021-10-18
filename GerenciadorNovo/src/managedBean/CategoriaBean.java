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
import dao.UsuarioDao;
import entity.Categoria;
import entity.Usuario;

@ManagedBean
@SessionScoped
public class CategoriaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuarioSessao = new Usuario();
	private Categoria categoria = new Categoria();
	private List<Categoria> listaTodas;

	@Inject
	private UsuarioDao uDao;
	@Inject
	protected CategoriaDao cat_Dao;

	public CategoriaBean() {
		this.uDao = new UsuarioDao();
		this.cat_Dao = new CategoriaDao();

	}

	public String salvarCategoria() {

		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		this.usuarioSessao = uDao.buscarPorCpf(id);
		categoria.setUsuario(usuarioSessao);
		if (cat_Dao.salvarCategoria(categoria)) {
			categoria = new Categoria();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
					"Categoria cadastrada com sucesso!");
			PrimeFaces.current().dialog().showMessageDynamic(message);
		}

		return "cadastroCategoria";
	}

	public Usuario getUsuarioSessao() {
		return usuarioSessao;
	}

	public void setUsuarioSessao(Usuario usuarioSessao) {
		this.usuarioSessao = usuarioSessao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getListaTodas() {
		long id = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
		listaTodas = cat_Dao.listarCategoriasPorUsuario(id);
		return listaTodas;
	}

	public void setListaTodas(List<Categoria> listaTodas) {
		this.listaTodas = listaTodas;
	}

}