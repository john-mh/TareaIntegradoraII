package org.integradora2.ui;

import org.integradora2.model.MercadoLibre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Main {

    public static final MercadoLibre ml = new MercadoLibre();
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu(){
        System.out.println("Bienvenido a Mercado Libre");
        System.out.println("1. Productos");
        System.out.println("2. Ordenes");
        System.out.println("3. Buscar");
        System.out.println("0. Salir");
        System.out.println("\n");

        int option = sc.nextInt();

        switch (option) {
            case 1 -> productMenu();
            case 2 -> orderMenu();
            case 3 -> searchMenu();
            case 0 -> System.out.println("\n Gracias por usar Mercado Libre");
            default -> {
                System.out.println("Opción invalida \n");
                mainMenu();
            }
        }
    }
    public static void productMenu(){
        System.out.println("1. Agregar producto");
        System.out.println("2. Eliminar producto");
        System.out.println("3. Mostrar productos");
        System.out.println("0. Regresar \n");

        int option = sc.nextInt();

        switch (option) {
            case 1 -> {
                System.out.println("Nombre del producto: ");
                String name = sc.next();
                System.out.println("Descripción del producto: ");
                String description = sc.next();
                System.out.println("Precio del producto: ");
                double price = sc.nextDouble();
                System.out.println("Cantidad disponible del producto: ");
                int availableQuantity = sc.nextInt();
                System.out.println("Tipo de producto: ");
                int type = sc.nextInt();
                ml.addProduct(name, description, price, 0, availableQuantity, type);
                System.out.println("Producto agregado \n");
                productMenu();
            }
            case 2 -> {
                displayListVerify(MercadoLibre::displayProducts, "productos");

                System.out.println("Indice del producto a eliminar: ");
                int index = sc.nextInt();
                ml.deleteProduct(index);
                System.out.println("Producto eliminado \n");
                productMenu();
            }
            case 3 -> {
                System.out.println("Productos: ");
                System.out.println(ml.displayProducts());
                System.out.println("\n");
                productMenu();
            }
            case 0 -> mainMenu();
            default -> {
                System.out.println("Opción invalida \n");
                productMenu();
            }
        }
    }
    public static void orderMenu(){
        System.out.println("1. Agregar orden");
        System.out.println("2. Eliminar orden");
        System.out.println("3. Mostrar ordenes");
        System.out.println("0. Regresar \n");

        int option = sc.nextInt();

        switch (option) {
            case 1 -> {
                System.out.println("Nombre de la orden: ");
                String name = sc.next();
                System.out.println("Cuantos productos desea agregar? ");
                int quantity = sc.nextInt();
                List<Integer> indexes = new ArrayList<>();

                displayListVerify(MercadoLibre::displayOrders, "productos");

                for (int i = 0; i < quantity; i++) {
                    System.out.println("Indice del producto a agregar: ");
                    int index = sc.nextInt();
                    while (index < 0 || index > ml.getProducts().size()){
                        System.out.println("Indice invalido");
                        System.out.println("Indice del producto a agregar: ");
                        index = sc.nextInt();
                    }
                    indexes.add(index);
                }

                System.out.println("Indice del producto a agregar: ");
                int index = sc.nextInt();
                ml.addOrder(indexes, LocalDate.now(), name, index);
                System.out.println("Orden agregada \n");
                orderMenu();
            }
            case 2 -> {
                displayListVerify(MercadoLibre::displayOrders, "ordenes");
                System.out.println("Indice de la orden a eliminar: ");
                int index = sc.nextInt();
                ml.deleteOrder(index);
                System.out.println("Orden eliminada \n");
                orderMenu();
            }
            case 3 -> {
                System.out.println("Ordenes: ");
                System.out.println(ml.displayOrders());
                System.out.println("\n");
                orderMenu();
            }
            case 0 -> mainMenu();
            default -> {
                System.out.println("Opción invalida \n");
                orderMenu();
            }
        }
    }

    public static void searchMenu(){
        System.out.println("1. Buscar producto");
        System.out.println("2. Buscar orden");
        System.out.println("0. Regresar \n");

        int option = sc.nextInt();

        switch (option) {
            case 1 -> {
                System.out.println("1. Nombre");
                System.out.println("2. Descripción");
                System.out.println("3. Precio");
                System.out.println("4. Cantidad disponible");
                System.out.println("5. Tipo");
                System.out.println("0. Regresar \n");

                int option2 = sc.nextInt();

                switch (option2) {
                    case 1 -> {
                        System.out.println("Nombre del producto: ");
                        String name = sc.next();
                        System.out.println(ml.searchProductByName(name));
                        System.out.println("\n");
                        searchMenu();
                    }
                    case 2 -> {
                        System.out.println("Precio del producto: ");
                        double price = sc.nextDouble();
                        System.out.println(ml.searchProductByPrice(price));
                        System.out.println("\n");
                        searchMenu();
                    }
                    case 3 -> {
                        System.out.println("Numero de veces comprado: ");
                        int timesBought = sc.nextInt();
                        System.out.println(ml.searchProductByTimesBought(timesBought));
                        System.out.println("\n");
                        searchMenu();
                    }
                    case 4 -> {
                        System.out.println("Tipo de producto: ");
                        System.out.println("1. Libros");
                        System.out.println("2. Electrónicos");
                        System.out.println("3. Ropa y accesorios");
                        System.out.println("4. Comida y bebidas");
                        System.out.println("5. Deportes");
                        System.out.println("6. Juguetes y juegos");
                        System.out.println("7. Belleza y cuidado personal");
                        System.out.println("8. Papelería");
                        System.out.println("\n");
                        int category = sc.nextInt();

                        while (category < 1 || category > 8) {
                            System.out.println("Opción invalida");
                            category = sc.nextInt();
                        }

                        System.out.println(ml.searchProductByCategory(ml.getCategory(category)));
                        System.out.println("\n");
                        searchMenu();
                    }
                    case 0 -> searchMenu();
                    default -> {
                        System.out.println("Opción invalida \n");
                        searchMenu();
                    }
                }
            }
            case 2 -> {
                System.out.println("1. Fecha");
                System.out.println("2. Nombre");
                System.out.println("3. Precio total");
                System.out.println("0. Regresar \n");

                int option2 = sc.nextInt();

                switch (option2) {
                    case 1: {
                        System.out.println("Fecha de la orden: ");
                        String date = sc.next();
                        System.out.println(ml.searchOrderByDate(LocalDate.parse(date)));
                        System.out.println("\n");
                        searchMenu();
                    }
                    case 2: {
                        System.out.println("Nombre de la orden: ");
                        String name = sc.next();
                        System.out.println(ml.searchOrderByName(name));
                        System.out.println("\n");
                        searchMenu();
                    }
                    case 3: {
                        System.out.println("Precio total de la orden: ");
                        double price = sc.nextDouble();
                        System.out.println(ml.searchOrderByTotalPrice(price));
                        System.out.println("\n");
                        searchMenu();
                    }
                }
            }
        }
    }

    public static void displayListVerify(Function<MercadoLibre, String> listDisplay, String item){
        System.out.println("Desea ver la lista de "+ item +"? (y/n)");
        String answer = sc.next();
        while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")){
            System.out.println("Opción invalida");
            System.out.println("Desea ver la lista de "+ item +"? (y/n)");
            answer = sc.next();
        }

        if(answer.equalsIgnoreCase("y")){
            System.out.println(item.substring(0,1).toUpperCase()+item.substring(1).toLowerCase()+": \n");
            System.out.println(listDisplay.apply(ml));
        }
    }

    //TODO: displayListSort
    public static void displayListSort(Function<MercadoLibre, String> listGetter){}

}