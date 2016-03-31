package web;

import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import business.controllers.CourtController;
import business.controllers.UserController;
import business.wrapper.CourtState;
import business.wrapper.UserWrapper;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@SessionAttributes("name")
public class CourtPresenter {
	
	@Autowired
    private ServletContext servletContext;

	@Autowired
    private CourtController courtController;

    public CourtPresenter() {

    }
    


    @RequestMapping(value = "/courts", method = RequestMethod.GET)
    public ModelAndView getCourts(Model model) {
    	ModelAndView modelAndView = new ModelAndView("/courtList");
        modelAndView.addObject("courts", courtController.showCourts());
        return modelAndView;
    }


    @RequestMapping(value = "/courts", method = RequestMethod.POST)
    public String createCourt(Model model) {
    	List<CourtState> courts = courtController.showCourts();
    	int biggestId = 0;
    	for (CourtState c:courts) {
    		if (c.getCourtId() > biggestId) biggestId = c.getCourtId();
    	}
    	courtController.createCourt(biggestId + 1);
    	return "redirect:/courts";
    }
    
    @RequestMapping(value = "/courts/{id}/changestatus", method = RequestMethod.POST)
    public String changeStatus(@PathVariable int id, Model model) {
    	CourtState court = courtController.getCourt(id);
    	courtController.changeCourtActivation(id, !court.isActive());
        return "redirect:/courts";
    }
    
   

}
