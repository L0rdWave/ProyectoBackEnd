public class Producto extends Articulo {
    private Categoria categoria;

    public Producto(int id, String descripcion, double precio, Categoria categoria) {
        super(id, descripcion, precio);
        this.categoria = categoria;
    }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public void mostrarInfo() {
        System.out.println("ID: " + getId() + " | Descripción: " + getDescripcion() + " | Precio: $" + getPrecio()
                + " | Categoría: " + categoria.getNombre());
    }
}