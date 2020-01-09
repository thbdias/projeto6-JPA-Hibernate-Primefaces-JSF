package managedBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.xml.bind.DatatypeConverter;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;

import dao.DaoEmail;
import dao.DaoGeneric;
import dao.DaoUsuario;
import model.EmailUser;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean {

	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private List<UsuarioPessoa> listUsuarioPessoa = new ArrayList<UsuarioPessoa>();
//	private DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
	private DaoUsuario daoUsuario = new DaoUsuario();
	private BarChartModel barChartModel = new BarChartModel();
	private EmailUser emailUser = new EmailUser();
	private DaoEmail daoEmail = new DaoEmail();
	private String campoPesquisa;

	@PostConstruct
	public void init() {
		listUsuarioPessoa = daoUsuario.listar(UsuarioPessoa.class);
		
		montarGrafico();
	}

	private void montarGrafico() {
		ChartSeries userSalario = new ChartSeries();

		for (UsuarioPessoa usuarioPessoa : listUsuarioPessoa) {
			userSalario.set(usuarioPessoa.getNome(), usuarioPessoa.getSalario());
		}
		
		barChartModel = new BarChartModel();
		barChartModel.addSeries(userSalario);
		barChartModel.setTitle("Gráfico de salários");
	}

	public BarChartModel getBarChartModel() {
		return barChartModel;
	}

	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}

	public void pesquisaCep(AjaxBehaviorEvent evento) {
		try {

			URL url = new URL("https://viacep.com.br/ws/" + usuarioPessoa.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = "";
			StringBuilder jsonCep = new StringBuilder();

			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}

			UsuarioPessoa userCepPessoa = new Gson().fromJson(jsonCep.toString(), UsuarioPessoa.class);

			usuarioPessoa.setCep(userCepPessoa.getCep());
			usuarioPessoa.setLogradouro(userCepPessoa.getLogradouro());
			usuarioPessoa.setComplemento(userCepPessoa.getComplemento());
			usuarioPessoa.setBairro(userCepPessoa.getBairro());
			usuarioPessoa.setLocalidade(userCepPessoa.getLocalidade());
			usuarioPessoa.setUf(userCepPessoa.getUf());
			usuarioPessoa.setUnidade(userCepPessoa.getUnidade());
			usuarioPessoa.setIbge(userCepPessoa.getIbge());
			usuarioPessoa.setGia(userCepPessoa.getGia());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String salvar() {
		daoUsuario.salvar(usuarioPessoa);
		listUsuarioPessoa.add(usuarioPessoa);		
		init();		
		// null -> informa se deseja informar msg para um determinado campo
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!"));
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
			init();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Removido com sucesso!"));
			usuarioPessoa = new UsuarioPessoa();

		} catch (Exception e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Informação: ", "Existem telefones para o usuário!"));
			} else {
				e.printStackTrace();
			}
		}

		return "";
	}
	
	public void setEmailUser(EmailUser emailUser) {
		this.emailUser = emailUser;
	}
	
	public EmailUser getEmailUser() {
		return emailUser;
	}
	
	public void addEmail() {
		emailUser.setPessoa(usuarioPessoa);
		emailUser = daoEmail.updateMerge(emailUser);
		usuarioPessoa.getEmails().add(emailUser);
		emailUser = new EmailUser();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Resultado: ", "Salvo com sucesso!"));		
	}
	
	public void removerEmail() throws Exception {
		String codigoEmail = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigoEmailParam");
		
		EmailUser emailRemover = new EmailUser();
		emailRemover.setId(Long.parseLong(codigoEmail));
		
		daoEmail.deletarPorId(emailRemover);		
		usuarioPessoa.getEmails().remove(emailRemover);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Resultado: ", "Removido com sucesso!"));
	}
	
	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}
	
	public String getCampoPesquisa() {
		return campoPesquisa;
	}
	
	public void pesquisar() {
		listUsuarioPessoa =  daoUsuario.pesquisar(campoPesquisa);
		montarGrafico();
	}
	
	public void upload(FileUploadEvent image) {
		String imagem = "data:image/png;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContents());
		usuarioPessoa.setImagem(imagem);
	}

}
