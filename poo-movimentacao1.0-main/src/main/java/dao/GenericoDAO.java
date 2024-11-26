package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GenericoDAO<T> {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    private final Class<T> classeEntidade;

    public GenericoDAO(Class<T> classeEntidade) {
        this.classeEntidade = classeEntidade; 
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public T inserir(T objeto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(objeto);
            em.getTransaction().commit();
            return objeto;
        } finally {
            em.close();        
        }
    }

    public T alterar(T objeto) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(objeto);
            em.getTransaction().commit();
            return objeto;
        } finally {
            em.close();
        }
    }

    public void excluir(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T entidade = em.find(classeEntidade, id);
            if (entidade != null) {
                em.remove(entidade);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }        
    }

    public List<T> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<T> listarTodos = em.createQuery("from " + classeEntidade.getSimpleName(), classeEntidade).getResultList();
        em.close();
        return listarTodos;
    }

    public T buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        T entidade = em.find(classeEntidade, id);
        em.close();
        return entidade;
    }
}
