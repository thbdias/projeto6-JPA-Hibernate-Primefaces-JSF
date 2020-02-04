package managedBean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import dao.DaoUsuario;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean {

	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private List<UsuarioPessoa> listUsuarioPessoa = new ArrayList<UsuarioPessoa>();
	private DaoUsuario daoUsuario = new DaoUsuario();
	
	private boolean c1;
	private boolean c2;
	private boolean c3;
	private boolean c4;
	private boolean c5;
	private boolean c6;
	private boolean[] list = new boolean[6];
	private List<String> cities;
	private String[] selectedCities2;

	@PostConstruct
	public void init() {
		listUsuarioPessoa = daoUsuario.listar(UsuarioPessoa.class);
		
		for (int i = 0; i < list.length; i++) {
			list[i] = true;
		}
		
		c1 = c2 = c3 = c4 = c5 = c6 = true;
		cities = new ArrayList<String>();
        cities.add("Miami");
        cities.add("London");
        cities.add("Paris");
        cities.add("Istanbul");
        cities.add("Berlin");
        cities.add("Barcelona");
        cities.add("Rome");
        cities.add("Brasilia");
        cities.add("Amsterdam");
	}
	
	
	public void atualizaTable(int idCombo) {
		if (list[idCombo] == true) { // se tiver coluna
			list[idCombo] = false;
		} else if (list[idCombo] == false) { // se nao tiver coluna
			list[idCombo] = true;
		}
	}
	
	public void novoMetodo() {
		System.out.println();
		String[] result = getSelectedCities2();
		for (String r : result) {
			System.out.println("- " + r);
		}
		
	}

	
	
	
	
	
	/*********************************************************
	 * GETTERS AND SETTERS * 
	 * *******************************************************
	 * */
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}

	public List<UsuarioPessoa> getListUsuarioPessoa() {
		return listUsuarioPessoa;
	}
	
	public void setC1(boolean c1) {
		this.c1 = c1;
	}

	public boolean getC1() {
		return c1;
	}

	public void setList(boolean[] list) {
		this.list = list;
	}

	public boolean[] getList() {
		return list;
	}

	public boolean isC2() {
		return c2;
	}

	public void setC2(boolean c2) {
		this.c2 = c2;
	}

	public boolean isC3() {
		return c3;
	}

	public void setC3(boolean c3) {
		this.c3 = c3;
	}

	public boolean isC4() {
		return c4;
	}

	public void setC4(boolean c4) {
		this.c4 = c4;
	}

	public boolean isC5() {
		return c5;
	}

	public void setC5(boolean c5) {
		this.c5 = c5;
	}

	public boolean isC6() {
		return c6;
	}

	public void setC6(boolean c6) {
		this.c6 = c6;
	}
	
	public void setCities(List<String> cities) {
		this.cities = cities;
	}
	
	public List<String> getCities() {
		return cities;
	}
	
	public void setSelectedCities2(String[] selectedCities2) {
		this.selectedCities2 = selectedCities2;
	}
	
	public String[] getSelectedCities2() {
		return selectedCities2;
	}

}







