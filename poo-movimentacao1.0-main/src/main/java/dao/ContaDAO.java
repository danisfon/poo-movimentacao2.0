package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidade.Conta;

public class ContaDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    public Conta inserir(Conta cliente) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
        em.close();
        return cliente;
    }

}
