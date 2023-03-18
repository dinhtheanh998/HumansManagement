package Service.BaseService;

import java.util.List;

public interface IBase<T> {

    List<T> getAll();
    boolean add();
    boolean edit();
    boolean delete();

}