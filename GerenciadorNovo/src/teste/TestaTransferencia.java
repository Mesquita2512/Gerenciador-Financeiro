package teste;





import dao.ContaDao;
import dao.TransferenciaDao;
import entity.Conta;
import entity.Transferencia;

public class TestaTransferencia {

	public static void main(String[] args) {
		Conta conta1 = new Conta();
		Conta conta2 = new Conta();
		ContaDao contaDao = new ContaDao();
		conta1 = contaDao.buscarporId(1);
		conta2 = contaDao.buscarporId(9);
		
		Transferencia transf = new Transferencia();
		
		transf.setConta_Debito(conta1);
		transf.setConta_Credito(conta2);
		transf.setVal_transferencia(123.89);
	
		
		TransferenciaDao tDao = new TransferenciaDao();
		tDao.salvarTransferencia(transf);

	}

}
