package Regras;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import dao.CategoriaDao;
import entity.Categoria;
import entity.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioRegras implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaDao cat_Dao;

	public UsuarioRegras() {
		this.cat_Dao = new CategoriaDao();
	}

	public void inserirCategorias(Usuario usuario) {
		Categoria cat1 = new Categoria("Supermercado", 'S', usuario);
		cat_Dao.salvarCategoria(cat1);
		Categoria cat2 = new Categoria("Farmacia", 'S', usuario);
		cat_Dao.salvarCategoria(cat2);
		Categoria cat3 = new Categoria("Lazer", 'S', usuario);
		cat_Dao.salvarCategoria(cat3);
		Categoria cat4 = new Categoria("Carro", 'S', usuario);
		cat_Dao.salvarCategoria(cat4);
		Categoria cat5 = new Categoria("Salario", 'E', usuario);
		cat_Dao.salvarCategoria(cat5);
		cat_Dao = new CategoriaDao();

	}

}