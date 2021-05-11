package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author mesqu
 *
 */
@Entity
@Table(name = "Senha")
public class Senha {
	@Id
	@Column(name ="Cod_Senha")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cod_senha;
	@Column(name ="Login")
	private String login;
	@Column(name ="Usuario_Senha")
	private String usuarioSenha;
	@Column(name ="Senha")
	private String senha;
	@Column(name ="Observacoes")
	private String observacoes;
	@ManyToOne
	@JoinColumn(name = "Cod_Usuario")
	private Usuario usuarioConta;

	public Senha() {
		
	}
	
	public Senha( String login, String usuarioSenha, String senha, String observacoes,
			Usuario usuarioConta) {
		this.login = login;
		this.usuarioSenha = usuarioSenha;
		this.senha = senha;
		this.observacoes = observacoes;
		this.usuarioConta = usuarioConta;
	}

	public String getUsuarioSenha() {
		return usuarioSenha;
	}

	public void setUsuarioSenha(String usuarioSenha) {
		this.usuarioSenha = usuarioSenha;
	}

	public Usuario getUsuarioConta() {
		return usuarioConta;
	}

	public void setUsuarioConta(Usuario usuarioConta) {
		this.usuarioConta = usuarioConta;
	}

	public long getCod_senha() {
		return cod_senha;
	}

	public void setCod_senha(long cod_senha) {
		this.cod_senha = cod_senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@Override
	public String toString() {
		return "Senha [cod_senha=" + cod_senha + ", login=" + login + ", usuarioSenha=" + usuarioSenha + ", senha="
				+ senha + ", observacoes=" + observacoes + ", usuarioConta=" + usuarioConta + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cod_senha ^ (cod_senha >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Senha other = (Senha) obj;
		if (cod_senha != other.cod_senha)
			return false;
		return true;
	}
	
	

}
