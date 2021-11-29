package vista;
import java.sql.SQLException;
import java.util.List;

import dao.ClienteDAOImpl;
import dao.ConexionBD;
import dao.GrupoDAOImpl;
import dao_artiuclos.ArticuloDAOImpl;
import modelo.Articulo;
import modelo.Cliente;
import modelo.Grupo;

public class App {

	public static void main(String[] args) {

		try {
			ClienteDAOImpl clidao = new ClienteDAOImpl();
			System.out.println("Cantdad de clientes: "+clidao.size());

			System.out.println("Existe el cliente?: "+clidao.exists(55));

			// LISTAR POR GRUPO

			ArticuloDAOImpl artdao = new ArticuloDAOImpl();
			GrupoDAOImpl grupdao = new GrupoDAOImpl();
			Grupo g = grupdao.findByPK(2);
			List<Articulo> articulos = artdao.findByGrupo(g);
			for (Articulo art : articulos){
				System.out.println(art);
			}
			System.out.println("---------------------------------------------------------------------------------");

			// FIND BY SAMPLE
			Cliente clienteEjemplo = new Cliente();
//			clienteEjemplo.setNombre("Lluis");
			clienteEjemplo.setNombre("sergiodao");
			List<Cliente> clientesSamples = clidao.findByExample(clienteEjemplo);
			for (Cliente cli : clientesSamples){
				System.out.println(cli);
			}
			System.out.println("---------------------------------------------------------------------------------");

			if (clidao.findByNombre("Diana Perez") != null){
				System.out.println("Encontrada diana perez");
			}else {
				System.out.println("No existe");
			}

			List<Cliente> clientes = clidao.findAll();
			for (Cliente cli : clientes) {
				System.out.println(cli);
			}
			Cliente cli = clidao.findByPK(4);
			if (cli != null) {
				System.out.println("Encontrado cli 4: " + cli);
			}

			// insertar
			Cliente cliInsertar = new Cliente("sergiodao", "direccion dao");
			if (clidao.insert(cliInsertar)) {
				System.out.println("Insertado!");
			}

			// actualizar
			Cliente cliModificar = clidao.findByPK(5);
			if (cliModificar != null) {
				cliModificar.setNombre("otro nombre");
				cliModificar.setDireccion("otra direccion");
				if (clidao.update(cliModificar)) {
					System.out.println("Cliente modificado!!");
				}
			}

			// eliminar
			if (clidao.delete(6)) {
				System.out.println("Cliente borrado!!");
			}
			Cliente cliEliminar = clidao.findByPK(7);
			if (cliEliminar != null) {
				if (clidao.delete(cliEliminar)) {
					System.out.println("Cliente borrado!!");
				}
			}
			
			clidao.cerrar();
			ConexionBD.cerrar();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		mainArticulos();

	}

	public static void mainArticulos(){
		try {
			ArticuloDAOImpl artdao = new ArticuloDAOImpl();

			if (artdao.findByNombre("Teclado Razer") != null){
				System.out.println("Encontrado teclado razer");
			}else {
				System.out.println("No existe");
			}

			List<Articulo> articulos = artdao.findAll();
			for (Articulo art : articulos) {
				System.out.println(art);
			}
			Articulo art = artdao.findByPK(4);
			if (art != null) {
				System.out.println("Encontrado cli 4: " + art);
			}

			// insertar
			GrupoDAOImpl grupoDAO = new GrupoDAOImpl();
			Grupo g = grupoDAO.findByPK(1);
			Articulo artInsertar = new Articulo("sergiodao",34.7,"ArtSer",g,20);
			if (artdao.insert(artInsertar)) {
				System.out.println("Insertado!" +artInsertar);
			}
			System.out.println("--------------------------------------------------------------------------------------------------------------------");
			if (artdao.insertGenKey(artInsertar) != null){
				System.out.println("Insertado!" +artInsertar);
			}
			System.out.println("--------------------------------------------------------------------------------------------------------------------");
			//Busvar articulos por clave
			art = artdao.findByPK(1);
			g = art.getGrupo();
			System.out.println(art);
			System.out.println(g);


			// actualizar
			Articulo artModificar = artdao.findByPK(5);
			if (artModificar != null) {
				artModificar.setNombre("otro nombre");
				artModificar.setPrecio(34.6);
				if (artdao.update(artModificar)) {
					System.out.println("Articulo modificado!!");
				}
			}

			// eliminar
			if (artdao.delete(6)) {
				System.out.println("Articulo borrado!!");
			}
			Articulo artDelete = artdao.findByPK(7);
			if (artDelete != null) {
				if (artdao.delete(artDelete)) {
					System.out.println("Articulo borrado!!");
				}
			}

			artdao.cerrar();
			ConexionBD.cerrar();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
