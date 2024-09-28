package bookNow.Repository;

import bookNow.Model.ServiceCompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceCompanyModel, Long> {
    List<ServiceCompanyModel> findByCompanyId(Long companyId);
}
