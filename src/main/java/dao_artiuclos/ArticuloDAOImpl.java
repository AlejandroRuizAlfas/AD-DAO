package dao_artiuclos;

import dao.ConexionBD;
import dao.GenericDAO;
import dao.GrupoDAOImpl;
import modelo.Articulo;
import modelo.Cliente;
import modelo.Grupo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAOImpl implements GenericDAO<Articulo> {

    final String SQLSELECTALL = "SELECT * FROM articulos";
    final String SQLSELECTNOMBRE = "SELECT * FROM articulos WHERE nombre = ?";
    final String SQLSELECTPK = "SELECT * FROM articulos WHERE id = ?";
    final String SQLINSERT = "INSERT INTO articulos (nombre, precio, grupo, stock) VALUES (?,?,?, ?)";
    final String SQLUPDATE = "UPDATE articulos SET nombre = ?, precio = ? WHERE id = ?";
    final String SQLDELETE = "DELETE FROM articulos WHERE id = ?";
    final String SQLSELECTGRUPO = "SELECT * FROM articulos where grupo = ?";

    private final PreparedStatement pstSelectPK;
    private final PreparedStatement pstSelectNombre;
    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstInsertGenKey;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstSelectGrupo;

    private static GrupoDAOImpl grpdao;

    static {
        try {
            grpdao = new GrupoDAOImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArticuloDAOImpl() throws SQLException {
        Connection con = ConexionBD.getConexion();
        pstSelectPK = con.prepareStatement(SQLSELECTPK);
        pstSelectNombre = con.prepareStatement(SQLSELECTNOMBRE);
        pstSelectAll = con.prepareStatement(SQLSELECTALL);
        pstInsert = con.prepareStatement(SQLINSERT);
        pstInsertGenKey = con.prepareStatement(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
        pstUpdate = con.prepareStatement(SQLUPDATE);
        pstDelete = con.prepareStatement(SQLDELETE);
        pstSelectGrupo = con.prepareStatement(SQLSELECTGRUPO);
    }

    public void cerrar() throws SQLException {
        pstSelectPK.close();
        pstSelectAll.close();
        pstInsert.close();
        pstUpdate.close();
        pstDelete.close();
        pstSelectGrupo.close();
    }

    @Override
    public Articulo findByPK(int id) throws Exception {
        Articulo art = null;
        pstSelectPK.setInt(1,id);
        ResultSet rs = pstSelectPK.executeQuery();
        if (rs.next()){
            Grupo grp = grpdao.findByPK(rs.getInt("grupo"));
            art = new Articulo(id,rs.getString("nombre"),rs.getDouble("precio"),rs.getString("codigo"),grp,rs.getInt("stock"));
        }
        rs.close();
        return art;
    }

    public Articulo findByNombre(String nombre) throws Exception {
        Articulo art = null;
        pstSelectNombre.setString(1,nombre);
        ResultSet rs = pstSelectNombre.executeQuery();
        if (rs.next()){
            Grupo grp = grpdao.findByPK(rs.getInt("grupo"));
            art = new Articulo(rs.getInt(1),rs.getString("nombre"),rs.getDouble("precio"),rs.getString("codigo"),grp,rs.getInt("stock"));
        }
        rs.close();
        return art;
    }

    @Override
    public List<Articulo> findAll() throws Exception {
        List<Articulo> listaArticulos = new ArrayList<Articulo>();
        ResultSet rs = pstSelectAll.executeQuery();
        while (rs.next()) {
            Grupo grp = grpdao.findByPK(rs.getInt("grupo"));
            listaArticulos.add(new Articulo(rs.getInt(1),rs.getString("nombre"),rs.getDouble("precio"),rs.getString("codigo"),grp,rs.getInt("stock")));
        }
        rs.close();
        return listaArticulos;
    }

    @Override
    public boolean insert(Articulo artInsertar) throws Exception {
        pstInsert.setString(1, artInsertar.getNombre());
        pstInsert.setDouble(2, artInsertar.getPrecio());
        pstInsert.setInt(3,artInsertar.getGrupo().getId());
        pstInsert.setInt(4,artInsertar.getStock());
        int insertados = pstInsert.executeUpdate();
        return (insertados == 1);
    }

    public Articulo insertGenKey(Articulo artInsertar) throws SQLException{
        pstInsertGenKey.setString(1, artInsertar.getNombre());
        pstInsertGenKey.setDouble(2, artInsertar.getPrecio());
        pstInsertGenKey.setInt(3,artInsertar.getGrupo().getId());
        pstInsertGenKey.setInt(4,artInsertar.getStock());
        int insertados = pstInsertGenKey.executeUpdate();
        if (insertados == 1){
            ResultSet rsClaves = pstInsertGenKey.getGeneratedKeys();
            rsClaves.first();
            int id = rsClaves.getInt(1);
            System.out.println(id);
            rsClaves.close();
            artInsertar.setId(id);
            return artInsertar;
        }
       return null;
    }

    @Override
    public boolean update(Articulo artUpdate) throws Exception {
        pstUpdate.setString(1, artUpdate.getNombre());
        pstUpdate.setDouble(2, artUpdate.getPrecio());
        pstUpdate.setInt(3, artUpdate.getId());
        int actualizados = pstUpdate.executeUpdate();
        return (actualizados == 1);
    }

    @Override
    public boolean delete(int id) throws Exception {
        pstDelete.setInt(1, id);
        int borrados = pstDelete.executeUpdate();
        return (borrados == 1);
    }

    @Override
    public boolean delete(Articulo artDelete) throws Exception {
        return this.delete(artDelete.getId());
    }

    public List<Articulo> findByGrupo(Grupo grupo) throws Exception {
        List<Articulo> listaArticulos = new ArrayList<Articulo>();
        pstSelectGrupo.setInt(1,grupo.getId());
        ResultSet rs = pstSelectGrupo.executeQuery();
        while (rs.next()) {
            Grupo grp = grpdao.findByPK(rs.getInt("grupo"));
            listaArticulos.add(new Articulo(rs.getInt(1),rs.getString("nombre"),rs.getDouble("precio"),rs.getString("codigo"),grp,rs.getInt("stock")));
        }
        rs.close();
        return listaArticulos;
    }
}
