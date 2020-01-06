package managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DaoGeneric;
import dao.DaoUsuario;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean {

	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private List<UsuarioPessoa> listUsuarioPessoa = new ArrayList<UsuarioPessoa>();
//	private DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
	private DaoUsuario daoUsuario = new DaoUsuario();
	
	
	@PostConstruct
	public void init() {
		listUsuarioPessoa = daoUsuario.listar(UsuarioPessoa.class);
	}
	
	
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}

	public String salvar() {
		daoUsuario.salvar(usuarioPessoa);
		listUsuarioPessoa.add(usuarioPessoa);
		//null -> informa se deseja informar msg para um determinado campo
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!"));
		usuarioPessoa = new UsuarioPessoa();
		return "";
	}
	
	public String novo() {
		usuarioPessoa = new UsuarioPessoa();
		return "";
	}
	
	public List<UsuarioPessoa> getListUsuarioPessoa() {		
		return listUsuarioPessoa;
	}
		
	public String remover() {
		try {
			
			daoUsuario.removerUsuario(usuarioPessoa);
			listUsuarioPessoa.remove(usuarioPessoa);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Removido com sucesso!"));
			usuarioPessoa = new UsuarioPessoa();
			
		} catch (Exception e) {			
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				FacesContext.getCurrentInstance()
					.addMessage(null, 
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Existem telefones para o usuário!"));
			}
			else {
				e.printStackTrace();
			}
		}
		
		return "";
	}

}
