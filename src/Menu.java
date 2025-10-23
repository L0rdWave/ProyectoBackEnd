import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Menu implements CRUD {
    private ArrayList<Producto> productos = new ArrayList<>();
    private ArrayList<Categoria> categorias = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private HashSet<Integer> idsProductosUsados = new HashSet<>();
    private HashSet<Integer> idsCategoriasUsadas = new HashSet<>();
    private final String DATA_DIR = "data";
    private final String IDS_PRODUCTOS_FILE = DATA_DIR + File.separator + "ids_productos.txt";
    private final String IDS_CATEGORIAS_FILE = DATA_DIR + File.separator + "ids_categorias.txt";

    public Menu() {
        // Crear carpeta data si no existe y cargar IDs usados
        File d = new File(DATA_DIR);
        if (!d.exists()) d.mkdirs();
        cargarIdsPersistentes();
    }

    // Helper para leer enteros de forma segura (evita InputMismatchException)
    private int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = sc.nextLine();
            try {
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    // Helper para leer double de forma segura
    private double leerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = sc.nextLine().trim();
            // permitir coma como separador decimal
            linea = linea.replace(',', '.');
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número (ej: 12.34).");
            }
        }
    }

    // Comprobar existencia de IDs
    private boolean idProductoExiste(int id) {
        if (idsProductosUsados.contains(id)) return true;
        for (Producto p : productos) {
            if (p.getId() == id) return true;
        }
        return false;
    }

    private boolean idCategoriaExiste(int id) {
        if (idsCategoriasUsadas.contains(id)) return true;
        for (Categoria c : categorias) {
            if (c.getId() == id) return true;
        }
        return false;
    }

    // Persistencia de IDs usados
    private void cargarIdsPersistentes() {
        cargarIdsDesdeArchivo(IDS_PRODUCTOS_FILE, idsProductosUsados);
        cargarIdsDesdeArchivo(IDS_CATEGORIAS_FILE, idsCategoriasUsadas);
    }

    private void cargarIdsDesdeArchivo(String path, HashSet<Integer> set) {
        File f = new File(path);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                try {
                    set.add(Integer.parseInt(linea));
                } catch (NumberFormatException e) {
                    // ignorar líneas inválidas
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudieron cargar los IDs persistentes desde " + path);
        }
    }

    private void guardarIdsPersistentes() {
        guardarIdsEnArchivo(IDS_PRODUCTOS_FILE, idsProductosUsados);
        guardarIdsEnArchivo(IDS_CATEGORIAS_FILE, idsCategoriasUsadas);
    }

    private void guardarIdsEnArchivo(String path, HashSet<Integer> set) {
        File f = new File(path);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, false))) {
            for (Integer id : set) {
                bw.write(id.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("No se pudieron guardar los IDs persistentes en " + path);
        }
    }

    // CRUD Productos
    @Override
    public void crear() {
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías. Cree una categoría primero.\n");
            return;
        }
        int id;
        while (true) {
            id = leerEntero("Ingrese ID del producto: ");
            if (!idProductoExiste(id)) break;
            System.out.println("ID ya existe. Por favor ingrese otro ID.");
        }
        System.out.print("Ingrese descripción: ");
        String desc = sc.nextLine();
        double precio = leerDouble("Ingrese precio: ");
        System.out.println("Seleccione categoría por ID:");
        listarCategorias();
        int catId = leerEntero("ID de categoría: ");
        Categoria cat = null;
        for (Categoria c : categorias) {
            if (c.getId() == catId) {
                cat = c;
                break;
            }
        }
        if (cat == null) {
            System.out.println("Categoría no encontrada. Producto no creado.\n");
            return;
        }
    productos.add(new Producto(id, desc, precio, cat));
    // Registrar ID como usado y persistir
    idsProductosUsados.add(id);
    guardarIdsPersistentes();
    System.out.println("Producto creado!\n");
    }

    @Override
    public void listar() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos.\n");
            return;
        }
        System.out.println("Listado de productos:");
        for (Producto p : productos) {
            p.mostrarInfo();
        }
        System.out.println();
    }

    @Override
    public void modificar() {
    int id = leerEntero("Ingrese ID del producto a modificar: ");
        for (Producto p : productos) {
            if (p.getId() == id) {
                System.out.print("Nueva descripción: ");
                p.setDescripcion(sc.nextLine());
                System.out.print("Nuevo precio: ");
                p.setPrecio(leerDouble("Nuevo precio: "));
                System.out.println("Seleccione nueva categoría por ID:");
                listarCategorias();
                int catId = sc.nextInt();
                sc.nextLine();
                for (Categoria c : categorias) {
                    if (c.getId() == catId) {
                        p.setCategoria(c);
                        break;
                    }
                }
                System.out.println("Producto modificado!\n");
                return;
            }
        }
        System.out.println("Producto no encontrado.\n");
    }

    @Override
    public void eliminar() {
    int id = leerEntero("Ingrese ID del producto a eliminar: ");
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == id) {
                productos.remove(i);
                System.out.println("Producto eliminado!\n");
                return;
            }
        }
        System.out.println("Producto no encontrado.\n");
    }

    // CRUD Categorías
    public void crearCategoria() {
        int id;
        while (true) {
            id = leerEntero("Ingrese ID de categoría: ");
            if (!idCategoriaExiste(id)) break;
            System.out.println("ID de categoría ya existe. Por favor ingrese otro ID.");
        }
        System.out.print("Ingrese nombre de categoría: ");
        String nombre = sc.nextLine();
    categorias.add(new Categoria(id, nombre));
    // Registrar ID de categoría como usado y persistir
    idsCategoriasUsadas.add(id);
    guardarIdsPersistentes();
    System.out.println("Categoría creada!\n");
    }

    public void listarCategorias() {
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías.\n");
            return;
        }
        System.out.println("Listado de categorías:");
        for (Categoria c : categorias) {
            c.mostrarInfo();
        }
    }

    public void modificarCategoria() {
    int id = leerEntero("Ingrese ID de la categoría a modificar: ");
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                System.out.print("Nuevo nombre: ");
                c.setNombre(sc.nextLine());
                System.out.println("Categoría modificada!\n");
                return;
            }
        }
        System.out.println("Categoría no encontrada.\n");
    }

    public void eliminarCategoria() {
    int id = leerEntero("Ingrese ID de la categoría a eliminar: ");
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId() == id) {
                categorias.remove(i);
                System.out.println("Categoría eliminada!\n");
                return;
            }
        }
        System.out.println("Categoría no encontrada.\n");
    }

    // Menú principal
    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("===== MENÚ PRINCIPAL =====");
            System.out.println("1. CRUD Productos");
            System.out.println("2. CRUD Categorías");
            System.out.println("0. Salir");
                opcion = leerEntero("Elija una opción: ");
            switch(opcion) {
                case 1: menuProductos(); break;
                case 2: menuCategorias(); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opción inválida!\n");
            }
        } while(opcion != 0);
    }

    private void menuProductos() {
        int opcion;
        do {
            System.out.println("===== CRUD PRODUCTOS =====");
            System.out.println("1. Crear");
            System.out.println("2. Listar");
            System.out.println("3. Modificar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            opcion = leerEntero("Elija una opción: ");
            switch(opcion) {
                case 1: crear(); break;
                case 2: listar(); break;
                case 3: modificar(); break;
                case 4: eliminar(); break;
                case 0: break;
                default: System.out.println("Opción inválida!\n");
            }
        } while(opcion != 0);
    }

    private void menuCategorias() {
        int opcion;
        do {
            System.out.println("===== CRUD CATEGORÍAS =====");
            System.out.println("1. Crear");
            System.out.println("2. Listar");
            System.out.println("3. Modificar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            opcion = leerEntero("Elija una opción: ");
            switch(opcion) {
                case 1: crearCategoria(); break;
                case 2: listarCategorias(); break;
                case 3: modificarCategoria(); break;
                case 4: eliminarCategoria(); break;
                case 0: break;
                default: System.out.println("Opción inválida!\n");
            }
        } while(opcion != 0);
    }
}