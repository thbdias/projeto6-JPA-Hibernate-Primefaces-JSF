package managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DaoGeneric;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean {

	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
	private List<UsuarioPessoa> listUsuarioPessoa = new ArrayList<UsuarioPessoa>();
	
	
	@PostConstruct
	public void init() {
		listUsuarioPessoa = daoGeneric.listar(UsuarioPessoa.class);
	}
	
	
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}

	public String salvar() {
		daoGeneric.salvar(usuarioPessoa);
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
			
			daoGeneric.deletarPorId(usuarioPessoa);
			listUsuarioPessoa.remove(usuarioPessoa);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Removido com sucesso!"));
			usuarioPessoa = new UsuarioPessoa();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				FacesContext.getCurrentInstance()
					.addMessage(null, 
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Existem telefones para o usuário!"));
			}
		}
		
		return "";
	}

}
