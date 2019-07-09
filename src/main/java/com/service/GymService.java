package com.service;

import com.modelGym.Gym;

import com.repositoryGym.GymRepository;
import com.modelGym.Gym;
import com.repositoryGym.GymRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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
//@Transactional
@CacheConfig(cacheNames = "Gym")
@Slf4j
public class GymService {
    @Autowired
    private GymRepository gymRepository;
    /*@Autowired
    @Qualifier("transactionManagerGym")
    private PlatformTransactionManager transactionManager;*/
    /**
     * @param ：queryBy：若为Integer，根据体育馆id查询；若为String，根据体育馆名称查询
     * @return ：查询到的体育馆
     * @author ：Yimyl
     * @date ：Created in 2019.4.27
     * @desc ：查询体育馆信息
     */
    @Cacheable
    public Gym query(Object queryBy) {
        Gym gym = null;
        try {
            if(queryBy instanceof String){
                gym = gymRepository.findByName((String) queryBy);
            }else if(queryBy instanceof Integer){
                Optional<Gym> optional = gymRepository.findById((Integer) queryBy);
                gym = optional.get();
            }
        } catch (Exception e) {
            log.error("com.service.GymService.query:", e);
        }
        return gym;

    }
    /**
     * @author     ：Yimyl
     * @date       ：Created in 2019.4.27
     * @desc       ：分页查询体育馆，按id排序
     * @param      ：
     * @return     ：查询页码的数据
     */
    public List<Gym> pageQuery(int page, int size){
        List<Gym> gymList = null;
        try{
            Pageable pageable = PageRequest.of(page-1,size, Sort.Direction.ASC ,"id");
            Page<Gym> pagedata = gymRepository.findAll(pageable);
            gymList = pagedata.getContent();

        } catch (Exception e) {
            log.error("bjtu.service.GymService.pageQuery :", e);
        }
        return gymList;
    }
    /**
     * @author     ：Yimyl
     * @date       ：Created in 2019.4.27
     * @desc       ：分页查询，按照sortBy排序
     * @param      ：page：页码
     *              size：每页大小
     *              sortBy：排序的依据，若为“id”则按照id排序，若为“name”则按照name排序
     * @return     ：查询页码的数据
     */
    public List<Gym> pageQuery(int page, int size, String sortBy){
        List<Gym> gymList = null;
        try{
            Pageable pageable = PageRequest.of(page-1,size, Sort.Direction.ASC ,sortBy);
            Page<Gym> pagedata = gymRepository.findAll(pageable);
            gymList = pagedata.getContent();

        } catch (Exception e) {
            log.error("bjtu.service.GymService.pageQuery :", e);
        }
        return gymList;
    }
    /**
     * @author     ：Yimyl
     * @date       ：Created in 2019.4.27
     * @desc       ：注册体育馆
     * @param      ：
     * @return     ：注册状态
     */
   /* @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")*/
   /* public String register(String name, String info) {
        try {
            if(isGymnameCanRegister(name) == false)return "Gym name has been register";
            Gym gym = Gym.builder().name(name).info(info).build();
            gymRepository.saveAndFlush(gym);
            return "register success";
        } catch (Exception e) {
            log.error("bjtu.service.GymService.register :", e);
            return "register fail";
        }
    }
    *//**
     * @author     ：Yimyl
     * @date       ：Created in 2019.4.27
     * @desc       ：修改体育馆信息
     * @param      ：
     * @return     ：修改状态
     *//*
//    @Transactional
    public String modifyInfo(Object modifyBy, String info) {
        try {
            if(modifyBy instanceof String){
                Gym gym = gymRepository.findByName((String) modifyBy);
                gym.setInfo(info);
                gymRepository.saveAndFlush(gym);
                return "modify success";
            } else if(modifyBy instanceof Integer){
                Optional<Gym> optional = gymRepository.findById((Integer) modifyBy);
                Gym gym = optional.get();
                gym.setInfo(info);
                gymRepository.save(gym);
                return "modify success";
            }
        } catch (Exception e) {
            log.error("bjtu.service.GymService.modifyInfo :", e);
        }
        return "modify error";
    }*/


    /**
     * @author     ：Yimyl
     * @date       ：Created in 2019.4.27
     * @desc       ：体育挂名称是否可用
     * @param      ：
     * @return     ：true：没注册，可用；false：已注册，不可用
     */
    public boolean isGymnameCanRegister(String name){
        try {
            Gym gym = gymRepository.findByName(name);
            log.info("gym : {}",gym);
            if (gym == null) {
                return true;
            }
        } catch (Exception e) {
            log.error("bjtu.service.GymService.isGymnameCanRegister :", e);
        }
        return false;
    }

}
