package com.xgs.hisystem.realm;

import com.xgs.hisystem.pojo.entity.RoleEntity;
import com.xgs.hisystem.pojo.entity.UserEntity;
import com.xgs.hisystem.repository.IRoleRespository;
import com.xgs.hisystem.repository.IUserRepository;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IRoleRespository iRoleRespository;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserEntity userEntity= (UserEntity) principalCollection.getPrimaryPrincipal();
        UserEntity user= iUserRepository.findByEmail(userEntity.getEmail());
        if (user==null){
            return null;
        }
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();

        List<RoleEntity> roleList = iRoleRespository.findByUserIdAndRoleStatus(user.getId());

        if (roleList != null && !roleList.isEmpty()) {
            roleList.forEach(role -> {
                authorizationInfo.addRole(role.getRole());
            });
        }

        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String email = (String) authenticationToken.getPrincipal(); //获取登录用户账号

        UserEntity user = iUserRepository.findByEmail(email);
        if (user == null) {
            throw new UnknownAccountException(
                    "no messages!"
            );
        }
        String realmName = getName();//当前realm对象的唯一名字，调用父类的getName()方法
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt()); //加密的盐值

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, realmName);
        return info;
    }

}


