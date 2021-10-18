package dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionalException;
import entity.Receita;
import fabricaConexao.FabricaJpa;

public class ReceitaDao {

	GenericoDao daoG = new GenericoDao();

	public boolean salvarReceita(Receita receita) {
		return daoG.salvar(receita);
	}

	public boolean atualizarReceita(Receita receita) {
		return daoG.atualizar(receita);
	}
	
	public ReceitaDao buscarporId(int Id) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		ReceitaDao resultado;
		try {
			entityManager.getTransaction().begin();

			resultado = entityManager.find(ReceitaDao.class, Id);

			entityManager.getTransaction().commit();
		} catch (EntityExistsException | TransactionalException e) {
			resultado = null;
			FabricaJpa.shutdown();
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<Receita> getListaReceitas() {
		return (List<Receita>) daoG.listarTodos(Receita.class);
	}

	
	@SuppressWarnings("unchecked")
	public List<Receita> listarReceitasPorConta(long idConta) {
		return (List<Receita>) listarPorConta(Receita.class, idConta);
	}

	@SuppressWarnings("unchecked")
	public List<?> listarPorConta(Class<?> classe, long idConta) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		List<Object> lista;
		try {
			String tabela = classe.getName();
			String jpql = "from " + tabela + " where cod_conta = " + idConta;
			TypedQuery<Object> q = (TypedQuery<Object>) entityManager.createQuery(jpql, classe);
			lista = (List<Object>) q.getResultList();
		} catch (EntityExistsException | TransactionalException e) {
			lista = null;
			FabricaJpa.shutdown();
		}

		return lista;
	}

	public void excluir(long cod_receita) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Receita deletar = entityManager.find(Receita.class, cod_receita);
			entityManager.remove(deletar);
			entityManager.getTransaction().commit();
		} catch (EntityExistsException | TransactionalException e) {
			FabricaJpa.shutdown();
		}
	}
}
