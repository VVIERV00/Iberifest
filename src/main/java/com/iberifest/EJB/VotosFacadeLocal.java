/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Comentario;
import com.iberifest.modelo.User;
import com.iberifest.modelo.Votos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author adolfo
 */
@Local
public interface VotosFacadeLocal {

    void create(Votos votos);

    void edit(Votos votos);

    void remove(Votos votos);

    Votos find(Object id);

    List<Votos> findAll();

    List<Votos> findRange(int[] range);

    int count();
    
    boolean existeVoto(Comentario comentario, User user);
    
    Votos findByComentAndUser(Comentario comentario, User user);
    
}
