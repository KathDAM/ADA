/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package pruebasnb.jdbc_practica1;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import Domain.*;
import Metodos.FabricasQueNoTienenPedidos;
import Metodos.PedidosYTotalDescuentoPorCliente;
import Metodos.TotalArticulosAnyo;
import datos.*;

/**
 *
 * @author catalvman
 */
public class JDBC_Practica1 {
    static Scanner lect = new Scanner(System.in);
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static PedidoDAO pedidoDAO = new PedidoDAO();
    private static ArticuloDAO articuloDAO = new ArticuloDAO();
    private static FabricaDAO fabricaDAO = new FabricaDAO();
    private static DireccionEnvioDAO direccionEnvioDAO = new DireccionEnvioDAO();
    private static FabricasQueNoTienenPedidos fabricasQueNoTienenPedidos = new FabricasQueNoTienenPedidos();
    private static TotalArticulosAnyo totalArticulosDAO = new TotalArticulosAnyo();
    private static PedidosYTotalDescuentoPorCliente pedidosDAO = new PedidosYTotalDescuentoPorCliente();

    private enum MenuGeneral {
        CLIENTE, PEDIDO, ARTICULO, FABRICA, DIRECCION, FUNCIONALIDADES, SALIR_MENU
    };

    private enum MenuCliente {
        MOSTRAR_INFORMACION_CLIENTE, AGREGAR_CLIENTE, ELIMINAR_CLIENTE, ACTUALIZAR_CLIENTE, SALIR_CLIENTE
    };

    private enum MenuPedido {
        MOSTRAR_INFORMACION_PEDIDO, AGREGAR_PEDIDO, ELIMINAR_PEDIDO, ACTUALIZAR_PEDIDO, SALIR_PEDIDO
    };

    private enum MenuArticulo {
        MOSTRAR_INFORMACION_ARTICULO, AGREGAR_ARTICULO, ELIMINAR_ARTICULO, ACTUALIZAR_ARTICULO, SALIR_ARTICULO
    };

    private enum MenuFabrica {
        MOSTRAR_INFORMACION_FABRICA, AGREGAR_FABRICA, ELIMINAR_FABRICA, ACTUALIZAR_FABRICA, SALIR_FABRICA
    };

    private enum MenuDirecionEnvio {
        MOSTRAR_INFORMACION_DIRECIONES, AGREGAR_DIRECIONES, ELIMINAR_DIRECIONES, ACTUALIZAR_DIRECIONES, SALIR_DIRECIONES
    };

    private enum MenuFunciones {
        PEDIDOS_Y_TOTAL_DESCUENTO, FABRICAS_SIN_PEDIDOS, TOTAL_ARTICULOS_ANYO, SALIR_FUNCIONES
    };

    public static void main(String[] args) throws SQLException {
        MenuGeneral opcionElegida = null;
        System.out.println("---------- Menú JDBC ----------");
        System.out.println("Bienvenido a la base de datos de Clientes");

        do {
            imprimirMenuGeneral();
            opcionElegida = opcionValidaMenu();

            switch (opcionElegida) {
                case CLIENTE:
                    gestionarClientes();
                    break;

                case PEDIDO:
                    gestionarPedidos();
                    break;

                case ARTICULO:
                    gestionarArticulos();
                    break;

                case FABRICA:
                    gestionarFabricas();
                    break;

                case DIRECCION:
                    gestionarDireciones();
                
                case FUNCIONALIDADES:
                    gestionarMetodos();

                case SALIR_MENU:
                    System.out.println("Cerrando el programa...");
                    break;
            }
        } while (opcionElegida != MenuGeneral.SALIR_MENU);

        System.out.println("\n\n¡Hasta Luego!\n\n");
        lect.close();
    }


//**************** FUNCIONES Y METODOS LANZADAS DESDE LA ELECCIÓN DEL MENÚ DE LA APLICACIÓN ********************
/************************************************************************************************/
    // CLIENTE
    private static void gestionarClientes() throws SQLException {
        MenuCliente opcionCliente = null;
        do {
            imprimirMenuCliente();
            opcionCliente = opcionValidaMenuCliente();

            switch (opcionCliente) {
                case MOSTRAR_INFORMACION_CLIENTE:
                    mostrarInformacionCliente();
                    break;
                case AGREGAR_CLIENTE:
                    agregarCliente();
                    break;
                case ELIMINAR_CLIENTE:
                    eliminarCliente();
                    break;
                case ACTUALIZAR_CLIENTE:
                    actualizarCliente();
                    break;
                case SALIR_CLIENTE:
                    System.out.println("Saliendo del menú de cliente...");
                    break;
            }
        } while (opcionCliente != MenuCliente.SALIR_CLIENTE);
    }

