/*
* Proyecto: Tarea Integradora 2
* Autor: John Hortua
* Código: A00373357
*/

package org.integradora2.ui;

import org.integradora2.model.FileManager;
import org.integradora2.model.MercadoLibre;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public class Main {

    public static final MercadoLibre ml = new MercadoLibre();
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        FileManager.loadData(ml.getProducts(), ml.getOrders());
        mainMenu();
    }

    public static void mainMenu() {
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
            case 0 -> {
                FileManager.saveData(ml.getProducts(), ml.getOrders());
                System.out.println("\n Gracias por usar Mercado Libre");
                System.exit(0);
            }
            default -> {
                System.out.println("Opción invalida \n");
                mainMenu();
            }
        }
    }

    public static void productMenu() {
        System.out.println("1. Agregar nuevo producto");
        System.out.println("2. Agregar producto existente");
        System.out.println("3. Eliminar producto");
        System.out.println("4. Mostrar productos");
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
                productPriceValidation(price);
                System.out.println("Cantidad disponible del producto: ");
                int availableQuantity = sc.nextInt();
                System.out.println("Tipo de producto: ");
                int type = sc.nextInt();
                ml.addProduct(name, description, price, 0, availableQuantity, type);
                System.out.println("Producto agregado \n");
                productMenu();
            }
            case 2 -> {
                displayListValidation(MercadoLibre::displayProducts, "productos");

                System.out.println("Indice del producto a agregar: ");
                int index = sc.nextInt();

                while (index < 0 || index >= ml.getProducts().size()) {
                    System.out.println("Indice invalido, intente de nuevo");
                    index = sc.nextInt();
                }

                System.out.println("Cantidad a agregar: ");
                int quantity = sc.nextInt();
                ml.increaseProductQuantity(index, quantity);
                System.out.println("Producto agregado \n");
                productMenu();
            }
            case 3 -> {
                displayListValidation(MercadoLibre::displayProducts, "productos");

                System.out.println("Indice del producto a eliminar: ");
                int index = sc.nextInt();
                ml.deleteProduct(index);
                System.out.println("Producto eliminado \n");
                productMenu();
            }
            case 4 -> {
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

    public static void orderMenu() {
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
                double totalPrice = 0;

                displayListValidation(MercadoLibre::displayOrders, "productos");

                for (int i = 0; i < quantity; i++) {
                    System.out.println("Indice del producto a agregar: ");
                    int index = sc.nextInt();
                    while (index < 0 || index > ml.getProducts().size()) {
                        System.out.println("Indice invalido");
                        System.out.println("Indice del producto a agregar: ");
                        index = sc.nextInt();
                    }
                    indexes.add(index);
                    totalPrice += ml.getProductPrice(index);
                }

                System.out.println("Indice del producto a agregar: ");
                int index = sc.nextInt();
                ml.addOrder(indexes, LocalDate.now(), name, totalPrice);
                System.out.println("Orden agregada \n");
                orderMenu();
            }
            case 2 -> {
                displayListValidation(MercadoLibre::displayOrders, "ordenes");
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

    public static void searchMenu() {
        System.out.println("1. Buscar producto");
        System.out.println("2. Buscar orden");
        System.out.println("0. Regresar \n");

        int option = sc.nextInt();
        String[] sortParameters = new String[2];

        switch (option) {
            case 1 -> {
                System.out.println("1. Nombre");
                System.out.println("2. Precio");
                System.out.println("3. Categoría");
                System.out.println("4. Veces comprado");
                System.out.println("0. Regresar \n");

                int option2 = sc.nextInt();
                String[] parameterOptions = {"name", "price", "category", "timesBought"};

                switch (option2) {
                    case 1: {
                        System.out.println("Nombre del producto: ");
                        String name = sc.next();
                        setSortParameters(sortParameters, parameterOptions);
                        System.out.println("Quiere que la búsqueda sea exacta? (s/n)");
                        System.out.println("* Si es exacta, solo se mostrarán los productos que tengan el nombre ingresado, si no, se mostrarán los productos que contengan el nombre ingresado *");
                        String exact = sc.next();

                        if (exact.equals("s")) {
                            System.out.println(ml.displaySortedProductList(ml.searchProductByName_Equals(name), sortParameters[1], sortParameters[0]));
                        } else {
                            System.out.println(ml.displaySortedProductList(ml.searchProductByName_Contains(name), sortParameters[1], sortParameters[0]));
                        }

                        System.out.println("\n");
                        searchMenu();
                    }
                    case 2: {
                        System.out.println("Precio del producto: ");
                        double price = sc.nextDouble();
                        productPriceValidation(price);
                        setSortParameters(sortParameters, parameterOptions);
                        System.out.println(ml.displaySortedProductList(ml.searchProductByPrice(price), sortParameters[1], sortParameters[0]));
                        System.out.println("\n");
                        searchMenu();
                    }
                    case 3: {
                        System.out.println("Categoría del producto: ");
                        System.out.println("1. Libros");
                        System.out.println("2. Electrónicos");
                        System.out.println("3. Ropa y accesorios");
                        System.out.println("4. Comida y bebidas");
                        System.out.println("5. Deportes");
                        System.out.println("6. Juguetes y juegos");
                        System.out.println("7. Belleza y cuidado personal");
                        System.out.println("8. Papelería");
                        int type = sc.nextInt();

                        while (type < 1 || type > 8) {
                            System.out.println("Opción invalida");
                            System.out.println("Categoría del producto: ");
                            type = sc.nextInt();
                        }

                        setSortParameters(sortParameters, parameterOptions);
                        System.out.println(ml.displaySortedProductList(ml.searchProductByCategory(ml.getCategory(type)), sortParameters[1], sortParameters[0]));
                        System.out.println("\n");
                        searchMenu();
                    }
                    case 4: {
                        System.out.println("Veces comprado del producto: ");
                        int timesBought = sc.nextInt();
                        setSortParameters(sortParameters, parameterOptions);
                        System.out.println(ml.displaySortedProductList(ml.searchProductByTimesBought(timesBought), sortParameters[1], sortParameters[0]));
                        System.out.println("\n");
                        searchMenu();
                    }
                    case 0: {
                        searchMenu();
                    }
                    default: {
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
                String[] parameterOptions = {"date", "name", "totalPrice"};

                switch (option2) {
                    case 1: {
                        System.out.println("Fecha de la orden: ");
                        String date = sc.next();
                        setSortParameters(sortParameters, parameterOptions);
                        System.out.println(ml.displaySortedOrderList(ml.searchOrderByDate(LocalDate.parse(date)), sortParameters[1], sortParameters[0]));
                        System.out.println("\n");
                        searchMenu();
                    }
                    case 2: {
                        System.out.println("Nombre de la orden: ");
                        String name = sc.next();
                        setSortParameters(sortParameters, parameterOptions);
                        System.out.println(ml.displaySortedOrderList(ml.searchOrderByName(name), sortParameters[1], sortParameters[0]));
                        System.out.println("\n");
                        searchMenu();
                    }
                    case 3: {
                        System.out.println("Precio total de la orden: ");
                        double price = sc.nextDouble();
                        setSortParameters(sortParameters, parameterOptions);
                        System.out.println(ml.displaySortedOrderList(ml.searchOrderByTotalPrice(price), sortParameters[1], sortParameters[0]));
                        System.out.println("\n");
                        searchMenu();
                    }
                }
            }
        }
    }

    public static void productPriceValidation(double price) {
        while (price <= 0 || price > 10000) {
            System.out.println("El precio no es válido, ingrese un precio válido");
            System.out.println("Precio del producto: ");
            price = sc.nextDouble();
        }
    }

    public static void displayListValidation(Function<MercadoLibre, String> listDisplay, String item) {
        System.out.println("Desea ver la lista de " + item + "? (y/n)");
        String answer = sc.next();
        while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
            System.out.println("Opción invalida");
            System.out.println("Desea ver la lista de " + item + "? (y/n)");
            answer = sc.next();
        }

        if (answer.equalsIgnoreCase("y")) {
            System.out.println(item.substring(0, 1).toUpperCase() + item.substring(1).toLowerCase() + ": \n");
            System.out.println(listDisplay.apply(ml));
        }
    }

    public static void setSortParameters(String[] sortParameters, String[] parameterOptions) {
        System.out.println("Desea ordenar la lista? (y/n)");
        String answer = sc.next();
        while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
            System.out.println("Opción invalida");
            System.out.println("Desea ordenar la lista? (y/n)");
            answer = sc.next();
        }

        if (answer.equalsIgnoreCase("y")) {
            System.out.println("1. Ascendente");
            System.out.println("2. Descendente");
            int option = sc.nextInt();
            while (option < 1 || option > 2) {
                System.out.println("Opción invalida");
                option = sc.nextInt();
            }

            if (option == 1) {
                sortParameters[0] = "asc";
            } else {
                sortParameters[0] = "desc";
            }

            System.out.println("Por cual atributo desea ordenar la lista?");
            for (int i = 0; i < parameterOptions.length; i++) {
                System.out.println((i + 1) + ". " + parameterOptions[i]);
            }

            int option2 = sc.nextInt();

            while (option2 < 1 || option2 > parameterOptions.length) {
                System.out.println("Opción invalida");
                option2 = sc.nextInt();
            }

            boolean valid = false;

            for(int i = 0; i < parameterOptions.length && !valid; i++) {
                if(option2 == i + 1) {
                    sortParameters[1] = parameterOptions[i];
                    valid = true;
                }
            }
        }
    }



}