package business.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.AlreadyExistTrainingException;
import business.api.exceptions.ErrorAddingStudentException;
import business.api.exceptions.ErrorDeletingStudentException;
import business.api.exceptions.InvalidTrainingIdException;
import business.api.exceptions.NoTrainingsInSystemException;
import business.controllers.TrainingController;
import business.wrapper.TrainingWrapper;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.TRAININGS)
public class TrainingResource {
	
	private TrainingController trainingController;

    @Autowired
    public void setUserController(TrainingController trainingController) {
        this.trainingController = trainingController;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void createTraining(@RequestBody TrainingWrapper trainingWrapper) throws AlreadyExistTrainingException { 
        if (!this.trainingController.createTraining(trainingWrapper)) {
            throw new AlreadyExistTrainingException();
        }
    }
    
    @RequestMapping(value="/{trainingId}", method = RequestMethod.DELETE)
    public void deleteTraining(@PathVariable int trainingId) throws InvalidTrainingIdException { 
    	if (!trainingController.deleteTraining(trainingId)) {
            throw new InvalidTrainingIdException();
        }
    }
    
    @RequestMapping(value="/{trainingId}" + Uris.STUDENT, method = RequestMethod.POST)
    public void addStudent(@PathVariable int trainingId, @RequestBody String studentUsername) throws ErrorAddingStudentException { 
    	if (!trainingController.addStudent(studentUsername, trainingId)) {
    		throw new ErrorAddingStudentException();
    	}	
    }
    
    @RequestMapping(value="{trainingId}" + Uris.STUDENT + "/" + "{studentId}", method = RequestMethod.DELETE)
    public void deleteStudent(@PathVariable("trainingId") int trainingId, @PathVariable("trainingId") int studentId) throws ErrorDeletingStudentException { 
    	if (!trainingController.deleteStudent(studentId, trainingId)) {
    		throw new ErrorDeletingStudentException();
    	}	
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<TrainingWrapper> getTrainings() throws NoTrainingsInSystemException {
    	 List<TrainingWrapper> trainingWrappers = trainingController.getTrainings();
    	 if (trainingWrappers.size() < 1) {
    		 throw new NoTrainingsInSystemException();
    	 } else {
    		return trainingWrappers; 
    	 } 
    }
    
    @RequestMapping(value="{trainingId}", method = RequestMethod.GET)
    public TrainingWrapper getTraining(@PathVariable("trainingId") int trainingId) throws InvalidTrainingIdException {
    	TrainingWrapper trainingWrapper = trainingController.getTraining(trainingId);
    	if (trainingWrapper == null) {
    		throw new InvalidTrainingIdException();
    	} else {
    		return trainingWrapper;
    	}
    }
    
    

   

}
