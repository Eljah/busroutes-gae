package com.appspot.repo;

        import com.appspot.model.BusNode;
        import org.springframework.data.jpa.repository.JpaRepository;

        import java.util.List;

/**
 * Created by eljah32 on 10/8/2017.
 */
public interface BusNodeRepository extends JpaRepository<BusNode, String> {
    List<BusNode> findTop1ByLatitude(double latitude);
    List<BusNode> findTop1ByLongitude(double longitude);
}
