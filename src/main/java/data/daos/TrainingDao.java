package data.daos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import data.entities.Training;

public interface TrainingDao extends JpaRepository<Training, Integer>, TrainingDaoExtended {
	
	@Query("select b from Training b where b.startDate between ?1 and ?2")
	  List<Training> findByDatesBetween(Calendar startDate, Calendar dateFinish);
}
