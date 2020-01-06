package dao;

import model.UsuarioPessoa;

public class DaoUsuario extends DaoGeneric<UsuarioPessoa> {

	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
		String sqlDeleteFone = "delete from telefoneuser where pessoa_id = " + pessoa.getId();
		getEntityManager().getTransaction().begin();
		getEntityManager().createNativeQuery(sqlDeleteFone).executeUpdate();
		getEntityManager().getTransaction().commit();
		
		super.deletarPorId(pessoa);
	}
	
}
