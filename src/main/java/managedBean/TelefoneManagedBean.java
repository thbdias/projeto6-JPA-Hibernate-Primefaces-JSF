package managedBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DaoTelefone;
import dao.DaoUsuario;
import model.TelefoneUser;
import model.UsuarioPessoa;

@ManagedBean(name = "telefoneManagedBean")
@ViewScoped
public class TelefoneManagedBean {

	private UsuarioPessoa user = new UsuarioPessoa();
	private DaoUsuario daoUser = new DaoUsuario();
	private DaoTelefone daoTelefone = new DaoTelefone();
	private TelefoneUser telefoneUser = new TelefoneUser();

	@PostConstruct
	public void init() {
		// pegando param passado pela tela index.jsf
		String codidoUserParametro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("codigoUserParam");
		user = daoUser.pesquisar(Long.parseLong(codidoUserParametro), UsuarioPessoa.class);
	}

	public void setTelefoneUser(TelefoneUser telefoneUser) {
		this.telefoneUser = telefoneUser;
	}

	public TelefoneUser getTelefoneUser() {
		return telefoneUser;
	}

	public void setUser(UsuarioPessoa user) {
		this.user = user;
	}

	public UsuarioPessoa getUser() {
		return user;
	}

	public void setDaoTelefone(DaoTelefone daoTelefone) {
		this.daoTelefone = daoTelefone;
	}

	public DaoTelefone getDaoTelefone() {
		return daoTelefone;
	}
	
	public String salvar() {
		telefoneUser.setPessoa(user);
		daoTelefone.salvar(telefoneUser);
		telefoneUser = new TelefoneUser();
		user = daoUser.pesquisar(user.getId(), UsuarioPessoa.class);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Telefone salvo com sucesso!"));
		return "";
	}
	
	public String removeTelefone() throws Exception {
		daoTelefone.deletarPorId(telefoneUser);
		user = daoUser.pesquisar(user.getId(), UsuarioPessoa.class);
		telefoneUser = new TelefoneUser();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Telefone removido com sucesso!"));
		return "";
	}

}





