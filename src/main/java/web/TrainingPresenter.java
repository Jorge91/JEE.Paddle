package web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import data.entities.Court;
import data.entities.User;
import business.controllers.CourtController;
import business.controllers.TrainingController;
import business.controllers.UserController;
import business.wrapper.CourtState;
import business.wrapper.TrainingWrapper;
import business.wrapper.UserWrapper;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@SessionAttributes("name")
public class TrainingPresenter {
	
	@Autowired
    private ServletContext servletContext;

	@Autowired
    private TrainingController trainingController;
	
	@Autowired
    private CourtController courtController;
	
	@Autowired
    private UserController userController;


    public TrainingPresenter() {

    }
    


    @RequestMapping(value = "/trainings", method = RequestMethod.GET)
    public ModelAndView getCourts(Model model) {
    	ModelAndView modelAndView = new ModelAndView("/trainingList");
        modelAndView.addObject("trainings", trainingController.getTrainings());
        return modelAndView;
    }

    @RequestMapping(value = "/create-training", method = RequestMethod.GET)
    public String createTraining(Model model) {
    	TrainingForm trainingform = new TrainingForm();
    	model.addAttribute("training", trainingform);
    	return "/createTraining";
    }
    

    @RequestMapping(value = "/create-training", method = RequestMethod.POST)
    public String createUserSubmit(@ModelAttribute(value = "training") TrainingForm trainingForm, BindingResult bindingResult, Model model) throws ParseException {
        if (!bindingResult.hasErrors()) {
        	Court court = courtController.getCourtInstance(trainingForm.getCourt());
            User trainer = userController.getUserInstance(trainingForm.getTrainerName());
            
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Calendar startDate  = Calendar.getInstance();
            startDate.setTime(df.parse(trainingForm.getStartDate()));
            
            TrainingWrapper trainingWrapper = new TrainingWrapper();
            trainingWrapper.setCourt(court);
            trainingWrapper.setStartDate(startDate);
            trainingWrapper.setTrainer(trainer);
            if (trainingController.createTraining(trainingWrapper)) {
                return "redirect:/trainings";
            } else {
            	return "/createTraining";
            }
        }
        return "/createTraining";
    }
    /*
    @RequestMapping(value = "/create-training", method = RequestMethod.POST)
    public String createTraining(HttpServletRequest request, BindingResult bindingResult, Model model) throws ParseException{  
        Court court = courtController.getCourtInstance(Integer.parseInt(request.getParameter("courtId")));
        User trainer = userController.getUserInstance(request.getParameter("trainerName"));
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar startDate  = Calendar.getInstance();
        startDate.setTime(df.parse(request.getParameter("startDate")));
        
        TrainingWrapper trainingWrapper = new TrainingWrapper();
        trainingWrapper.setCourt(court);
        trainingWrapper.setStartDate(startDate);
        trainingWrapper.setTrainer(trainer);
        if (trainingController.createTraining(trainingWrapper)) {
            return "redirect:/trainings";
        } else {
        	return "/createTraining";
        }
    
        
    }*/
    
   

}
