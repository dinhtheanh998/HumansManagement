package DAO.BaseDAO;

import java.util.List;
import java.util.UUID;

public interface IBaseDAO<T> {

    public boolean add(T t);
    public boolean update(T t);
    public boolean delete(T t);
    public T get(UUID id);

    public T getByCode(String code);
    public List<T> getAll();

}
