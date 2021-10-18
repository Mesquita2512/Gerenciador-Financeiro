package entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario {
	/**
	 * 
	 */

	@Id
	@Column(name = "Cod_usuario")
	private long cpf;
	@Column(nullable = false, name = "Nome")
	private String nome;
	@Column(name = "Endereço")
	private String endereco;
	@Column(name = "Telefone")
	private String telefone;
	@Column(name = "Email")
	private String email;
	@Column(nullable = false, name = "Usuario")
	private String usuario;
	@Column(nullable = false, name = "Senha")
	private String senha;

	public Usuario() {

	}

	public Usuario(long cpf, String nome, String endereco, String telefone, String email, String usuario,
			String senha) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
		this.usuario = usuario;
		this.senha = senha;
	}

	public long getCpf() {
		return cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cpf ^ (cpf >>> 32));
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
		Usuario other = (Usuario) obj;
		if (cpf != other.cpf)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [cpf=" + cpf + ", nome=" + nome + ", endereco=" + endereco + ", telefone=" + telefone
				+ ", email=" + email + ", usuario=" + usuario + ", senha=" + senha + "]";
	}

}
