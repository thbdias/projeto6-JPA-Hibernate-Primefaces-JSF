package managedBean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import dao.DaoUsuario;
import model.UsuarioPessoa;
import util.ColumnEnum;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean {

	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private List<UsuarioPessoa> listUsuarioPessoa = new ArrayList<UsuarioPessoa>();
	private DaoUsuario daoUsuario = new DaoUsuario();
	
	private boolean[] list = new boolean[6];
	private List<String> columns;
	private String[] selectedColumn;

	@PostConstruct
	public void init() {
		listUsuarioPessoa = daoUsuario.listar(UsuarioPessoa.class);
		
		for (int i = 0; i < list.length; i++) {
			list[i] = false;
		}
		
		columns = new ArrayList<String>();
		for (ColumnEnum ce : ColumnEnum.values()) {
			columns.add(ce.toString());
		}
	}	
	
	public void atualizaTable(int idCombo, boolean option) {
		list[idCombo] = option; 
	}
	
	public void toggleColumn() {		
		String[] result = getSelectedColumn();
		boolean flag = false;
		
		for (ColumnEnum ce : ColumnEnum.values()) {
			flag = false;
			for (String r : result) {
				if (ce.toString().equalsIgnoreCase(r)) {
					atualizaTable(ce.getValor(), true);
					flag = true;
					break;
				}				
			}
			if (!flag) {
				atualizaTable(ce.getValor(), false);
			}
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

	public void setList(boolean[] list) {
		this.list = list;
	}

	public boolean[] getList() {
		return list;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
	public List<String> getColumns() {
		return columns;
	}
	
	public void setSelectedColumn(String[] selectedColumn) {
		this.selectedColumn = selectedColumn;
	}
	
	public String[] getSelectedColumn() {
		return selectedColumn;
	}

}







