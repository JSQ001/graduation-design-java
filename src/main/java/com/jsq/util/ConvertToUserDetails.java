package com.jsq.util;

import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.jsq.entity.User;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Created by cbc on 2017/12/26.
 */
@Getter
@ToString
public class ConvertToUserDetails extends User implements UserDetails,CredentialsContainer {

    private static final long serialVersionUID = -8838314723658446423L;

    private final Set<GrantedAuthority> authorities;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final boolean enabled;

    public ConvertToUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this(user, authorities, true, true, true, true);
    }

    public ConvertToUserDetails(User user,Collection<? extends GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        if (user != null
                && StringUtils.isNotBlank(user.getUserNumber())
                && StringUtils.isNotBlank(user.getPassword())) {
            setUserNumber(user.getUserNumber());
            setGender(user.getGender());
            setPassword(user.getPassword());
            setNickName(user.getNickName());
            setId(user.getId());
            setAvatar(user.getAvatar());
            setCreatedDate(user.getCreatedDate());
            setClassId(user.getClassId());
            setClassName(user.getClassName());
            setDeptId(user.getDeptId());
            setDeptName(user.getDeptName());
            setPhone(user.getPhone());
            setMajorId(user.getMajorId());
            setRole(user.getRole());
            setMajorName(user.getMajorName());
            setLastUpdatedBy(user.getLastUpdatedBy());
            setLastUpdatedDate(user.getLastUpdatedDate());
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = true;
            System.out.print(user.isLocked());
            this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }


    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet(new AuthorityComparator());
        Iterator var2 = authorities.iterator();

        while(var2.hasNext()) {
            GrantedAuthority grantedAuthority = (GrantedAuthority)var2.next();
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            System.out.print(grantedAuthority);
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    @Override
    public void eraseCredentials() {
        setPassword(null);
    }


    @Override
    public String getUsername() {
        return super.getUserNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //账户是否过期 false:过期
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

        private static final long serialVersionUID = 4496552229096679252L;

        private AuthorityComparator() {
        }

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            return g2.getAuthority() == null?-1:(g1.getAuthority() == null?1:g1.getAuthority().compareTo(g2.getAuthority()));
        }
    }

}
