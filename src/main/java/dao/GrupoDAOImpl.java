package dao;

import modelo.Articulo;
import modelo.Grupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAOImpl implements GenericDAO<Grupo>{

    final String SQLSELECTALL = "SELECT * FROM grupos";
    //final String SQLSELECTNOMBRE = "SELECT * FROM grupos WHERE nombre = ?";
    final String SQLSELECTPK = "SELECT * FROM grupos WHERE id = ?";
    final String SQLINSERT = "INSERT INTO articulos (descripcion) VALUES (?)";
    final String SQLUPDATE = "UPDATE grupos SET descripcion = ? WHERE id = ?";
    final String SQLDELETE = "DELETE FROM grupos WHERE id = ?";

    private final PreparedStatement pstSelectPK;
    //private final PreparedStatement pstSelectNombre;
    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDelete;

    public GrupoDAOImpl() throws SQLException {
        Connection con = ConexionBD.getConexion();
        pstSelectPK = con.prepareStatement(SQLSELECTPK);
        pstSelectAll = con.prepareStatement(SQLSELECTALL);
        pstInsert = con.prepareStatement(SQLINSERT);
        pstUpdate = con.prepareStatement(SQLUPDATE);
        pstDelete = con.prepareStatement(SQLDELETE);
    }

    public void cerrar() throws SQLException {
        pstSelectPK.close();
        pstSelectAll.close();
        pstInsert.close();
        pstUpdate.close();
        pstDelete.close();
    }

    @Override
    public Grupo findByPK(int id) throws Exception {
        Grupo grupo = null;
        pstSelectPK.setInt(1,id);
        ResultSet rs = pstSelectPK.executeQuery();
        if (rs.next()){
            grupo = new Grupo(id,rs.getString("descripcion"));
        }
        rs.close();
        return grupo;
    }

    @Override
    public List<Grupo> findAll() throws Exception {
        List<Grupo> listaGrupos = new ArrayList<Grupo>();
        ResultSet rs = pstSelectAll.executeQuery();
        while (rs.next()) {
            listaGrupos.add(new Grupo(rs.getInt(1),rs.getString("descripcion")));
        }
        rs.close();
        return listaGrupos;
    }

    @Override
    public boolean insert(Grupo grpInsertar) throws Exception {
        pstInsert.setString(1, grpInsertar.getDescripcion());
        int insertados = pstInsert.executeUpdate();
        return (insertados == 1);
    }

    @Override
    public boolean update(Grupo grpUpdate) throws Exception {
        pstUpdate.setString(1, grpUpdate.getDescripcion());
        pstUpdate.setInt(2,grpUpdate.getId());
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
    public boolean delete(Grupo grpDelete) throws Exception {
        return this.delete(grpDelete.getId());
    }


}