    private static void mostrarInformacionCliente() throws SQLException {
        try {
            List<Cliente> clientes = clienteDAO.obtenerTodosLosClientes(); 
            
            if (clientes.isEmpty()) {
                System.out.println("No hay clientes en la base de datos.");
            } else {
                for (Cliente cliente : clientes) {
                    System.out.println(cliente);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener información del cliente: " + e.getMessage());
        }
    }

    private static void agregarCliente() {
        int id = solicitarInt("Ingrese el id del cliente: ");
        try {
            if (clienteDAO.existeCliente(id)) {
                System.out.println("Error: Ya existe un cliente con ID " + id);
                return; 
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar cliente: " + e.getMessage());
            return; 
        }

        double saldo = solicitarDouble("Ingrese el saldo: ");
        double limiteCredito = solicitarDouble("Ingrese el límite de crédito: ");
        double descuento = solicitarDouble("Ingrese el descuento: ");

        Cliente cliente = new Cliente(id, saldo, limiteCredito, descuento);
        try {
            clienteDAO.insertar(cliente);
            System.out.println("Cliente agregado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
        }
    }
    
    private static void eliminarCliente() {
        int id = solicitarInt("Ingrese el id del cliente a eliminar: ");
    
        try {
            if (!clienteDAO.existeCliente(id)) {
                System.out.println("Error: No existe un cliente con ID " + id);
                return; 
            }
    
            clienteDAO.eliminar(id);
            System.out.println("Cliente eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
    
    private static void actualizarCliente() {
        int id = solicitarInt("Ingrese el id del cliente a actualizar: ");
        
        try {
            if (!clienteDAO.existeCliente(id)) {
                System.out.println("Error: No existe un cliente con ID " + id);
                return; 
            }

            double saldo = solicitarDouble("Ingrese el nuevo saldo: ");
            double limiteCredito = solicitarDouble("Ingrese el nuevo límite de crédito: ");
            double descuento = solicitarDouble("Ingrese el nuevo descuento: ");
    
            Cliente cliente = new Cliente(id, saldo, limiteCredito, descuento);
            clienteDAO.actualizar(cliente);
            System.out.println("Cliente actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
        }
    }
    
    
/************************************************************************************************/
// PEDIDO
    private static void gestionarPedidos() throws SQLException {
        MenuPedido opcionPedido = null;
        do {
            imprimirMenuPedido();
            opcionPedido = opcionValidaMenuPedido();

            switch (opcionPedido) {
                case MOSTRAR_INFORMACION_PEDIDO:
                    mostrarInformacionPedido();
                    break;
                case AGREGAR_PEDIDO:
                    agregarPedido();
                    break;
                case ELIMINAR_PEDIDO:
                    eliminarPedido();
                    break;
                case ACTUALIZAR_PEDIDO:
                    actualizarPedido();
                    break;
                case SALIR_PEDIDO:
                    System.out.println("Saliendo del menú de pedido...");
                    break;
            }
        } while (opcionPedido != MenuPedido.SALIR_PEDIDO);
    }

    private static void mostrarInformacionPedido() throws SQLException {
        List<Pedido> pedidos = pedidoDAO.obtenerTodosLosPedidos(); 

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos en la base de datos.");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
            }
        }
    }

    private static void agregarPedido() {
        int numDireccion = solicitarInt("Ingrese el ID de dirección: ");
        int idArticulo = solicitarInt("Ingrese el ID del artículo: ");
        int idCliente = solicitarInt("Ingrese el ID del cliente: ");
        
        try {
            if (!direccionEnvioDAO.existeDireccion(numDireccion)) { 
                System.out.println("Error: No existe una dirección con ID " + numDireccion);
                return; 
            }
    
            if (!articuloDAO.existeArticulo(idArticulo)) { 
                System.out.println("Error: No existe un artículo con ID " + idArticulo);
                return; 
            }

            if (!clienteDAO.existeCliente(idCliente)) {
                System.out.println("Error: No existe un cliente con ID " + idCliente);
                return; 
            }
    
            Pedido pedido = new Pedido(0, new java.sql.Timestamp(System.currentTimeMillis()), idArticulo, numDireccion, idCliente);
            pedidoDAO.insertar(pedido);
            System.out.println("Pedido agregado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar pedido: " + e.getMessage());
        }
    }
    
    
    private static void eliminarPedido() {
        int idPedido = solicitarInt("Ingrese el ID del pedido a eliminar: ");
        
        try {
            if (!pedidoDAO.existePedido(idPedido)) { 
                System.out.println("Error: No existe un pedido con ID " + idPedido);
                return; 
            }
    
            pedidoDAO.eliminar(idPedido);
            System.out.println("Pedido eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
    }
    
    
    private static void actualizarPedido() {
        int idPedido = solicitarInt("Ingrese el ID del pedido a actualizar: ");
        
        try {
            if (!pedidoDAO.existePedido(idPedido)) {
                System.out.println("Error: No existe un pedido con ID " + idPedido);
                return; 
            }
    
            int numDireccion = solicitarInt("Ingrese el nuevo ID de dirección: ");
            int idArticulo = solicitarInt("Ingrese el ID del artículo: ");
            int idCliente = solicitarInt("Ingrese el nuevo ID del cliente: ");
            
            if (!direccionEnvioDAO.existeDireccion(numDireccion)) {
                System.out.println("Error: No existe una dirección con ID " + numDireccion);
                return; 
            }
    
            if (!articuloDAO.existeArticulo(idArticulo)) {
                System.out.println("Error: No existe un artículo con ID " + idArticulo);
                return; 
            }
    
            if (!clienteDAO.existeCliente(idCliente)) {
                System.out.println("Error: No existe un cliente con ID " + idCliente);
                return; 
            }
    
            Pedido pedido = new Pedido(idPedido, new java.sql.Timestamp(System.currentTimeMillis()), idArticulo, numDireccion, idCliente);
            pedidoDAO.actualizar(pedido);
            System.out.println("Pedido actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
        }
    }
    

/************************************************************************************************/
// ARTICULO
    private static void gestionarArticulos() throws SQLException {
        MenuArticulo opcionArticulo = null;
        do {
            imprimirMenuArticulo();
            opcionArticulo = opcionValidaMenuArticulo();

            switch (opcionArticulo) {
                case MOSTRAR_INFORMACION_ARTICULO:
                    mostrarInformacionArticulo();
                    break;
                case AGREGAR_ARTICULO:
                    agregarArticulo();
                    break;
                case ELIMINAR_ARTICULO:
                    eliminarArticulo();
                    break;
                case ACTUALIZAR_ARTICULO:
                    actualizarArticulo();
                    break;
                case SALIR_ARTICULO:
                    System.out.println("Saliendo del menú de artículo...");
                    break;
            }
        } while (opcionArticulo != MenuArticulo.SALIR_ARTICULO);
    }

    private static void mostrarInformacionArticulo() throws SQLException {
        List<Articulo> articulos = articuloDAO.obtenerTodosLosArticulos(); 
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos en la base de datos.");
        } else {
            for (Articulo articulo : articulos) {
                System.out.println(articulo);
            }
        }
    }

    private static void agregarArticulo() {
        int idArticulo = solicitarInt("Ingrese el ID del artículo: ");
        
        try {
            if (articuloDAO.existeArticulo(idArticulo)) {
                System.out.println("Error: Ya existe un artículo con ID " + idArticulo);
                return; 
            }
    
            String descripcion = solicitarString("Ingrese la descripción del artículo: ");
            Articulo articulo = new Articulo(idArticulo, descripcion);
            
            articuloDAO.insertar(articulo);
            System.out.println("Artículo agregado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar artículo: " + e.getMessage());
        }
    }

    private static void eliminarArticulo() {
        int idArticulo = solicitarInt("Ingrese el ID del artículo a eliminar: ");
        
        try {
            if (!articuloDAO.existeArticulo(idArticulo)) {
                System.out.println("Error: No existe un artículo con ID " + idArticulo);
                return; 
            }
    
            articuloDAO.eliminar(idArticulo);
            System.out.println("Artículo eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar artículo: " + e.getMessage());
        }
    }
    
    
    private static void actualizarArticulo() {
        int idArticulo = solicitarInt("Ingrese el ID del artículo a actualizar: ");
        
        try {
            if (!articuloDAO.existeArticulo(idArticulo)) {
                System.out.println("Error: No existe un artículo con ID " + idArticulo);
                return; 
            }
    
            String descripcion = solicitarString("Ingrese la nueva descripción del artículo: ");
            Articulo articulo = new Articulo(idArticulo, descripcion);
    
            articuloDAO.actualizar(articulo);
            System.out.println("Artículo actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar artículo: " + e.getMessage());
        }
    }
    

/************************************************************************************************/
//FABRICA
private static void gestionarFabricas() throws SQLException {
    MenuFabrica opcionFabrica = null;
    do {
        imprimirMenuFabrica();
        opcionFabrica = opcionValidaMenuFabrica();

        switch (opcionFabrica) {
            case MOSTRAR_INFORMACION_FABRICA:
                mostrarInformacionFabrica();
                break;
            case AGREGAR_FABRICA:
                agregarFabrica();
                break;
            case ELIMINAR_FABRICA:
                eliminarFabrica();
                break;
            case ACTUALIZAR_FABRICA:
                actualizarFabrica();
                break;
            case SALIR_FABRICA:
                System.out.println("Saliendo del menú de fábricas...");
                break;
        }
    } while (opcionFabrica != MenuFabrica.SALIR_FABRICA);
}

private static void mostrarInformacionFabrica() throws SQLException {
    List<Fabrica> fabricas = fabricaDAO.obtenerTodasLasFabricas(); 

    if (fabricas.isEmpty()) {
        System.out.println("No hay fábricas en la base de datos.");
    } else {
        for (Fabrica fabrica : fabricas) {
            System.out.println(fabrica);
        }
    }
}

private static void agregarFabrica() {
    int idFabrica = solicitarInt("Ingrese el ID de la fábrica: ");
    
    try {
        if (fabricaDAO.existeFabrica(idFabrica)) { 
            System.out.println("Error: Ya existe una fábrica con ID " + idFabrica);
            return; 
        }

        String telefono = solicitarString("Ingrese el teléfono de la fábrica: ");
        Fabrica fabrica = new Fabrica(idFabrica, telefono);

        fabricaDAO.insertar(fabrica);
        System.out.println("Fábrica agregada correctamente.");
    } catch (SQLException e) {
        System.out.println("Error al agregar fábrica: " + e.getMessage());
    }
}


private static void eliminarFabrica() {
    int idFabrica = solicitarInt("Ingrese el ID de la fábrica a eliminar: ");
    
    try {
        if (!fabricaDAO.existeFabrica(idFabrica)) { 
            System.out.println("Error: No existe una fábrica con ID " + idFabrica);
            return; 
        }

        fabricaDAO.eliminar(idFabrica);
        System.out.println("Fábrica eliminada correctamente.");
    } catch (SQLException e) {
        System.out.println("Error al eliminar fábrica: " + e.getMessage());
    }
}


private static void actualizarFabrica() {
    int idFabrica = solicitarInt("Ingrese el ID de la fábrica a actualizar: ");
    
    try {
        if (!fabricaDAO.existeFabrica(idFabrica)) {
            System.out.println("Error: No existe una fábrica con ID " + idFabrica);
            return; 
        }

        String telefono = solicitarString("Ingrese el nuevo teléfono de la fábrica: ");
        Fabrica fabrica = new Fabrica(idFabrica, telefono);

        fabricaDAO.actualizar(fabrica);
        System.out.println("Fábrica actualizada correctamente.");
    } catch (SQLException e) {
        System.out.println("Error al actualizar fábrica: " + e.getMessage());
    }
}


/************************************************************************************************/
// DIRECCION
private static void gestionarDireciones() throws SQLException {
    MenuDirecionEnvio opcionDireccion = null;
    do {
        imprimirMenuDireccion();
        opcionDireccion = opcionValidaMenuDireccion();

        switch (opcionDireccion) {
            case MOSTRAR_INFORMACION_DIRECIONES:
                mostrarInformacionDireccion();
                break;
            case AGREGAR_DIRECIONES:
                agregarDireccion();
                break;
            case ELIMINAR_DIRECIONES:
                eliminarDireccion();
                break;
            case ACTUALIZAR_DIRECIONES:
                actualizarDireccion();
                break;
            case SALIR_DIRECIONES:
                System.out.println("Saliendo del menú de direcciones...");
                break;
        }
    } while (opcionDireccion != MenuDirecionEnvio.SALIR_DIRECIONES);
}

private static void mostrarInformacionDireccion() throws SQLException {
    List<DireccionEnvio> direcciones = direccionEnvioDAO.obtenerTodasLasDirecciones(); 

    if (direcciones.isEmpty()) {
        System.out.println("No hay direcciones en la base de datos.");
    } else {
        for (DireccionEnvio direccion : direcciones) {
            System.out.println(direccion);
        }
    }
}

private static void agregarDireccion() throws SQLException {
    int numero = solicitarInt("Ingrese el número de la dirección: "); 
    String calle = solicitarString("Ingrese la calle: ");
    String comuna = solicitarString("Ingrese la comuna: ");
    String ciudad = solicitarString("Ingrese la ciudad: ");
    int idCliente = solicitarInt("Ingrese el ID del cliente asociado: ");

    
    if (!clienteDAO.existeCliente(idCliente)) {
        System.out.println("Error: El ID del cliente no existe.");
        return; 
    }

    DireccionEnvio direccion = new DireccionEnvio(numero, calle, comuna, ciudad, idCliente); 
    
    try {
        direccionEnvioDAO.insertar(direccion);
        System.out.println("Dirección agregada correctamente.");
    } catch (SQLException e) {
        System.out.println("Error al agregar dirección: " + e.getMessage());
    }
}


private static void eliminarDireccion() {
    int idDireccion = solicitarInt("Ingrese el ID de la dirección a eliminar: ");
    
    try {
        if (!direccionEnvioDAO.existeDireccion(idDireccion)) { 
            System.out.println("Error: No existe una dirección con ID " + idDireccion);
            return; 
        }

        direccionEnvioDAO.eliminar(idDireccion);
        System.out.println("Dirección eliminada correctamente.");
    } catch (SQLException e) {
        System.out.println("Error al eliminar dirección: " + e.getMessage());
    }
}

private static void actualizarDireccion() {
    int idDireccion = solicitarInt("Ingrese el ID de la dirección a actualizar: ");
    
    try {
        if (!direccionEnvioDAO.existeDireccion(idDireccion)) {
            System.out.println("Error: No existe una dirección con ID " + idDireccion);
            return; 
        }

        int numero = solicitarInt("Ingrese el nuevo número de la dirección: ");
        String calle = solicitarString("Ingrese la nueva calle: ");
        String comuna = solicitarString("Ingrese la nueva comuna: ");
        String ciudad = solicitarString("Ingrese la nueva ciudad: ");
        int idCliente = solicitarInt("Ingrese el nuevo ID del cliente asociado: ");
        
        if (!clienteDAO.existeCliente(idCliente)) {
            System.out.println("Error: El ID del cliente no existe.");
            return; 
        }

        DireccionEnvio direccion = new DireccionEnvio(idDireccion, numero, calle, comuna, ciudad, idCliente);
        direccionEnvioDAO.actualizar(direccion);
        System.out.println("Dirección actualizada correctamente.");
    } catch (SQLException e) {
        System.out.println("Error al actualizar dirección: " + e.getMessage());
    }
}


/************************************************************************************************/
//METODOS
private static void gestionarMetodos() {
    MenuFunciones opcionFuncion = null;
    do {
        imprimirMenuFunciones();
        opcionFuncion = opcionValidaMenuFunciones();

        switch (opcionFuncion) {
            case PEDIDOS_Y_TOTAL_DESCUENTO:
                obtenerPedidosYTotalDescuento();
                break;

            case FABRICAS_SIN_PEDIDOS:
                eliminarFabricasSinPedidos();
                break;

            case TOTAL_ARTICULOS_ANYO:
                calcularTotalArticulosPorAnyo();
                break;

            case SALIR_FUNCIONES:
                System.out.println("Saliendo del menú de funcionalidades...");
                break;
        }
    } while (opcionFuncion != MenuFunciones.SALIR_FUNCIONES);
}

private static void eliminarFabricasSinPedidos() {
    try {
        boolean fabricasEliminadas = fabricasQueNoTienenPedidos.eliminarFabricasQueNoTienenPedidos();
        System.out.println("Fábricas eliminadas: " + fabricasEliminadas);
    } catch (SQLException e) {
        System.out.println("Error al eliminar fábricas: " + e.getMessage());
    }
}


private static void calcularTotalArticulosPorAnyo() {
    int year = solicitarInt("Ingrese el año para calcular el total de artículos: ");
    try {
        int totalArticulos = totalArticulosDAO.calcularTotalArticulosAnyo(year);
        System.out.println("Total de artículos pedidos en el año " + year + ": " + totalArticulos);
    } catch (SQLException e) {
        System.out.println("Error al calcular total de artículos: " + e.getMessage());
    }
}

private static void obtenerPedidosYTotalDescuento() {
    int idCliente = solicitarInt("Ingrese el ID del cliente: ");
    try {
        pedidosDAO.obtenerPedidosYTotalDescuentoPorCliente(idCliente);
    } catch (SQLException e) {
        System.out.println("Error al obtener pedidos y descuentos: " + e.getMessage());
    }
}


/************************************************************************************************/
//**************** MÉTODOS DE LECTURA DE DATOS VÁLIDOS POR TECLADO ********************

    private static MenuGeneral opcionValidaMenu() {
        try {
            int choiceInt = Integer.valueOf(lect.nextLine());
            return MenuGeneral.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción inválida... Inténtelo otra vez.");
            System.out.println("Opción: ");
            return opcionValidaMenu();
        }
    }

    private static MenuCliente opcionValidaMenuCliente() {
        try {
            int choiceInt = Integer.valueOf(lect.nextLine());
            return MenuCliente.values()[choiceInt-1];
        } catch (RuntimeException re) {
            System.out.println("Opción inválida... Inténtelo otra vez.");
            return opcionValidaMenuCliente();
        }
    }

    private static MenuPedido opcionValidaMenuPedido() {
        try {
            int choiceInt = Integer.valueOf(lect.nextLine());
            return MenuPedido.values()[choiceInt-1];
        } catch (RuntimeException re) {
            System.out.println("Opción inválida... Inténtelo otra vez.");
            return opcionValidaMenuPedido();
        }
    }

    private static MenuArticulo opcionValidaMenuArticulo() {
        try {
            int choiceInt = Integer.valueOf(lect.nextLine());
            return MenuArticulo.values()[choiceInt-1];
        } catch (RuntimeException re) {
            System.out.println("Opción inválida... Inténtelo otra vez.");
            return opcionValidaMenuArticulo();
        }
    }

    private static MenuFabrica opcionValidaMenuFabrica() {
        try {
            int choiceInt = Integer.valueOf(lect.nextLine());
            return MenuFabrica.values()[choiceInt-1];
        } catch (RuntimeException re) {
            System.out.println("Opción inválida... Inténtelo otra vez.");
            return opcionValidaMenuFabrica();
        }
    }

    private static MenuDirecionEnvio opcionValidaMenuDireccion() {
        try {
            int choiceInt = Integer.valueOf(lect.nextLine());
            return MenuDirecionEnvio.values()[choiceInt-1];
        } catch (RuntimeException re) {
            System.out.println("Opción inválida... Inténtelo otra vez.");
            return opcionValidaMenuDireccion();
        }
    }
    
    private static MenuFunciones opcionValidaMenuFunciones() {
        try {
            int choiceInt = Integer.valueOf(lect.nextLine());
            return MenuFunciones.values()[choiceInt - 1];
        } catch (RuntimeException re) {
            System.out.println("Opción inválida... Inténtelo otra vez.");
            return opcionValidaMenuFunciones();
        }
    }
    private static int solicitarInt(String mensaje) {
        int valor;
        while (true) {
            try {
                System.out.println(mensaje);
                valor = Integer.parseInt(lect.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número entero válido.");
            }
        }
        return valor;
    }
    
    private static double solicitarDouble(String mensaje) {
        double valor;
        while (true) {
            try {
                System.out.println(mensaje);
                valor = Double.parseDouble(lect.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número decimal válido.");
            }
        }
        return valor;
    }
    
    private static String solicitarString(String mensaje) {
        String valor;
        while (true) {
            System.out.println(mensaje);
            valor = lect.nextLine();
            if (!valor.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Error: El valor no puede estar vacío.");
            }
        }
        return valor;
    }

/************************************************************************************************/

//**************** MÉTODOS PARA IMPRIMIR LOS MENUS ********************
    private static void imprimirMenuGeneral() {
        StringBuilder sb = new StringBuilder()
                .append("\n\n¡BIENVENIDO AL MENÚ!:")
                .append("\n\nElije una opción:\n")
                .append("\t1) CLIENTE\n")
                .append("\t2) PEDIDO\n")
                .append("\t3) ARTICULO\n")
                .append("\t4) FÁBRICA\n")
                .append("\t5) DIRECCIONES\n")
                .append("\t6) FUNCIONALIDADES\n")
                .append("\t7) Salir del menú\n")
                .append("\nOpción: ");
        System.out.print(sb.toString());
    }

    private static void imprimirMenuCliente() {
        StringBuilder sb = new StringBuilder()
                .append("\n\n¡BIENVENIDO AL MENÚ DEL CLIENTE!:")
                .append("\n\nElije una opción:\n")
                .append("\t1) Mostrar información del cliente\n")
                .append("\t2) Agregar cliente\n")
                .append("\t3) Eliminar cliente\n")
                .append("\t4) Actualizar cliente\n")
                .append("\t5) Salir del menú de clientes\n")
                .append("\nOpción: ");
        System.out.print(sb.toString());
    }

    private static void imprimirMenuPedido() {
        StringBuilder sb = new StringBuilder()
                .append("\n\n¡BIENVENIDO AL MENÚ DE PEDIDOS!:")
                .append("\n\nElije una opción:\n")
                .append("\t1) Mostrar información del pedido\n")
                .append("\t2) Agregar pedido\n")
                .append("\t3) Eliminar pedido\n")
                .append("\t4) Actualizar pedido\n")
                .append("\t5) Salir del menú de pedidos\n")
                .append("\nOpción: ");
        System.out.print(sb.toString());
    }

    private static void imprimirMenuArticulo() {
        StringBuilder sb = new StringBuilder()
                .append("\n\n¡BIENVENIDO AL MENÚ DE ARTICULOS!:")
                .append("\n\nElije una opción:\n")
                .append("\t1) Mostrar información del articulo\n")
                .append("\t2) Agregar articulo\n")
                .append("\t3) Eliminar articulo\n")
                .append("\t4) Actualizar articulo\n")
                .append("\t5) Salir del menú de articulos\n")
                .append("\nOpción: ");
        System.out.print(sb.toString());
    }

    private static void imprimirMenuFabrica() {
        StringBuilder sb = new StringBuilder()
                .append("\n\n¡BIENVENIDO AL MENÚ DE LA FABRICA!:")
                .append("\n\nElije una opción:\n")
                .append("\t1) Mostrar información de la fábrica\n")
                .append("\t2) Agregar en la fábrica\n")
                .append("\t3) Eliminar en la fábrica\n")
                .append("\t4) Actualizar de la fábrica\n")
                .append("\t5) Salir del menú de la fábrica\n")
                .append("\nOpción: ");
        System.out.print(sb.toString());
    }

    private static void imprimirMenuDireccion() {
        StringBuilder sb = new StringBuilder()
                .append("\n\n¡BIENVENIDO AL MENÚ DE DIRECCIONES!:")
                .append("\n\nElije una opción:\n")
                .append("\t1) Mostrar información de direcciones\n")
                .append("\t2) Agregar dirección\n")
                .append("\t3) Eliminar dirección\n")
                .append("\t4) Actualizar dirección\n")
                .append("\t5) Salir del menú de direcciones\n")
                .append("\nOpción: ");
        System.out.print(sb.toString());
    }

    private static void imprimirMenuFunciones() {
        StringBuilder sb = new StringBuilder()
                .append("\n\n¡BIENVENIDO AL MENÚ DE FUNCIONALIDADES!:")
                .append("\n\nElige una opción:\n")
                .append("\t1) Obtener pedidos y total de descuento por cliente\n")
                .append("\t2) Eliminar fábricas que no tienen pedidos\n")
                .append("\t3) Calcular total de artículos por año\n")
                .append("\t4) Salir del menú de funcionalidades\n")
                .append("\nOpción: ");
        System.out.print(sb.toString());
    }  
}