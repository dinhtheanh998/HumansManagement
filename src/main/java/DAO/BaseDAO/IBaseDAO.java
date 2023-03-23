package DAO.BaseDAO;

import java.util.List;
import java.util.UUID;

public interface IBaseDAO<T> {

    public boolean add(T t, boolean isUpdate);
    public boolean update(T t);
    public boolean delete(String code);
    public T get(UUID id);

    public T getByCode(String code);
    public List<T> getAll();

}
