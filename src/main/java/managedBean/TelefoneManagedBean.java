package managedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DaoUsuario;
import model.UsuarioPessoa;

@ManagedBean (name = "telefoneManagedBean")
@ViewScoped
public class TelefoneManagedBean {

	private UsuarioPessoa user = new UsuarioPessoa();
	private DaoUsuario daoUser = new DaoUsuario();
	
	@PostConstruct
	public void init() {
		//pegando param passado pela tela index.jsf
		String codidoUserParametro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigoUserParam");		
		user = daoUser.pesquisar(Long.parseLong(codidoUserParametro), UsuarioPessoa.class);
	}
	
	public void setUser(UsuarioPessoa user) {
		this.user = user;
	}
	
	public UsuarioPessoa getUser() {
		return user;
	}
	
}
