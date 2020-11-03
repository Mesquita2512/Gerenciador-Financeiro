package teste;

import java.util.List;

import dao.CategoriaDao;
import entity.Categoria;
import entity.Usuario;

public class buscaCategoriasPorUsuario {

	public static void main(String[] args) {

		CategoriaDao catDao = new CategoriaDao();
		List<Categoria> lista ;
		
		lista = catDao.listarCategoriaUsuario(789,"'S'");
		
		lista.forEach((p) ->{
            String j = p.getDen_Categoria();
            long k = p.getCod_Categoria();
            Usuario l = p.getUsuario();
            char m = p.getTip_Categoria();
            
            System.out.println(j+" "+k+" "+l +" "+m);
            });


	}

}
