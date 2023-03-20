package Service.BaseService;

import Connection.MyConnection;
import DAO.BaseDAO.BaseDAO;
import jdk.jfr.Name;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Base<T> implements IBase<T> {

    private Class<T> tClass;
    private BaseDAO<T> _baseDAO;

    public Base(Class<T> tClass) {
        this.tClass = tClass;
        _baseDAO = new BaseDAO<>(tClass);
    }


    @Override
    public List<T> getAll() {
        return _baseDAO.getAll();
    }

    @Override
    public  boolean add() {
        return false;
    }

    @Override
    public boolean edit() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
