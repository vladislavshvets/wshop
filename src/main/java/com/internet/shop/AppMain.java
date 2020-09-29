package com.internet.shop;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.dao.UserDao;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import java.util.Set;

public class AppMain {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductDao productDaoJdbc = (ProductDao) injector.getInstance(ProductDao.class);
        UserDao userDaoJdbc = (UserDao) injector.getInstance(UserDao.class);
        System.out.println(productDaoJdbc.getAll());
        User user = userDaoJdbc.getById(4L).get();
        System.out.println(user);
        user.setRoles(Set.of(Role.of("ADMIN")));
        Set<Role> res = user.getRoles();
        for (Role role : res) {
            if (role.getRoleName().equals(Role.RoleName.ADMIN)) {
                role.setId(1L);
            }
            if (role.getRoleName().equals(Role.RoleName.USER)) {
                role.setId(2L);
            }
        }
        userDaoJdbc.update(user);
        System.out.println(userDaoJdbc.getById(4L).get());
        System.out.println(userDaoJdbc.findByLogin("Vlad"));
        System.out.println(userDaoJdbc.getAll());
    }
}
