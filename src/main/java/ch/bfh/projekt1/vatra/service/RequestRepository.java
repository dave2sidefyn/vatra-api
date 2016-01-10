package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dave on 23.10.15.
 */
public interface RequestRepository extends CrudRepository<Request, String> {

	List<Request> findAllByApp(@Param("app") App app);
	
	List<Request> findAllByAppAndIdentify(@Param("app") App app, @Param("identify") String identify);
}
