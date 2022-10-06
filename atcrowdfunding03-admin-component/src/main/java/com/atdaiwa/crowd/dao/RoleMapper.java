package com.atdaiwa.crowd.dao;

import com.atdaiwa.crowd.entity.Role;
import com.atdaiwa.crowd.entity.RoleExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    long countByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    int deleteByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    int insert(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    int insertSelective(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    List<Role> selectRoleByKeyword(String keyword);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    List<Role> selectByExample(RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    Role selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_role
     *
     * @mbg.generated Fri Mar 18 14:15:48 JST 2022
     */
    int updateByPrimaryKey(Role record);

    List<Role> selectAssignedRole(Integer adminId);

    List<Role> selectUnassignedRole(Integer adminId);
}