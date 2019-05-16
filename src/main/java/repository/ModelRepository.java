package repository;

import model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ModelRepository<T extends BaseModel> extends JpaRepository<T, Long> {

    public T findById(long id);
}
