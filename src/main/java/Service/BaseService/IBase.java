package Service.BaseService;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface IBase<T> {

    List<T> getAll();

    boolean add(Class<?> e,boolean isUpdate) throws InstantiationException, IllegalAccessException, ParseException;

    boolean edit(Class<?> e,String code);
    boolean delete();

}
