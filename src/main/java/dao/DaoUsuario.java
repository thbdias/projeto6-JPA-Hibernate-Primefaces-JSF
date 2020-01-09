package dao;

import model.UsuarioPessoa;

public class DaoUsuario extends DaoGeneric<UsuarioPessoa> {

	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {	
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(pessoa);
		
		getEntityManager().getTransaction().commit();
	}
	
}
