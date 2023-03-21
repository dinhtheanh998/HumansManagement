package Service.BaseService;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface IBase<T> {

    List<T> getAll();

    boolean add(Class<?> e) throws InstantiationException, IllegalAccessException, ParseException;

    boolean edit(String code);
    boolean delete();

}
