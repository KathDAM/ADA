/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package pruebasnb.jdbc_practica1;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import Domain.*;
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
    private static PedidoArticuloDAO pedidoArticuloDAO = new PedidoArticuloDAO();
    private static DireccionEnvioDAO direccionEnvioDAO = new DireccionEnvioDAO();

    private enum MenuGeneral {
        CLIENTE, PEDIDO, ARTICULO, FABRICA,DIRECCION, SALIR_MENU
    };

    private enum MenuCliente {
        MOSTRAR_INFORMACION_CLIENTE, AGREGAR_CLIENTE, ELIMINAR_CLIENTE, ACTUALIZAR_CLIENTE,LISTADO_PEDIDOS, SALIR_CLIENTE
    };

    private enum MenuPedido {
        MOSTRAR_INFORMACION_PEDIDO, AGREGAR_PEDIDO, ELIMINAR_PEDIDO, ACTUALIZAR_PEDIDO, SALIR_PEDIDO
    };

    private enum MenuArticulo {
        MOSTRAR_INFORMACION_ARTICULO, AGREGAR_ARTICULO, ELIMINAR_ARTICULO, ACTUALIZAR_ARTICULO, ARTICULOS_POR_ANYO, SALIR_ARTICULO
    };

    private enum MenuFabrica {
        MOSTRAR_INFORMACION_FABRICA, AGREGAR_FABRICA, ELIMINAR_FABRICA, ACTUALIZAR_FABRICA,ELIMINAR_FABRICAS_SIN_PEDIDOS, SALIR_FABRICA
    };

    private enum MenuDirecionEnvio {
        MOSTRAR_INFORMACION_DIRECIONES, AGREGAR_DIRECIONES, ELIMINAR_DIRECIONES, ACTUALIZAR_DIRECIONES, SALIR_DIRECIONES
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
                case LISTADO_PEDIDOS:
                    listadoPedidos();
                    break;
                case SALIR_CLIENTE:
                    System.out.println("Saliendo del menú de cliente...");
                    break;
            }
        } while (opcionCliente != MenuCliente.SALIR_CLIENTE);
    }

    private static void mostrarInformacionCliente() throws SQLException {
        List<Cliente> clientes = clienteDAO.seleccionar();
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private static void agregarCliente() throws SQLException {
        int id = solicitarInt("Ingrese el id del cliente: ");
        double saldo = solicitarDouble("Ingrese el saldo: ");
        double limiteCredito = solicitarDouble("Ingrese el límite de crédito: ");
        double descuento = solicitarDouble("Ingrese el descuento: ");
    
        Cliente cliente = new Cliente(id, saldo, limiteCredito, descuento);
        clienteDAO.insertar(cliente);
        System.out.println("Cliente agregado correctamente.");
    }
    
    private static void eliminarCliente() throws SQLException {
        int id = solicitarInt("Ingrese el id del cliente a eliminar: ");
        clienteDAO.eliminar(id);
        System.out.println("Cliente eliminado correctamente.");
    }
    
    private static void actualizarCliente() throws SQLException {
        int id = solicitarInt("Ingrese el id del cliente a actualizar: ");
        double saldo = solicitarDouble("Ingrese el nuevo saldo: ");
        double limiteCredito = solicitarDouble("Ingrese el nuevo límite de crédito: ");
        double descuento = solicitarDouble("Ingrese el nuevo descuento: ");
    
        Cliente cliente = new Cliente(id, saldo, limiteCredito, descuento);
        boolean actualizado = clienteDAO.actualizar(cliente);
    
        if (actualizado) {
            Cliente clienteActualizado = clienteDAO.obtenerClientePorId(id);
            System.out.println("Cliente actualizado correctamente: " + clienteActualizado);
        } else {
            System.out.println("No se pudo actualizar el cliente.");
        }
    }
    
    private static void listadoPedidos() throws SQLException {
        int idCliente = solicitarInt("Dime el ID del cliente para la consulta de pedidos: ");
        System.out.println("Lista de todos los pedidos del cliente ID " + idCliente + ":");
        double totalDescuentos = clienteDAO.totalPedidosClientes(idCliente);
        if (totalDescuentos > 0) {
            System.out.println("Total ahorrado con los descuentos: " + totalDescuentos);
        } else {
            System.out.println("No se encontraron descuentos para el cliente con ID " + idCliente + ".");
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
        List<Pedido> pedidos = pedidoDAO.seleccionar();
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }

    private static void agregarPedido() throws SQLException {
        int numero = solicitarInt("Ingrese el número del pedido: ");
        int numDireccion = solicitarInt("Ingrese la dirección (id numDireccion): ");
        int idCliente = solicitarInt("Ingrese el id del cliente: ");
    
        Pedido pedido = new Pedido(0, new java.sql.Timestamp(System.currentTimeMillis()), numero, numDireccion, idCliente);
        pedidoDAO.insertar(pedido);
        System.out.println("Pedido agregado correctamente.");
    }
    
    private static void eliminarPedido() throws SQLException {
        int idPedido = solicitarInt("Ingrese el id del pedido a eliminar: ");
        pedidoDAO.eliminar(idPedido);
        System.out.println("Pedido eliminado correctamente.");
    }
    
    private static void actualizarPedido() throws SQLException {
        int idPedido = solicitarInt("Ingrese el id del pedido a actualizar: ");
        int numero = solicitarInt("Ingrese el nuevo número del pedido: ");
        int numDireccion = solicitarInt("Ingrese la nueva dirección (id numDireccion): ");
        int idCliente = solicitarInt("Ingrese el id del cliente: ");
    
        Pedido pedido = new Pedido(idPedido, new java.sql.Timestamp(System.currentTimeMillis()), numero, numDireccion, idCliente);
        boolean actualizado = pedidoDAO.actualizar(pedido);
    
        if (actualizado) {
            Pedido pedidoActualizado = pedidoDAO.obtenerPedidoPorId(idPedido);
            System.out.println("Pedido actualizado correctamente: " + pedidoActualizado);
        } else {
            System.out.println("No se pudo actualizar el pedido.");
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
                case ARTICULOS_POR_ANYO:
                    totalArticulosPedidosPorAnyo();
                    break;
                case SALIR_ARTICULO:
                    System.out.println("Saliendo del menú de artículo...");
                    break;
            }
        } while (opcionArticulo != MenuArticulo.SALIR_ARTICULO);
    }

    private static void mostrarInformacionArticulo() throws SQLException {
        List<Articulo> articulos = articuloDAO.seleccionar();
        for (Articulo articulo : articulos) {
            System.out.println(articulo);
        }
    }

    private static void agregarArticulo() throws SQLException {
        int idArticulo = solicitarInt("Ingrese el id del artículo: ");
        String descripcion = solicitarString("Ingrese la descripción del artículo: ");
    
        Articulo articulo = new Articulo(idArticulo, descripcion);
        articuloDAO.insertar(articulo);
        System.out.println("Artículo agregado correctamente.");
    }
    
    private static void eliminarArticulo() throws SQLException {
        int idArticulo = solicitarInt("Ingrese el id del artículo a eliminar: ");
        articuloDAO.eliminar(idArticulo);
        System.out.println("Artículo eliminado correctamente.");
    }
    
    private static void actualizarArticulo() throws SQLException {
        int idArticulo = solicitarInt("Ingrese el id del artículo a actualizar: ");
        String descripcion = solicitarString("Ingrese la nueva descripción del artículo: ");
    
        Articulo articulo = new Articulo(idArticulo, descripcion);
        boolean actualizado = articuloDAO.actualizar(articulo);

        if (actualizado) {
            Articulo articuloActualizado = articuloDAO.obtenerArticuloPorId(idArticulo);
            System.out.println("Artículo actualizado correctamente: " + articuloActualizado);
        } else {
            System.out.println("No se pudo actualizar el artículo.");
        }
    }
    
    private static void totalArticulosPedidosPorAnyo() throws SQLException {
        int anyo = solicitarInt("Dime el año que quieres ver: ");
        pedidoDAO.calcularTotalArticulosPorAnyo(anyo);
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
            case ELIMINAR_FABRICAS_SIN_PEDIDOS:
                eliminarFabricasSinPedidos();
                break;
            case SALIR_FABRICA:
                System.out.println("Saliendo del menú de fábricas...");
                break;
        }
    } while (opcionFabrica != MenuFabrica.SALIR_FABRICA);
}

