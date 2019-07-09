package com.modelGym;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author ：Yimyl
 * @date ：Created in 2019/4/26 23:12
 * @description：
 * @modified By：
 * @version: $
 */
//@Table(name = "gym", schema = "gym")
@Table(name = "gym")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Proxy(lazy = false)
@ToString(callSuper = true, exclude = "trainers")
@EqualsAndHashCode(callSuper = false)
public class Gym extends Base implements Serializable {

    @Column(nullable = false, length = 20, unique = true, updatable = false)
    private String name;

    @Column(nullable = false, length = 100)
    private String info;

    @OneToMany(targetEntity = Trainer.class, mappedBy = "gym",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Trainer> trainers;


}
