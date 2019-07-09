package com.modelUser;

import com.modelUser.Base;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ：Yimyl
 * @date ：Created in 2019/4/26 23:12
 * @description：
 * @modified By：
 * @version: $
 */
//@Table(name = "user", schema = "app_user")
@Table(name = "user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Proxy(lazy = false)
public class User extends Base implements Serializable {

    @Column(nullable = false, length = 20, unique = true, updatable = false)
    private String username;

    @Column(nullable = false, length = 20)
    private String password;
}
