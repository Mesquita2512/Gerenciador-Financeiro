package dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionalException;
import entity.Categoria;
import fabricaConexao.FabricaJpa;

public class CategoriaDao {
	GenericoDao daoG = new GenericoDao();

	public boolean salvarCategoria(Categoria categoria) {
		return daoG.salvar(categoria);
	}

	
	@SuppressWarnings("unchecked")
	public List<Categoria> listarCategoriaUsuario(long idUsuario, String tipoCategoria) {
		return (List<Categoria>) listarPorUsuario(Categoria.class, idUsuario, tipoCategoria);
	}

	@SuppressWarnings("unchecked")
	public List<?> listarPorUsuario(Class<?> classe, long idUsuario, String tipoCategoria) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		List<Object> lista;
		try {
			String tabela = classe.getName();
			String jpql = "from " + tabela + " where cod_usuario = " + idUsuario +" and Tip_Categoria = "+tipoCategoria ;
			TypedQuery<Object> q = (TypedQuery<Object>) entityManager.createQuery(jpql, classe);
			lista = (List<Object>) q.getResultList();
		} catch (EntityExistsException | TransactionalException e) {
			lista = null;
			FabricaJpa.shutdown();
		}

		return lista;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Categoria> listarCategoriasPorUsuario(long idUsuario) {
		return (List<Categoria>) listarPorUsuario(Categoria.class, idUsuario);
	}

	@SuppressWarnings("unchecked")
	public List<?> listarPorUsuario(Class<?> classe, long idUsuario) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		List<Object> lista;
		try {
			String tabela = classe.getName();
			String jpql = "from " + tabela + " where cod_usuario = " + idUsuario;
			TypedQuery<Object> q = (TypedQuery<Object>) entityManager.createQuery(jpql, classe);
			lista = (List<Object>) q.getResultList();
		} catch (EntityExistsException | TransactionalException e) {
			lista = null;
			FabricaJpa.shutdown();
		}

		return lista;
	}
	
	public Categoria buscarPorId(long id) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		Categoria resultado;
		try {
			entityManager.getTransaction().begin();

			resultado = entityManager.find(Categoria.class, id);

			entityManager.getTransaction().commit();
		} catch (EntityExistsException | TransactionalException e) {
			resultado = null;
			FabricaJpa.shutdown();
		}

		return resultado;
	}
	
	public void excluir(long cod_categoria) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Categoria deletar = entityManager.find(Categoria.class, cod_categoria);
			entityManager.remove(deletar);
			entityManager.getTransaction().commit();
			
		} catch (EntityExistsException | TransactionalException e) {
			FabricaJpa.shutdown();
			
		}
		
	}
}