private static void mostrarInformacionFabrica() throws SQLException {
    List<Fabrica> fabricas = fabricaDAO.seleccionar();
    for (Fabrica fabrica : fabricas) {
        System.out.println(fabrica);
    }
}

private static void agregarFabrica() throws SQLException {
    int idFabrica = solicitarInt("Ingrese el id de la fábrica: ");
    String nombre = solicitarString("Ingrese el nombre de la fábrica: ");
    int totalArticulos = solicitarInt("Ingrese el total de artículos: ");

    Fabrica fabrica = new Fabrica(idFabrica, nombre, totalArticulos);
    fabricaDAO.insertar(fabrica);
    System.out.println("Fábrica agregada correctamente.");
}

private static void eliminarFabrica() throws SQLException {
    int idFabrica = solicitarInt("Ingrese el id de la fábrica a eliminar: ");
    fabricaDAO.eliminar(idFabrica);
    System.out.println("Fábrica eliminada correctamente.");
}

private static void actualizarFabrica() throws SQLException {
    int idFabrica = solicitarInt("Ingrese el id de la fábrica a actualizar: ");
    String nombre = solicitarString("Ingrese el nuevo nombre de la fábrica: ");
    int totalArticulos = solicitarInt("Ingrese el total de artículos: ");

    Fabrica fabrica = new Fabrica(idFabrica, nombre, totalArticulos);
    boolean actualizado = fabricaDAO.actualizar(fabrica);
    
    if (actualizado) {
        Fabrica fabricaActualizada = fabricaDAO.obtenerFabricaPorId(idFabrica);
        System.out.println("Fábrica actualizada correctamente: " + fabricaActualizada);
    } else {
        System.out.println("No se pudo actualizar la fábrica.");
    }
}

