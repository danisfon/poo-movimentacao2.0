package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidade.Cliente;
import javax.persistence.Query;

public class ClienteDAO extends GenericoDAO<Cliente> {

    public ClienteDAO() {
        super(Cliente.class);
    }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");
    

    public Cliente buscarPorCpf(String cpf){
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.cpfCorrentista = :cpf");
        query.setParameter("cpf", cpf);
		Cliente cliente = (Cliente) query.getSingleResult();
		em.close();
		return cliente;
	}

}
