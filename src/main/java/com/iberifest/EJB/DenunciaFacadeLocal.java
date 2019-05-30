/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.Denuncia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author adolfo
 */
@Local
public interface DenunciaFacadeLocal {

    void create(Denuncia denuncia);

    void edit(Denuncia denuncia);

    void remove(Denuncia denuncia);

    Denuncia find(Object id);

    List<Denuncia> findAll();

    List<Denuncia> findRange(int[] range);

    int count();
    
}
