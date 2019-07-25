package com.kdgc.hand.mapper;

import com.kdgc.hand.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
public interface UserMapper {

    Map<String, Object> findUserById(@Param("id") int id);

    List<User> findUserList();

}
