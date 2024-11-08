package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidade.Cliente;

public class ClienteDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    public Cliente inserir(Cliente cliente) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
        em.close();
        return cliente;
    }

}
