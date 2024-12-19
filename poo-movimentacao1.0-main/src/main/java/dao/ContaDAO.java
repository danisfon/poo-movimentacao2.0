package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import entidade.Conta;

public class ContaDAO extends GenericoDAO<Conta> {

   public ContaDAO() {
        super(Conta.class);
    }


    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");




    public int operacoesPorDia(Long id) {
        EntityManager em = emf.createEntityManager();
        Long conta = em.createQuery(
            "SELECT COUNT(m) FROM Movimentacao m WHERE m.conta.id = :id_conta AND DATE(m.dataTransacao) = CURRENT_DATE", Long.class)
            .setParameter("id_conta", id)
            .getSingleResult();
        em.close();
        return conta.intValue();
    }
    
    public int contarPorConta(Long id) {
        EntityManager em = emf.createEntityManager();
        Long conta = em.createQuery(
            "SELECT COUNT(c) FROM Conta c WHERE c.cliente.id = :id_cliente", Long.class)
            .setParameter("id_cliente", id).getSingleResult();
        em.close();
        return conta.intValue();
    }

    public Double calcularSaldo(Long id) {
        EntityManager em = emf.createEntityManager();
        Double calculo = em.createQuery(
                "SELECT COALESCE(SUM(CASE m.tipoTransacao " +
                        "WHEN 'DEPOSITO' THEN m.valorOperacao " +
                        "WHEN 'SAQUE' THEN -m.valorOperacao " +
                        "WHEN 'PIX' THEN -m.valorOperacao " +
                        "WHEN 'PAGAMENTO' THEN -m.valorOperacao " +
                        "ELSE 0 END), 0.0) " +
                        "FROM Movimentacao m WHERE m.conta.id = :id_conta",
                Double.class)
                .setParameter("id_conta", id)
                .getSingleResult();
        em.close();
    
        if (calculo == null) {
            return 0.0;
        }
        return calculo;
    }
    
}
