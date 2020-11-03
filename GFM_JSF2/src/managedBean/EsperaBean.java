package managedBean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class EsperaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public String sair() {
		return "index";
	}

}