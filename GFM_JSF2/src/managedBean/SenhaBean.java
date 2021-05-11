package managedBean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import dao.SenhaDao;

@ManagedBean
@SessionScoped
public class SenhaBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private SenhaDao sDao;

}
