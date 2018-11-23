package net.tsystems.impl;

import net.tsystems.entities.UserDO;
import net.tsystems.entitydao.UserDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl extends AbstractDaoImpl<UserDO, Integer> implements UserDAO {
    @Override
    public UserDO createReturnObject(UserDO u) {
        getEntityManager().persist(u);
        return u;
    }

    @Override
    public UserDO findByUsername(String username) {
        List<UserDO> list = (List<UserDO>) getEntityManager()
                .createQuery("from UserDO where username='" + username + "'")
                .list();
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public boolean checkUniqueUsername(String username, int id) {
        List<UserDO> list = (List<UserDO>)getEntityManager()
                .createQuery("from UserDO where username='" + username + "' and id <> " + id)
                .list();
        return list.size() == 0;
    }
}