private static void eliminarFabricasSinPedidos() throws SQLException {
    fabricaDAO.eliminarFabricasQueNoTienenPedidos();
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
    List<DireccionEnvio> direcciones = direccionEnvioDAO.seleccionar();
    for (DireccionEnvio direccion : direcciones) {
        System.out.println(direccion);
    }
}

private static void agregarDireccion() throws SQLException {
    int numDireccion = solicitarInt("Ingrese el id de la dirección: ");
    int numero = solicitarInt("Ingrese el número de la dirección: ");
    String calle = solicitarString("Ingrese la calle: ");
    String comuna = solicitarString("Ingrese la comuna: ");
    String ciudad = solicitarString("Ingrese la ciudad: ");
    int idCliente = solicitarInt("Ingrese el id del cliente asociado: ");

    DireccionEnvio direccionEnvio = new DireccionEnvio(numDireccion, numero, calle, comuna, ciudad, idCliente);
    direccionEnvioDAO.insertar(direccionEnvio);
    System.out.println("Dirección agregada correctamente.");
}

private static void eliminarDireccion() throws SQLException {
    int idDireccion = solicitarInt("Ingrese el id de la dirección a eliminar: ");
    direccionEnvioDAO.eliminar(idDireccion);
    System.out.println("Dirección eliminada correctamente.");
}

private static void actualizarDireccion() throws SQLException {
    int numDireccion = solicitarInt("Ingrese el id de la dirección a actualizar: ");
    int numero = solicitarInt("Ingrese el nuevo número de la dirección: ");
    String calle = solicitarString("Ingrese la nueva calle: ");
    String comuna = solicitarString("Ingrese la nueva comuna: ");
    String ciudad = solicitarString("Ingrese la nueva ciudad: ");
    int idCliente = solicitarInt("Ingrese el id del cliente asociado: ");

    DireccionEnvio direccionEnvio = new DireccionEnvio(numDireccion, numero, calle, comuna, ciudad, idCliente);
    boolean actualizado = direccionEnvioDAO.actualizar(direccionEnvio);
    if (actualizado) {
        DireccionEnvio direccionActualizada = direccionEnvioDAO.obtenerDireccionPorId(numDireccion);
        System.out.println("Dirección actualizada correctamente: " + direccionActualizada);
    } else {
        System.out.println("No se pudo actualizar la dirección.");
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
                .append("\t4) FABRICA\n")
                .append("\t5) Salir del menú\n")
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
                .append("\t5) Listado Pedidos del cliente\n")
                .append("\t6) Salir del menú de clientes\n")
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
                .append("\t5) Mostrar articulos por año\n")
                .append("\t6) Salir del menú de articulos\n")
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
                .append("\t5) Eliminar Fabricas sin pedidos\n")
                .append("\t6) Salir del menú de la fábrica\n")
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
}