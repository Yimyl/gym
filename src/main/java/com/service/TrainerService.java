package com.service;

import com.repositoryGym.TrainerRepository;
import com.modelGym.Gym;
import com.modelGym.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ：Yimyl
 * @date ：Created in 2019/4/27 9:48
 * @description：
 * @modified By：
 * @version: $
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "Trainer")
public class TrainerService {
    @Autowired
    private TrainerRepository trainerRepository;

    /**
     * @param ：queryBy：若为Integer，根据教练id查询；若为String，根据教练姓名查询
     * @return ：查询到的教练(可能多个)
     * @author ：Yimyl
     * @date ：Created in 2019.4.27
     * @desc ：查询教练信息
     */
    @Cacheable
    public List<Trainer> query(Object queryBy) {
        List<Trainer> trainers = null;
        System.out.println("cacheinggggggggggggggggggggggggggggggg");
        try {
            if (queryBy instanceof String) {
                trainers = trainerRepository.findByName((String) queryBy);
            } else if (queryBy instanceof Integer) {
                Optional<Trainer> optional = trainerRepository.findById((Integer) queryBy);
                trainers = new ArrayList<>();
                trainers.add(optional.get());
            }
        } catch (Exception e) {
            log.error("bjtu.service.TrainerService.query:", e);
        }
        return trainers;
    }

    /**
     * @param ：page：页码 size：每页大小
     * @return ：该页码中的数据
     * @author ：Yimyl
     * @date ：Created in 2019.4.27
     * @desc ：分页查询，按id排序
     */
    @Cacheable
    public List<Trainer> pageQuery(int page, int size) {
        List<Trainer> trainerList = null;
        try {
            Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "id");
            Page<Trainer> pagedata = trainerRepository.findAll(pageable);
            trainerList = pagedata.getContent();

        } catch (Exception e) {
            log.error("bjtu.service.TrainerService.pageQuery :", e);
        }
        return trainerList;
    }

    /**
     * @param ：page：页码 size：每页大小
     *                 sortBy：排序的依据，若为“id”则按照id排序，若为“name”则按照name排序
     * @return ：该页码中的数据
     * @author ：Yimyl
     * @date ：Created in 2019.4.27
     * @desc ：分页查询，按id或name排序
     */
    @Cacheable
    public List<Trainer> pageQuery(int page, int size, String sortBy) {
        List<Trainer> trainerList = null;
        try {
            Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, sortBy);
            Page<Trainer> pagedata = trainerRepository.findAll(pageable);
            trainerList = pagedata.getContent();

        } catch (Exception e) {
            log.error("com.service.TrainerService.pageQuery :", e);
        }
        return trainerList;
    }

    /**
     * @param ：queryBy：健身馆信息（id 或name ）
     * @return ：查到的教练
     * @author ：Yimyl
     * @date ：Created in 2019.4.27
     * @desc ：分页查询健身馆中的教练
     */
    @Cacheable
    public List<Trainer> findTrainerOfGym(GymService gymService, Object queryBy, int page, int size) {
        List<Trainer> trainers = null;
        try {
            Gym gym = gymService.query(queryBy);
            if (gym != null) {
                trainers = gym.getTrainers();
                trainers = page(trainers, page, size);
            }
        } catch (Exception e) {
            log.error("com.service.TrainerService.findTrainerOfGym :", e);
        }
        return trainers;
    }

    /**
     * @param ：list：要分页的链表
     * @return ：对应页码的链表
     * @author ：Yimyl
     * @date ：Created in 2019.4.27
     * @desc ：对链表分页
     */
    private List<Trainer> page(List<Trainer> list, int page, int size) {
        List<Trainer> trainers = new ArrayList<>();
        int begin = (page - 1) * size;
        int end = begin + size;
        for (int i = begin; i < end && i < list.size(); i++) {
            trainers.add(list.get(i));
        }
        return trainers;
    }

    /**
     * @param ：
     * @return ：注册状态
     * @author ：Yimyl
     * @date ：Created in 2019.4.27
     * @desc ：注册教练
     */
    /*public String register(String name, String info, Gym gym) {
        try {
            Trainer trainer = Trainer.builder().name(name).info(info).gym(gym).build();
            trainerRepository.save(trainer);
            return "register success";
        } catch (Exception e) {
            log.error("com.service.TrainerService.register :", e);
            return "register fail";
        }
    }

    *//**
     * @param ：modifyBy：修改的教练信息，若为Integer，根据id修改；若为String，根据name修改
     * @return ：修改状态
     * @author ：Yimyl
     * @date ：Created in 2019.4.27
     * @desc ：修改教练信息
     *//*
    public String modifyInfo(Integer id, String info) {
        try {
            Optional<Trainer> optional = trainerRepository.findById((Integer) id);
            Trainer trainer = optional.get();
            trainer.setInfo(info);
            trainerRepository.save(trainer);
            return "modify success";
        } catch (Exception e) {
            log.error("bjtu.service.TrainerService.modifyInfo :", e);
        }
        return "modify error";
    }*/

    /**
     * @param ：
     * @return ：删除状态
     * @author ：Yimyl
     * @date ：Created in 2019.4.27
     * @desc ：删除教练
     */
    public String delete(Integer id) {
        try {
            trainerRepository.deleteById(id);
            return "delete success";
        } catch (Exception e) {
            //log.error("bjtu.service.TrainerService.delete :", e);
            return "delete fail";
        }


    }


}
