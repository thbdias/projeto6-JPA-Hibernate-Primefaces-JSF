package pos_java_maven_hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//quando o projeto subir, quando se quiser fazer alguma alteração no banco essa classe vai ser chamada
//ela vai ler o arquivo persistence.xml e vai deixar instânciada a conexão com BD
public class HibernateUtil {
	
	//padrão Singleton - ler o persistence apenas uma vez
	public static EntityManagerFactory factory = null;
	
	//não precisa instaciar o HibernateUtil
	static {
		init();
	}
	
	private static void init() {
		try {
			if (factory == null) {
				//pos-java-maven-hibernate = name -> do arquivo persistence.xml
				factory = Persistence.createEntityManagerFactory("pos-java-maven-hibernate");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager(); //prover a parte de persistencia
	}
	
	public static Object getPrimaryKey(Object entity) { //retorna a primary key
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}

}
