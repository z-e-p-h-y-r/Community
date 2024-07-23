package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.model.User;


@Mapper
public interface UserMapper {
    @Insert("insert into users (account_id,name,token,gmt_create,gmt_modified) values(#{accountID},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
