package teste;

import dao.CategoriaDao;
import dao.UsuarioDao;
import entity.Categoria;
import entity.Usuario;

public class TestaCadastroCategoria {

	public static void main(String[] args) {
		CategoriaDao cDao = new CategoriaDao();
		Categoria cat = new Categoria();
		Usuario usu = new Usuario();
		UsuarioDao uDao = new UsuarioDao();
		
		cat.setDen_Categoria("Lazer");
		cat.setTip_Categoria('S');
		usu = uDao.buscarPorCpf(123);
		cat.setUsuario(usu);
		
		cDao.salvarCategoria(cat);
		

	}

}
