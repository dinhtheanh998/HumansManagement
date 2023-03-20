package Service.BaseService;

import java.util.List;

public interface IBase<T> {

    List<T> getAll();

    boolean add(Class<?> e) throws InstantiationException, IllegalAccessException;

    boolean edit();
    boolean delete();

}
