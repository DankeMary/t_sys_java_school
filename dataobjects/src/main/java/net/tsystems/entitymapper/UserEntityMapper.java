package net.tsystems.entitymapper;

import net.tsystems.entities.UserDO;
import net.tsystems.serviceobject.UserSO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserEntityMapper {
    UserDO userToDO (UserSO uSO);
    UserSO userToSO (UserDO uDO);
    List<UserSO> userListToSOList (List<UserDO> uListDO);
    List<UserDO> userListToDOList (List<UserSO> uListSO);
}
