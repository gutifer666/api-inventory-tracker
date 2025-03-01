package com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.mappers;

import com.javiergutierrez.inventory_tracker_api.common.GenericMapper;
import com.javiergutierrez.inventory_tracker_api.modules.users.domain.User;
import com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserEntity, User> {
}
