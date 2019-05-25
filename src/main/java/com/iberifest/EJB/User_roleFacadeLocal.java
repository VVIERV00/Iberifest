/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iberifest.EJB;

import com.iberifest.modelo.User;
import com.iberifest.modelo.User_role;

import javax.ejb.Local;
import java.util.List;

/**
 * @author adolfo
 */
@Local
public interface User_roleFacadeLocal {

    void create(User_role user_role);

    void edit(User_role user_role);

    void remove(User_role user_role);

    User_role find(Object id);

    List<User_role> findAll();

    List<User_role> findRange(int[] range);

    int count();

    List<User_role> findByUserId(User usuario);
}