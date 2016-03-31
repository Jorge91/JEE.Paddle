package web;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import business.controllers.UserController;
import business.wrapper.UserWrapper;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@SessionAttributes("name")
public class UserPresenter {
	
	@Autowired
    private ServletContext servletContext;

	@Autowired
    private UserController userController;

    public UserPresenter() {

    }
    
    @RequestMapping("/home")
    public String home(Model model) {
        return "/home";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getUsers(Model model) {
    	ModelAndView modelAndView = new ModelAndView("/userList");
        modelAndView.addObject("users", userController.getUsers());
        return modelAndView;
    }

    @RequestMapping(value = "/create-user", method = RequestMethod.GET)
    public String createUser(Model model) {
    	model.addAttribute("userWrapper", new UserWrapper());
        return "/createUser";
    }

    @RequestMapping(value = "/create-user", method = RequestMethod.POST)
    public String createUserSubmit(@Valid UserWrapper userWrapper, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            if (userController.registration(userWrapper)) {
                return "/registrationSuccess";
            } else {
                bindingResult.rejectValue("id", "error.user", "Usuario ya existente");
            }
        }
        return "/createUser";
    }
    
   

}
