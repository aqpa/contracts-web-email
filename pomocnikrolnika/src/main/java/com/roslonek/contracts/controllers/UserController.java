package com.roslonek.contracts.controllers;

import com.roslonek.contracts.entities.User;
import com.roslonek.contracts.entities.UserDetails;
import com.roslonek.contracts.repositories.UserDetailsRepository;
import com.roslonek.contracts.repositories.UserRepository;
import com.roslonek.contracts.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    EmailUtil emailUtil;


    @RequestMapping("/showReg")
    public String showRegistrationPage() {
        return "login/registerUser";
    }



    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user,@RequestParam("confirmedPassword") String confirmedPassword, ModelMap modelMap) {
        if (userRepository.findUser(user.getEmail())!= null) {
            modelMap.addAttribute("msg", "Istnieje już użytkownik o tym adresie email.");
            return "login/registerUser";
        }
        else{
        }
        if (user.getPassword().equals(confirmedPassword)) {
            userRepository.save(user);
            modelMap.addAttribute("user", user);

            String defaultPath = "C://Users//user//Desktop";

            modelMap.addAttribute("defaultPath", defaultPath);
            return "login/userDetails";

        } else {
            modelMap.addAttribute("msg", "Wprowadzone hasła nie są takie same.");
            return "login/registerUser";
        }
    }




    @RequestMapping(value = "/userDetails", method = RequestMethod.POST)
    public String userDetails(@ModelAttribute("userDetails") UserDetails userDetails) {


        userDetailsRepository.save(userDetails);
        return "login/login";


    }

    @RequestMapping("/showLog")
    public String showLoginPage() {
        return "login/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, ModelMap modelMap) {
if (userRepository.findByEmail(email) == null){
    modelMap.addAttribute("msg", "Nie ma takiego użytkownika");
    return "login/login";
}
        User user = userRepository.findByEmail(email);
        UserDetails userDetails = userDetailsRepository.findByUserId(user.getId());

        if (user != null && user.getPassword().equals(password)) {


            modelMap.addAttribute("userDetails", userDetails);

            return "menu";
        } else {
            modelMap.addAttribute("msg", "Niepoprawny email bądź hasło. Spróbuj ponownie");
            return "login/login";
        }

    }

    @RequestMapping("/showUpdateUserDetails")
    public String showUpdateUserDetails(@RequestParam("id") int id, ModelMap modelMap) {
        UserDetails userDetails = userDetailsRepository.findByUserId(id);
        modelMap.addAttribute("userDetails", userDetails);
        return "editUserDetails";


    }

    @RequestMapping("/editUserDetails")
    public String editUserDetails(@ModelAttribute("userDetails") UserDetails userDetails) {

        userDetailsRepository.save(userDetails);
        return "menu";
    }
    @RequestMapping("/showPassword")
    public String showPassword(@RequestParam("id") int id, ModelMap modelMap) {
        UserDetails userDetails = userDetailsRepository.findByUserId(id);
        modelMap.addAttribute("userDetails", userDetails);
        return "changePassword";
    }
    @RequestMapping("/changePassword")
    public String changePassword(@RequestParam("userId") int userId, @RequestParam("password") String password, ModelMap modelMap) {

        User user = userRepository.findById(userId).get();
        user.setPassword(password);

        userRepository.save(user);

        UserDetails userDetails = userDetailsRepository.findByUserId(user.getId());
        modelMap.addAttribute("userDetails", userDetails);
        return "menu";
    }

    @RequestMapping("/whatPassword")
    public String whatPassword() {

        return "login/passwordReminder";
    }

    @RequestMapping("/passwordReminder")
    public String passwordReminder(@RequestParam("email") String email, ModelMap modelMap) {

        if (userRepository.findByEmail(email) != null) {

            String password = userRepository.findByEmail(email).getPassword();

            emailUtil.sendEmail(email, "Oto hasło do portalu kreującego umowy", password);
            modelMap.addAttribute("msg", "hasło wysłane na maila");
            return "login/login";

        }

        else {
            modelMap.addAttribute("msg", "Nie ma takiego maila w bazie danych");
            return "login/login";
        }
        }
}

