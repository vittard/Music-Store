package manager;

import java.sql.SQLException;
import java.util.Collection;

public interface ModelManager <T>{

	public T doRetrieveByKey(String key) throws SQLException;

	public Collection<T> doRetrieveAll(String orderBy) throws SQLException;

	public void doSave(T t) throws SQLException;

	public void doUpdate(T t) throws SQLException;  

	public void doDelete(T t) throws SQLException;

}
