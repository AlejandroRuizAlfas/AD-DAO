package dao;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Articulo;
import modelo.Cliente;
import modelo.Grupo;

/**
 *
 * @author sergio
 */
public class ClienteDAOImpl implements GenericDAO<Cliente> {

	final String SQLSELECTALL = "SELECT * FROM clientes";
	final String SQLSELECTCOUNT = "SELECT count(*) FROM clientes";
	final String SQLSELECTNOMBRE = "SELECT * FROM clientes WHERE nombre = ?";
	final String SQLSELECTPK = "SELECT * FROM clientes WHERE id = ?";
	final String SQLINSERT = "INSERT INTO clientes (nombre, direccion) VALUES (?, ?)";
	final String SQLUPDATE = "UPDATE clientes SET nombre = ?, direccion = ? WHERE id = ?";
	final String SQLDELETE = "DELETE FROM clientes WHERE id = ?";
	String SQLFINDSAMPLE = "SELECT * FROM clientes WHERE nombre like ? and direccion like ?";
	private final PreparedStatement pstSelectPK;
	private final PreparedStatement pstSelectCount;
	private final PreparedStatement pstSelectNombre;
	private final PreparedStatement pstSelectAll;
	private final PreparedStatement pstInsert;
	private final PreparedStatement pstUpdate;
	private final PreparedStatement pstDelete;
	private final PreparedStatement pstSelectSample;

	public ClienteDAOImpl() throws SQLException {
		Connection con = ConexionBD.getConexion();
		pstSelectPK = con.prepareStatement(SQLSELECTPK);
		pstSelectCount = con.prepareStatement(SQLSELECTCOUNT);
		pstSelectNombre = con.prepareStatement(SQLSELECTNOMBRE);
		pstSelectAll = con.prepareStatement(SQLSELECTALL);
		pstInsert = con.prepareStatement(SQLINSERT);
		pstUpdate = con.prepareStatement(SQLUPDATE);
		pstDelete = con.prepareStatement(SQLDELETE);
		pstSelectSample = con.prepareStatement(SQLFINDSAMPLE);
	}
	
	public void cerrar() throws SQLException {
		pstSelectPK.close();
		pstSelectAll.close();
		pstSelectCount.close();
		pstSelectNombre.close();
		pstInsert.close();
		pstUpdate.close();
		pstDelete.close();
	}

	public Cliente findByPK(int id) throws SQLException {
		Cliente c = null;
		pstSelectPK.setInt(1, id);
		ResultSet rs = pstSelectPK.executeQuery();
		if (rs.next()) {
			c = new Cliente(id, rs.getString("nombre"), rs.getString("direccion"));			
		}
		rs.close();
		return c;
	}

	public Cliente findByNombre(String nombre) throws SQLException {
		Cliente c = null;
		pstSelectNombre.setString(1,nombre);
		ResultSet rs = pstSelectNombre.executeQuery();
		if (rs.next()){
			c = new Cliente(rs.getInt(1), rs.getString("nombre"),rs.getString("direccion"));
		}
		rs.close();
		return c;
	}

	public List<Cliente> findAll() throws SQLException {
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		ResultSet rs = pstSelectAll.executeQuery();
		while (rs.next()) {
			listaClientes.add(new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("direccion")));
		}
		rs.close();
		return listaClientes;
	}

	public boolean insert(Cliente cliInsertar) throws SQLException {
		pstInsert.setString(1, cliInsertar.getNombre());
		pstInsert.setString(2, cliInsertar.getDireccion());
		int insertados = pstInsert.executeUpdate();
		return (insertados == 1);
	}

	public boolean update(Cliente cliActualizar) throws SQLException {
		pstUpdate.setString(1, cliActualizar.getNombre());
		pstUpdate.setString(2, cliActualizar.getDireccion());
		pstUpdate.setInt(3, cliActualizar.getId());
		int actualizados = pstUpdate.executeUpdate();
		return (actualizados == 1);
	}

	public boolean delete(int id) throws SQLException {
		pstDelete.setInt(1, id);
		int borrados = pstDelete.executeUpdate();
		return (borrados == 1);
	}

	public boolean delete(Cliente cliEliminar) throws SQLException {
		return this.delete(cliEliminar.getId());
	}

	public int size() throws SQLException {
		ResultSet rs = pstSelectCount.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	public boolean exists(int id) throws SQLException {
		pstSelectPK.setInt(1,id);
		ResultSet rs = pstSelectPK.executeQuery();
		return rs.next();

//		if (findByPK(id) != null){
//			return true;
//		}
//		return false;
	}

	public List<Cliente> findByExample(Cliente clienteEjemplo) throws SQLException {
		List<Cliente> clientes = new ArrayList<>();
		if (clienteEjemplo.getNombre() != null){
			String nombre = "%"+clienteEjemplo.getNombre()+"%";
			pstSelectSample.setString(1,nombre);
		}else{
			pstSelectSample.setString(1,"%");
		}
		if (clienteEjemplo.getDireccion() != null){
			String dir = "%"+clienteEjemplo.getDireccion()+"%";
			pstSelectSample.setString(2,dir);
		}else {
			pstSelectSample.setString(2,"%");
		}
		ResultSet rs = pstSelectSample.executeQuery();
		while (rs.next()) {
			clientes.add(new Cliente(rs.getInt(1),rs.getString("nombre"),rs.getString("direccion")));
		}
		rs.close();
		return clientes;
	}
}
