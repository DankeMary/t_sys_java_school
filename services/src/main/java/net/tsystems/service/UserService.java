package net.tsystems.service;

import net.tsystems.bean.TicketBean;
import net.tsystems.bean.UserBean;
import net.tsystems.bean.UserBeanExpanded;
import net.tsystems.beanmapper.UserBeanMapper;
import net.tsystems.beanmapper.UserBeanMapperImpl;
import net.tsystems.entities.UserDO;
import net.tsystems.entitydao.UserDAO;
import net.tsystems.entitymapper.UserEntityMapper;
import net.tsystems.entitymapper.UserEntityMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("userService")
@Transactional
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private UserDAO userDao;
    private UserEntityMapper entityMapper = new UserEntityMapperImpl();
    private UserBeanMapper beanMapper = new UserBeanMapperImpl();

    private TicketService ticketService;

    private PasswordEncoder passwordEncoder;

    public void create(UserBean user, String role) {
        try {
            if (role == null || role.isEmpty())
                user.setRole("USER");
            else
                user.setRole(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.create(userBeanToDO(user));
        } catch (Exception e) {
            LOG.error("Failed to create user", e);
        }
    }

    public UserBean createReturnObject(UserBean user) {
        UserBean userBean = null;
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userBean = userDOToBean(userDao.createReturnObject(userBeanToDO(user)));
        } catch (Exception e) {
            LOG.error("Failed to create user", e);
        }
        return userBean;
    }

    public void update(UserBean user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.update(userBeanToDO(user));
        } catch (Exception e) {
            LOG.error("Failed to update user", e);
        }
    }

    public void delete(int id) {
        try {
            userDao.delete(userDao.find(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to delete user by id=%s", id), e);
        }
    }

    public UserBean getUser(String username) {
        UserBean userBean = null;
        try {
            userBean = userDOToBean(userDao.findByUsername(username));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get user by username=%s", username), e);
        }
        return userBean;
    }

    public List<UserBean> getAll() {
        List<UserBean> users = new LinkedList<>();
        try {
            users = userDOListToBeanList(userDao.findAll());
        } catch (Exception e) {
            LOG.error("Failed to get all users", e);
        }
        return users;
    }

    public List<UserBean> getAll(int page, int maxResult) {
        List<UserBean> users = new LinkedList<>();
        try {
            users = userDOListToBeanList(userDao.findAll(page, maxResult));
        } catch (Exception e) {
            LOG.error("Failed to get all users", e);
        }
        return users;
    }

    public UserBean getUser(int id) {
        UserBean userBean = null;
        try {
            userBean = userDOToBean(userDao.find(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get user by id=%s", id), e);
        }
        return userBean;
    }

    @PreAuthorize("#username == authentication.principal.username or hasRole('ROLE_ADMIN')")
    public UserBeanExpanded getUserProfile(String username, int page, int maxResult) {
        UserBeanExpanded userExp = null;
        try {
            UserBean user = userDOToBean(userDao.findByUsername(username));
            List<TicketBean> tickets = ticketService.getUserTicketsAfterNow(user, page, maxResult);

            userExp = new UserBeanExpanded();
            userExp.setUser(user);
            userExp.setTickets(tickets);
        } catch (Exception e) {
            LOG.error(String.format("Failed to get user's profile by username=%s", username), e);
        }
        return userExp;
    }

    public void validate(UserBean user, Map<String, String> errors) {
        if (userDao.findByUsername(user.getUsername()) != null)
            errors.put("uniqueUsername", "User with such username already exists");
    }

    public boolean isUniqueUsername(String username, int id) {
        return userDao.checkUniqueUsername(username, id);
    }

    public UserBean userDOToBean(UserDO userDO) {
        return beanMapper.userToBean(entityMapper.userToSO(userDO));
    }

    public UserDO userBeanToDO(UserBean userBean) {
        return entityMapper.userToDO(beanMapper.userToSO(userBean));
    }

    public List<UserBean> userDOListToBeanList(List<UserDO> userDOList) {
        return beanMapper.userListToBeanList(entityMapper.userListToSOList(userDOList));
    }

    @Autowired
    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}
