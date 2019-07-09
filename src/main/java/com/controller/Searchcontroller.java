package com.controller;


import com.modelGym.Trainer;
import com.service.GymService;
import com.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class Searchcontroller {
    @Autowired
    TrainerService ts;
    @Autowired
    GymService gs;

    /*List<Gym> g1list=gs.*/

    @PostMapping("/search")
    public String gogogo(@RequestParam("gym") String gym,
                         @RequestParam("coach") String coach,
                         Model mod) {
        Object a;


        List<Trainer> glist = ts.pageQuery(1, 5000);


        if (glist == null)
            return "search";
        if (((gym == null || gym.equals("")) && (coach == null || coach.equals(""))))
            mod.addAttribute("hello", glist);
        if (!gym.equals("") && (coach == null || coach.equals(""))) {
            /*System.out.println(ts.toString());*/
            if (isNumeric(gym)){
                a=Integer.parseInt(gym);
            }
            else
                a=gym;
                List<Trainer> trainerOfGym = ts.findTrainerOfGym(gs, a, 1, 5000);

            if (trainerOfGym != null)
                mod.addAttribute("hello2", trainerOfGym);
            return "search2";
        }
        if (!gym.equals("") && !coach.equals("")) {
            if (isNumeric(gym)){
                a=Integer.parseInt(gym);
            }
            else
                a=gym;
            List<Trainer> glist4 = ts.findTrainerOfGym(gs, a, 1, 5000);
            LinkedList<Trainer> glist5 = new LinkedList<>();
            if (isNumeric(coach)){
                a=Integer.parseInt(coach);
            }
            else
                a=coach;
            List<Trainer> glist3 = ts.query(a);
            /*mod.addAttribute("hello",glist3);*/
            for (Trainer ts1 : glist3) {
                for (Trainer gs1 : glist4) {
                    if ((int) ts1.getId() == (int) gs1.getId())
                        glist5.add(ts1);
                    break;
                }
            }
            mod.addAttribute("hello", glist5);
        }
        if ((gym == null || gym.equals("")) && !coach.equals("")) {
            if (isNumeric(coach)){
                a=Integer.parseInt(coach);
            }
            else
                a=coach;
            List<Trainer> glist3 = ts.query(a);
            mod.addAttribute("hello", glist3);
        }

        return "search";
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
