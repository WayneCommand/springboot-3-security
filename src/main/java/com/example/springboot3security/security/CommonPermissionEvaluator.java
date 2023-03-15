package com.example.springboot3security.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class CommonPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)){
            return false;
        }
        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();

        return hasPrivilege(authentication, targetType, permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if ((authentication == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(authentication, targetType.toUpperCase(), permission.toString());
    }

    /**
     * 校验权限
     * @param auth              当前已认证的用户
     * @param targetType        校验类型
     * @param permission        权限（区分大小写, 严格匹配）
     * @return                  是否放行
     */
    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
            // 如果是 API 访问 直接放行
            if (grantedAuth.getAuthority().contains(SecurityConst.API_FLAG)) {
                return true;
            }
            // 验证用户权限
            if (grantedAuth.getAuthority().contains(permission)) {
                return true;
            }
        }
        return false;
    }
}

