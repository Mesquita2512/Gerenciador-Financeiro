package dao;

import entity.Senha;

public class SenhaDao {

	GenericoDao daoG = new GenericoDao();

	public boolean salvarSenha(Senha senha) {
		return daoG.salvar(senha);
	}

	public boolean atualizarSenha(Senha senha) {
		return daoG.atualizar(senha);
	}

}
