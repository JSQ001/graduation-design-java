package com.jsq.util.typeHandler;


import com.jsq.enums.BudgetJournalStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@MappedTypes(BudgetJournalStatusEnum.class)
public class BaseEnumTypeHandler extends BaseTypeHandler<BudgetJournalStatusEnum> {


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BudgetJournalStatusEnum budgetJournalStatusEnum, JdbcType jdbcType) throws SQLException {
        if(budgetJournalStatusEnum != null){
            preparedStatement.setInt(i,budgetJournalStatusEnum.getID());
        }
    }

    @Override
    public BudgetJournalStatusEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return BudgetJournalStatusEnum.parse(resultSet.getInt(s));
    }

    @Override
    public BudgetJournalStatusEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return BudgetJournalStatusEnum.parse(resultSet.getInt(i));
    }

    @Override
    public BudgetJournalStatusEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return BudgetJournalStatusEnum.parse(callableStatement.getInt(i));
    }
}
