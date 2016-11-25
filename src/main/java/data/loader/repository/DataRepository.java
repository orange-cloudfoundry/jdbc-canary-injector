package data.loader.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import data.loader.domain.Data;

public interface DataRepository extends CrudRepository<Data, Integer> {

	List<Data> findByDate(Date date);

	List<Data> findByStr(String str);

	List<Data> findFirst2ByOrderByDateDesc();

}
