package dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionalException;

import entity.Despesa;
import entity.Transferencia;
import fabricaConexao.FabricaJpa;

public class TransferenciaDao {
	GenericoDao daoG = new GenericoDao();

	public boolean salvarTransferencia(Transferencia transferencia) {
		return daoG.salvar(transferencia);
	}
	
	public boolean atualizarTransferencia(Transferencia transferencia) {
		return daoG.atualizar(transferencia);
	}
	
	@SuppressWarnings("unchecked")
	public List<Transferencia> getListaTransferencia() {
		return (List<Transferencia>) daoG.listarTodos(Transferencia.class);
	}

	@SuppressWarnings("unchecked")
	public List<Transferencia> listarTransfPorConta(long idConta) {
		return (List<Transferencia>) listarPorConta(Transferencia.class, idConta);
	}

	@SuppressWarnings("unchecked")
	public List<?> listarPorConta(Class<?> classe, long idConta) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		List<Object> lista;
		try {
			String tabela = classe.getName();
			String jpql = "from " + tabela + " where conta_Debito_Cod_Conta = " + idConta
					+ "or conta_Credito_Cod_Conta = " + idConta;
			TypedQuery<Object> q = (TypedQuery<Object>) entityManager.createQuery(jpql, classe);
			lista = (List<Object>) q.getResultList();
		} catch (EntityExistsException | TransactionalException e) {
			lista = null;
			FabricaJpa.shutdown();
		}

		return lista;
	}

	public void excluir(long cod_transf) {
		EntityManager entityManager = FabricaJpa.getEntityManagerFactory().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Transferencia deletar = entityManager.find(Transferencia.class, cod_transf);
			entityManager.remove(deletar);
			entityManager.getTransaction().commit();
		} catch (EntityExistsException | TransactionalException e) {
			FabricaJpa.shutdown();
		}
	}
}
