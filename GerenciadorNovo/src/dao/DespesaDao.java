package dao;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionalException;
import entity.Despesa;
import fabricaConexao.FabricaJpa;

public class DespesaDao {

	GenericoDao daoG = new GenericoDao();

	public boolean salvarDespesa(Despesa despesa) {
		return daoG.salvar(despesa);
	}

	public boolean atualizarDespesa(Despesa despesa) {
		return daoG.atualizar(despesa);
	}

	@SuppressWarnings("unchecked")
	public List<Despesa> getListaDespesas() {
		return (List<Despesa>) daoG.listarTodos(Despesa.class);
	}

	public Despesa buscarporId(int Id) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		Despesa resultado;
		try {
			entityManager.getTransaction().begin();

			resultado = entityManager.find(Despesa.class, Id);

			entityManager.getTransaction().commit();
		} catch (EntityExistsException | TransactionalException e) {
			resultado = null;
			FabricaJpa.shutdown();
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<Despesa> listarDespesasPorConta(long idConta) {
		return (List<Despesa>) listarPorConta(Despesa.class, idConta);
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

	public void excluir(long cod_despesa) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Despesa deletar = entityManager.find(Despesa.class, cod_despesa);
			entityManager.remove(deletar);
			entityManager.getTransaction().commit();
		} catch (EntityExistsException | TransactionalException e) {
			FabricaJpa.shutdown();
		}
	}
}
