package pl.sda.snake.repository;

import lombok.AllArgsConstructor;
import pl.sda.snake.hibernate.HibernateUtils;
import pl.sda.snake.model.Entity;

import java.util.List;

@AllArgsConstructor
public abstract class AbstractRepository <T extends Entity> {
    private Class<T> clazz;

    public T findById(Integer id) {
        return HibernateUtils.getInTransaction(session -> session.find(clazz, id));
    }

    public List<T> findAll() {
        return HibernateUtils.getInTransaction(session ->
                session.createQuery("select e from "+clazz.getSimpleName()+" e", clazz).getResultList());
    }


    public T save(T entity) {
        return HibernateUtils.getInTransaction(session -> {
            if (entity.getId() == null) {
                session.persist(entity);//trzeba mergowac bo id by sie nie uzupełniało
            }
            return (T) session.merge(entity);
        });
    }

    public void remove(T entity) {
        HibernateUtils.getInTransaction(session -> {
            session.delete(entity);
            return null;
        });
    }
}
