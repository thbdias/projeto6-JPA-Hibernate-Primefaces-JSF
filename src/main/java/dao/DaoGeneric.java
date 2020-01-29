package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pos_java_maven_hibernate.HibernateUtil;

public class DaoGeneric<E> {
	
	private EntityManager entityManager = HibernateUtil.getEntityManager();
	
	public List<E> listar(Class<E> entidade){
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> lista = entityManager.createQuery("from " + entidade.getName()).getResultList();
		transaction.commit();
		return lista;
	}
	
}
