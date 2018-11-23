package net.tsystems.beanmapper;

import net.tsystems.bean.UserBean;
import net.tsystems.serviceobject.UserSO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserBeanMapper {
    UserBean userToBean (UserSO uSO);
    UserSO userToSO (UserBean uBean);
    List<UserBean> userListToBeanList(List<UserSO> uListSO);
    List<UserSO> userListToSOList (List<UserBean> uListBean);
}
