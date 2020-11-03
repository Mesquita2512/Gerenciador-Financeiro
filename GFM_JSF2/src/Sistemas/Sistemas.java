package Sistemas;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;

@ManagedBean
@SessionScoped
public class Sistemas implements Serializable {
	private static final long serialVersionUID = 1L;

	public String gerenciadorFinanceiro() {
		return "gerenciador";
	}

	public String gerenciadorDeSenhas() {

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!!!",
				"Esta função não foi implementada ainda, aguarde!!!");
		PrimeFaces.current().dialog().showMessageDynamic(message);

		return "Sistemas";
	}

	public String ControleCombustivel() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!!!",
				"Esta função não foi implementada ainda, aguarde!!!");
		PrimeFaces.current().dialog().showMessageDynamic(message);

		return "Sistemas";
	}

}