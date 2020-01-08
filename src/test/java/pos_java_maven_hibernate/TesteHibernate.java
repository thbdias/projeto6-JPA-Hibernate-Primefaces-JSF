package pos_java_maven_hibernate;

import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TesteHibernate {

	@Test
	public void testeSalvar(){
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setIdade(45);
		pessoa.setLogin("teste");
		pessoa.setNome("maria 3");
		pessoa.setSenha("123");
		pessoa.setSobrenome("rosario");
//		pessoa.setEmail("teste@teste.com");
		
		daoGeneric.salvar(pessoa);
	}
	
	@Test
	public void testeBuscar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(1L);
		
		pessoa = daoGeneric.pesquisar(pessoa);
		
		System.out.println("===>>> " + pessoa);
	}
	
	@Test
	public void testeBuscar2() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();		
		UsuarioPessoa pessoa = daoGeneric.pesquisar(2L, UsuarioPessoa.class);		
		System.out.println("===>>> " + pessoa);
	}
	
	@Test
	public void testeUpdate() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();		
		UsuarioPessoa pessoa = daoGeneric.pesquisar(2L, UsuarioPessoa.class);
		
		pessoa.setIdade(101);
		pessoa.setNome("Nome atualizado hibernate");
		pessoa.setSenha("asdfasd");
		
		pessoa = daoGeneric.updateMerge(pessoa);
		
		System.out.println("===>>> " + pessoa);
	}
	
	
	@Test
	public void testeDelete() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();		
		UsuarioPessoa pessoa = daoGeneric.pesquisar(2L, UsuarioPessoa.class);
		
//		daoGeneric.deletarPorId(pessoa);				
	}
	
	@Test
	public void testeListar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();		
		List<UsuarioPessoa> pessoas = daoGeneric.listar(UsuarioPessoa.class);
		
		for (UsuarioPessoa pessoa : pessoas) {
			System.out.println("---------------");
			System.out.println(pessoa);
			System.out.println("---------------");
		}				
	}
	
	@Test
	public void testeQueryList(){
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery("from UsuarioPessoa where nome = 'maria 2'").getResultList();
		
		for (UsuarioPessoa pessoa : list) {			
			System.out.println(pessoa);			
		}
	}	
	
	@Test
	public void testeQueryListMaxResult(){
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery("from UsuarioPessoa order by nome").setMaxResults(3).getResultList();
		
		for (UsuarioPessoa pessoa : list) {			
			System.out.println(pessoa);			
		}
	}
	
	@Test
	public void testeQueryListPrameter(){
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager()
									.createQuery("from UsuarioPessoa where nome = :nome or sobrenome = :sobrenome")
									.setParameter("nome", "maria")
									.setParameter("sobrenome", "marta")
									.getResultList();
		
		for (UsuarioPessoa pessoa : list) {			
			System.out.println(pessoa);			
		}
	}
	
	
	@Test
	public void testQuerySomaIdade() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();		
		Long somaIdade = (Long) daoGeneric.getEntityManager().createQuery("select sum(u.idade) from UsuarioPessoa u").getSingleResult();		
		System.out.println("Soma de todas as idade é ---> " + somaIdade);
	}
	
	
	@Test
	public void testeNameQuery1() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.todos").getResultList();
		
		for (UsuarioPessoa pessoa : list) {			
			System.out.println(pessoa);			
		}
	}
	
	@Test
	public void testeNameQuery2() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.buscaPorNome").setParameter("nome", "maria").getResultList();
		
		for (UsuarioPessoa pessoa : list) {			
			System.out.println(pessoa);			
		}
	}
	
	@Test
	public void testeGravaTelefone() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(1L, UsuarioPessoa.class);
		
		TelefoneUser telefoneUser = new TelefoneUser();
		telefoneUser.setTipo("casa");
		telefoneUser.setNumero("555555");
		telefoneUser.setPessoa(pessoa);
		
		daoGeneric.salvar(telefoneUser);
	}
	
	@Test
	public void testeConsultaTelefones() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(1L, UsuarioPessoa.class);
		
		for (TelefoneUser telefoneUser : pessoa.getTelefoneUsers()) {
			System.out.println(telefoneUser.getNumero());
			System.out.println(telefoneUser.getTipo());
			System.out.println(telefoneUser.getPessoa().getNome());
			System.out.println("---------------------------------");
		}
		
		
	}
	
}






