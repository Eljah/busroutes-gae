package com.appspot.repo;

        import com.appspot.model.BusStop;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.repository.CrudRepository;

/**
 * Created by eljah32 on 10/8/2017.
 */
public interface BusStopRepository extends JpaRepository<BusStop, String> {

}
