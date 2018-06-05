//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jsq.util.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@MappedTypes({UUID.class})
public class UUIDTypeHandler extends BaseTypeHandler<UUID> {
    public UUIDTypeHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, UUID uuid, JdbcType jdbcType) throws SQLException {
        if(uuid != null) {
            preparedStatement.setString(i, uuid.toString());
        } else {
            preparedStatement.setString(i, (String)null);
        }

    }
    @Override
    public UUID getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return !StringUtils.isEmpty(resultSet.getString(s))?UUID.fromString(resultSet.getString(s)):null;
    }
    @Override
    public UUID getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return !StringUtils.isEmpty(resultSet.getString(i))?UUID.fromString(resultSet.getString(i)):null;
    }
    @Override
    public UUID getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return !StringUtils.isEmpty(callableStatement.getString(i))?UUID.fromString(callableStatement.getString(i)):null;
    }
}
