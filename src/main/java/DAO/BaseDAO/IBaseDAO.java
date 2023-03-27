package DAO.BaseDAO;

import java.util.List;
import java.util.UUID;

public interface IBaseDAO<T> {

     boolean add(T t, boolean isUpdate);
     boolean update(T t);
     boolean delete(String code);
     T get(UUID id);

     T getByCode(String code);
     List<T> getAll();

    boolean getByEmail(String email);

    boolean getByPhone(String phone);
}
