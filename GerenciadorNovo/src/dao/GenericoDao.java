package dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionalException;

import fabricaConexao.FabricaJpa;

public class GenericoDao {
	
	public boolean salvar(Object objeto) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		boolean resultado = true;
		try {
			entityManager.getTransaction().begin();

			entityManager.persist(objeto);

			entityManager.getTransaction().commit();
		} catch (EntityExistsException | TransactionalException e) {
			resultado = false;
			FabricaJpa.shutdown();
		}

		return resultado;
	}

	public Object buscarPorId(Class<?> classe, int id) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		Object resultado;
		try {
			entityManager.getTransaction().begin();

			resultado = entityManager.find(classe, id);

			entityManager.getTransaction().commit();
		} catch (EntityExistsException | TransactionalException e) {
			resultado = null;
			FabricaJpa.shutdown();
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<?> listarTodos(Class<?> classe) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		List<Object> lista;
		try {
			String tabela = classe.getName();
			String jpql = "from " + tabela;
			TypedQuery<Object> q = (TypedQuery<Object>) entityManager.createQuery(jpql, classe);
			lista = (List<Object>) q.getResultList();
		} catch (EntityExistsException | TransactionalException e) {
			lista = null;
			FabricaJpa.shutdown();
		}

		return lista;
	}

	public boolean atualizar(Object objeto) {
		boolean resultado = true;
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(objeto);
			entityManager.getTransaction().commit();
			resultado = true;
		} catch (EntityExistsException | TransactionalException e) {
			resultado = false;
			FabricaJpa.shutdown();
		}
		return resultado;
	}
	
}
