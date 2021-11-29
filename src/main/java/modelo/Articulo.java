package modelo;

public class Articulo {
    private int id;
    private String nombre;
    private double precio;
    private String codigo;
    private Grupo grupo;
    private int stock;

    public Articulo() {
    }

    public Articulo(int id, String nombre, double precio, String codigo, Grupo grupo, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.codigo = codigo;
        this.grupo = grupo;
        this.stock = stock;
    }

    public Articulo( String nombre, double precio, String codigo, Grupo grupo, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.codigo = codigo;
        this.grupo = grupo;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", codigo='" + codigo + '\'' +
                ", grupo=" + grupo.getDescripcion() +
                ", stock=" + stock +
                '}';
    }
}
