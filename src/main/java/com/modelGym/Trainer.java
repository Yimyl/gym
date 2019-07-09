package com.modelGym;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ：Yimyl
 * @date ：Created in 2019/4/26 23:12
 * @description：
 * @modified By：
 * @version: $
 */
//@Table(name = "trainer", schema = "gym")
@Table(name = "trainer")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, exclude = "gym")
@EqualsAndHashCode(callSuper = false)
@Proxy(lazy = false)
public class Trainer extends Base  implements Serializable {

    @Column(nullable = false, length = 20, unique = false)
    private String name;

    @Column(nullable = false, length = 100)
    private String info;

    @ManyToOne(targetEntity = Gym.class,cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinColumn(name = "gym_id")
    private Gym gym;
}
