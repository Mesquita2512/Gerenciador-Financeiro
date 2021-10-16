package dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.TransactionalException;

import entity.Usuario;
import fabricaConexao.FabricaJpa;

public class UsuarioDao {

	GenericoDao daoG = new GenericoDao();

	public boolean salvar(Usuario usuario) {
		return daoG.salvar(usuario);

	}

	public boolean atualizar(Usuario usuario) {
		return daoG.atualizar(usuario);
	}

	public Usuario getUsuario(String nomeUsuario, String senha) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		try {
			String jpql = "SELECT u from Usuario u where u.usuario = :usuario and u.senha = :senha";
			Query q = entityManager.createQuery(jpql);
			q.setParameter("usuario", nomeUsuario);
			q.setParameter("senha", senha);
			Usuario usuario = (Usuario) q.getSingleResult();
			return usuario;
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario buscarPorCpf(long id) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		Usuario resultado;
		try {
			entityManager.getTransaction().begin();

			resultado = entityManager.find(Usuario.class, id);

			entityManager.getTransaction().commit();
		} catch (EntityExistsException | TransactionalException e) {
			resultado = null;
			FabricaJpa.shutdown();
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getListaUsuario() {
		return (List<Usuario>) daoG.listarTodos(Usuario.class);
	}


	public boolean excluir(long cod_usuario) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Usuario deletar = entityManager.find(Usuario.class, cod_usuario);
			entityManager.remove(deletar);
			entityManager.getTransaction().commit();
			return true;
			
		} catch (EntityExistsException | TransactionalException e) {
			FabricaJpa.shutdown();
			return false;
		}
		
	}

}
