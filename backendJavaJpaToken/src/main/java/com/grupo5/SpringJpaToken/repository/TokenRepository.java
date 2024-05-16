package com.grupo5.SpringJpaToken.repository;

import com.grupo5.SpringJpaToken.model.Token;
import com.grupo5.SpringJpaToken.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {


    @Query("""
select t from Token t inner join Usuario u on t.usuario.id = u.id
where t.usuario.id = :userId and t.loggedOut = false
""")
    List<Token> findAllTokensByUser(Long userId);//La query de arriba se asocia a esta funcion

    Optional<Token> findByToken(String token);

}
