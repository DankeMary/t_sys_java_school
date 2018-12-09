package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.UserDO;

public interface UserDAO  extends AbstractDao<UserDO, Integer> {
    UserDO createReturnObject(UserDO u);
    UserDO findByUsername(String username);
    boolean checkUniqueUsername(String username, int id);
}
