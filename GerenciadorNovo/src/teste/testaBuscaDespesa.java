package teste;

import java.util.List;
import dao.DespesaDao;
import entity.Despesa;

public class testaBuscaDespesa {

	public static void main(String[] args) {
		List<Despesa> listaDespesa;

		DespesaDao dDao = new DespesaDao();
		listaDespesa = dDao.getListaDespesas();

		if (listaDespesa.size() > 2) {
			for (int i = listaDespesa.size(); i > 2; i--) {
				listaDespesa.remove(listaDespesa.size() - i);
			}
		}

		listaDespesa.forEach((p) -> {
			String j = p.getDesc_Transacao();

			System.out.println(j);
		});

	}

}
