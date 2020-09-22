package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }


   public List<Order> findAll(OrderSearch orderSearch) {
     return em.createQuery("select o from Order o join o.member m " +
               "where o.status = :status " +
               "and m.name like :name", Order.class)
               .setParameter("status", orderSearch.getOrderStatus())
               .setParameter("name", orderSearch.getMemberName())
               .setMaxResults(1000)
               .getResultList();
    }

    public List<Order> findAllWithMemberDeliver() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }
}
