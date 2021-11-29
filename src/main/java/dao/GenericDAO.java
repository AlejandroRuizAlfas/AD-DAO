package dao;

import java.util.List;

public interface GenericDAO<Tipo> {
	Tipo findByPK(int id) throws Exception;
    
    List<Tipo> findAll() throws Exception;   
         
    boolean insert(Tipo t) throws Exception;
     
    boolean update(Tipo t) throws Exception;
     
    boolean delete(int id) throws Exception;
    boolean delete(Tipo t) throws Exception;
    
}
