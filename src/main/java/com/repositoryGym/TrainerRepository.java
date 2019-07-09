package com.repositoryGym;

import com.modelGym.Trainer;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    List<Trainer> findByName(String name);
//    @Modifying
//    @Transactional
//    @Query(value = "delete from trainer t where t.id = ?1")
//    int deleteByTid(Integer id);

}
