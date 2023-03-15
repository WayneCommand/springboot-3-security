package com.example.springboot3security.util;

import com.example.springboot3security.security.SecurityConst;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户工具类
 */
public class UserUtil {

    /**
     * 获取当前用户姓名
     *
     * @return 用户姓名
     */
    public static String getCurrentUserName() {
        throw new UnsupportedOperationException();
    }


    /**
     * 获取当前用户 ID
     *
     * @return 当前用户 ID
     */
    public static Long getCurrentUserId() {
        throw new UnsupportedOperationException();
    }


    /**
     * 获取当前用户信息
     * 手动获取 request 信息
     *
     * @param request 请求
     * @return userInfo
     */
    public static UserDetails getCurrentUser(HttpServletRequest request) {
        String token = SecurityUtil.getToken(request);

        // 如果获取不到 Token， 返回一个匿名用户
        if (!StringUtils.hasText(token)) {
            return User.builder()
                    .username(SecurityConst.PAYLOAD_USER_NAME_ANONYMOUS_USER)
                    .build();
        }

        return SecurityUtil.userDetails(token);
    }

    /**
     * 获取当前用户信息
     *
     * @return 当前用户信息
     */
    public static User getCurrentUserDetail() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    /**
     * 获取当前用户权限集合
     *
     * @return 当前用户权限集合
     */
    public static List<String> getCurrentUserAuthority() {
        User currentUserDetail = getCurrentUserDetail();


        Collection<GrantedAuthority> authorities =  currentUserDetail.getAuthorities();

        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }


}