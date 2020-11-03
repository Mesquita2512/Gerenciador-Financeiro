package dao;

import entity.Conta;
import fabricaConexao.FabricaJpa;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionalException;

public class ContaDao {

	GenericoDao daoG = new GenericoDao();

	public boolean salvarConta(Conta conta) {
		return daoG.salvar(conta);
	}

	public Conta buscarporId(long Id) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		Conta resultado;
		try {
			entityManager.getTransaction().begin();

			resultado = entityManager.find(Conta.class, Id);

			entityManager.getTransaction().commit();
		} catch (EntityExistsException | TransactionalException e) {
			resultado = null;
			FabricaJpa.shutdown();
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<Conta> listarContasUsuario(long idUsuario) {
		return (List<Conta>) listarPorUsuario(Conta.class, idUsuario);
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

	public boolean atualizarConta(Conta conta) {
		return daoG.atualizar(conta);
	}

	public void excluir(long cod_Conta) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Conta deletar = entityManager.find(Conta.class, cod_Conta);
			entityManager.remove(deletar);
			entityManager.getTransaction().commit();
		} catch (EntityExistsException | TransactionalException e) {
			FabricaJpa.shutdown();
		}
	}
}
