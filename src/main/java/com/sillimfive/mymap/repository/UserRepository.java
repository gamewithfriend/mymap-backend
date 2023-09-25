package com.sillimfive.mymap.repository;

import com.sillimfive.mymap.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class,id);
    }

    public User findEmailOne(String email){
        return em.find(User.class,email);
    }

    public List<User> findByLoginId(String loginId){
        return em.createQuery("select u from User u where u.loginId = :loginId ",User.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }

}
