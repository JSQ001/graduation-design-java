package com.jsq.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jsq.entity.*;

import com.jsq.entity.Class;
import com.jsq.mapper.*;
import com.jsq.util.ConvertToUserDetails;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Service
@Slf4j
@Transactional
public class UserService extends BaseService<UserInfoMapper,User> implements UserDetailsService{


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

    @Autowired
    private UserDeptMapper userDeptMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserClassMapper userClassMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private MajorClassMapper majorClassMapper;

    @Autowired
    private MajorMapper majorMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.selectOne(new EntityWrapper<User>().eq("user_number",username));
        System.out.print(user);
        if (null == user) {
            throw new UsernameNotFoundException("Could not find the user '" + username + "'");
        }
        //设置系部名
        UserDept userDept = userDeptMapper.selectOne(UserDept.builder().userId(user.getId()).build());
        if(userDept!=null){
            user.setDeptName(departmentMapper.selectOne(Department.builder().enabled(true).id(user.getDeptId()).build()).getDeptName());
            user.setDeptId(userDept.getDeptId());
        }
        List<UserAuthority> userAuthorities = userAuthorityMapper.selectList(new EntityWrapper<UserAuthority>()
                .eq("userId", user.getId()));
        List<Long> authorityIds = userAuthorities.stream().map(UserAuthority::getAuthorityId).collect(Collectors.toList());
        List<Authority> roles = authorityMapper.selectBatchIds(authorityIds);

        //如果是学生，添加班级信息,专业信息
        roles.forEach(item->{
            if(item.getId()==3){
                UserClass userClass = userClassMapper.selectOne(UserClass.builder().userId(user.getId()).build());
                if(userClass!=null){
                    Class c = classMapper.selectById(userClass.getClassId());
                    user.setClassName(c.getClassName());
                    user.setClassId(userClass.getClassId());

                    Major m = majorMapper.selectById(majorClassMapper.selectOne(MajorClass.builder().classId(c.getId()).build()).getMajorId());
                    user.setMajorId(m.getId());
                    user.setMajorName(m.getMajorName());
                }
            }
        });

        ConvertToUserDetails convertToUserDetails = new ConvertToUserDetails(user,roles);
        return convertToUserDetails;
    }


    //新增用户
    public User insertUser(User user){
        user.setPassword(user.getUserNumber().toString());
        userInfoMapper.insert(user);
        userDeptMapper.insert(UserDept.builder().deptId(user.getDeptId()).userId(user.getId()).build());
        user.getRole().stream().map(item->UserAuthority.builder().authorityId(item).userId(user.getId()).build())
                .forEach(userAuthority -> userAuthorityMapper.insert(userAuthority));
        Long o = (Long) user.getRole().toArray()[0];
        if(o==3){
            userClassMapper.insert(UserClass.builder().userId(user.getId()).classId(user.getClassId()).build());
        }
        return user;
    }

    //获取所以教师
    public List<User> getAllTeacher(){
       List<Long> userIds = userAuthorityMapper.selectList(new EntityWrapper<UserAuthority>()
               .eq("authorityId",2))
               .stream().map(UserAuthority::getUserId).collect(Collectors.toList());
       return userInfoMapper.selectList(new EntityWrapper<User>().in("id",userIds));
    }

    //条件查询用户(分页)
    public List<User> getUserByOptions(Long deptId, Long classId, String nickName, String userNumber,boolean enabled, Page<User> page,String type){
        /**
         *  2表示教师，3表示学生
        * */
        List<Long> userIds = userAuthorityMapper.selectList(new EntityWrapper<UserAuthority>()
                .where("userId!=1")
                .eq("authorityId",type.equals("teacher") ? 2 : 3))
                .stream().map(UserAuthority::getUserId).collect(Collectors.toList());
        if(deptId!=null){
            userIds = userDeptMapper.selectList(new EntityWrapper<UserDept>()
                    .in("userId",userIds)
                    .eq("departmentId",deptId))
                    .stream().map(UserDept::getUserId).collect(Collectors.toList());
        }
        if(classId!=null){
            userIds = userClassMapper.selectList(new EntityWrapper<UserClass>()
                    .in("userId",userIds)
                    .eq("classId",classId))
                    .stream().map(UserClass::getUserId).collect(Collectors.toList());
        }

        return userIds.size()==0 ? new ArrayList<User>() :
                userInfoMapper.selectPage(page,new EntityWrapper<User>()
                .in("id",userIds)
                .eq("is_enabled",enabled)
                .eq(nickName!=null,"nickname",nickName)
                .eq(userNumber!=null,"user_number",userNumber)
                .orderBy("user_number"))
                .stream().map(user -> {
                    Long departmentId = null;
                    if(deptId!=null){
                        departmentId = deptId;
                    }else {

                        //Optional.ofNullable(a).orElse(b),如果a为null,整个值为b，否则为a
                        departmentId = Optional.ofNullable(userDeptMapper.selectOne(UserDept.builder().userId(user.getId()).build()))
                                .map(e->e).orElse(UserDept.builder().deptId(null).build()).getDeptId();
                    }
                    user.setDeptId(departmentId);
                    user.setDeptName(departmentId == null ? "admin":departmentMapper.selectById(departmentId).getDeptName());
                    user.setPassword(null);
                    user.setClassId(classId);
                    if(type.equals("student")){
                        Long cId = userClassMapper.selectOne(UserClass.builder().userId(user.getId()).build()).getClassId();
                        user.setClassId(cId);
                        user.setClassName(classMapper.selectById(cId).getClassName());
                    }
                    return user;
                }).collect(Collectors.toList());
    }



}
