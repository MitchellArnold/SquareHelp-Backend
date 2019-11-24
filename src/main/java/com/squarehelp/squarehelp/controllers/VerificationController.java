package com.squarehelp.squarehelp.controllers;

import com.squarehelp.squarehelp.models.SmokerInfo;
import com.squarehelp.squarehelp.models.User;
import com.squarehelp.squarehelp.models.Verification;
import com.squarehelp.squarehelp.repositories.SmokerInfoRepository;
import com.squarehelp.squarehelp.repositories.UserRepository;
import com.squarehelp.squarehelp.repositories.VerificationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.squarehelp.squarehelp.util.Calculator.calcMoneySaved;

@Controller
public class VerificationController {
    private final SmokerInfoRepository smokeDao;
    private final UserRepository userDao;
    private final VerificationRepository veriDao;

    public VerificationController(VerificationRepository veriDao, SmokerInfoRepository smokeDao, UserRepository userDao) {
        this.veriDao = veriDao;
        this.smokeDao = smokeDao;
        this.userDao = userDao;
    }

    @GetMapping("/verification/{user_id}")
    public String getVerificationsView(Model model, @PathVariable long user_id) {
        SmokerInfo smokerInfo = smokeDao.getOne(user_id);
        int moneySaved = calcMoneySaved(smokerInfo.getCost_of_cigs_saved(), smokerInfo.getTotal_days_smoke_free());

        // Get all verification requests
        List<Verification> temp = veriDao.findAllByOriginator_user_id(user_id);

        model.addAttribute("verifications", temp);
        model.addAttribute("users", userDao.getOne(user_id));
        model.addAttribute("smoke", smokerInfo);
        model.addAttribute("moneySaved", moneySaved);

        return "verification";
    }

    // The page to host the 'search' route
    @GetMapping("/verification/{user_id}/form")
    public String getVerificationForm(Model model, @PathVariable long user_id) {
        SmokerInfo smokerInfo = smokeDao.getOne(user_id);
        int moneySaved = calcMoneySaved(smokerInfo.getCost_of_cigs_saved(), smokerInfo.getTotal_days_smoke_free());

        model.addAttribute("users", userDao.getOne(user_id));
        model.addAttribute("smoke", smokerInfo);
        model.addAttribute("moneySaved", moneySaved);
        return "verification-form";
    }

    // For finding usernames of recipients of verification form
    @RequestMapping("/search")
    @ResponseBody
    public List<User> sendMatchingUser(@RequestParam String username){
        System.out.println(userDao.findByUsernameContaining(username));
        return userDao.findByUsernameContaining(username);
    }

    // Actual page to create the form with recipient selected
    @GetMapping("/verification/{user_id}/form/send/{recip}")
    public String getVerificationFormSend(Model model, @PathVariable long user_id, @PathVariable long recip) {
        SmokerInfo smokerInfo = smokeDao.getOne(user_id);
        int moneySaved = calcMoneySaved(smokerInfo.getCost_of_cigs_saved(), smokerInfo.getTotal_days_smoke_free());

        User ru = userDao.findUserById(recip);

        model.addAttribute("recipient", ru);
        model.addAttribute("users", userDao.getOne(user_id));
        model.addAttribute("smoke", smokerInfo);
        model.addAttribute("moneySaved", moneySaved);
        return "verification-form-create";
    }

    // Generate the form and send
    @PostMapping("/verification/{user_id}/form/send/{recip}")
    public String postVerificationFormSend(Model model, @PathVariable long user_id, @PathVariable long recip, @RequestParam String date) {
        SmokerInfo smokerInfo = smokeDao.getOne(user_id);
        int moneySaved = calcMoneySaved(smokerInfo.getCost_of_cigs_saved(), smokerInfo.getTotal_days_smoke_free());

        // Find recipients username (ru)
        User ru = userDao.findUserById(recip);

        // Convert user id to int for constructor
        int uid = Integer.parseInt(String.valueOf(user_id));

        // Create verification and notification
        Verification v = new Verification(uid, ru.getUsername(), date, 1, false);

        veriDao.save(v);

        model.addAttribute("smoke", smokerInfo);
        model.addAttribute("moneySaved", moneySaved);
        return "redirect:/verification/" + user_id;
    }

}