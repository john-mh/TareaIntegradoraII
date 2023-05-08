import org.integradora2.model.Order;
import org.integradora2.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.integradora2.model.MercadoLibre;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class SearchUnitTest {

    public MercadoLibre mercadoLibre = new MercadoLibre();

    public void setup(){
        mercadoLibre.addProduct("Camisa de Algodon", "Sedosa camisa blanca", 20, 5, 10, 3);
        mercadoLibre.addProduct("Camisa de Algodon", "Sedosa camisa blanca", 20, 5, 10, 3);
        mercadoLibre.addProduct("Camisa de Algodon", "Sedosa camisa blanca", 20, 5, 10, 3);
        mercadoLibre.addProduct("Camisa de Algodon", "Sedosa camisa blanca", 20, 5, 10, 3);
        mercadoLibre.addProduct("Camisa de Algodon", "Sedosa camisa blanca", 20, 5, 10, 3);
        mercadoLibre.addProduct("Camisa", "Camisa roja", 20, 5, 10, 3);
        mercadoLibre.addProduct("Pan", "Pan de trigo", 5, 5, 10, 4);

        List<Integer> order1 = new LinkedList<>();
        List<Integer> order2 = new LinkedList<>();
        order1.add(0);
        order1.add(1);
        order1.add(2);
        order2.add(3);
        order2.add(4);
        order2.add(5);
        order2.add(6);

        mercadoLibre.addOrder(order1, LocalDate.now(), "John", 60);
        mercadoLibre.addOrder(order2, LocalDate.now(), "Juan", 65);

    }
    @Test
    public void searchProductByName_ContainsTest() {
        setup();
        List<Product> products = mercadoLibre.searchProductByName_Contains("Camisa");
        Assertions.assertEquals(products.size(), 6);
    }

    @Test
    public void searchProductByName_ContainsTest2() {
        setup();
        List<Product> products = mercadoLibre.searchProductByName_Contains("Pan");
        Assertions.assertEquals(products.size(), 1);
    }

    @Test
    public void searchProductByName_ContainsTest3() {
        setup();
        List<Product> products = mercadoLibre.searchProductByName_Contains("Pantalón");
        Assertions.assertNull(products);
    }

    @Test
    public void searchProductByName_Contains4(){
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Product> products = mercadoLibre.searchProductByName_Contains("");
        });
    }

    @Test
    public void searchProductByName_EqualsTest1() {
        setup();
        List<Product> products = mercadoLibre.searchProductByName_Equals("Camisa de Algodon");
        Assertions.assertEquals(products.size(), 5);
    }

    @Test
    public void searchProductByName_EqualsTest2() {
        setup();
        List<Product> products = mercadoLibre.searchProductByName_Equals("Camisa");
        Assertions.assertEquals(products.size(), 1);
    }

    @Test
    public void searchProductByName_EqualsTest3() {
        setup();
        List<Product> products = mercadoLibre.searchProductByName_Equals("Pantalón");
        Assertions.assertNull(products);
    }

    @Test
    public void searchProductByName_Equals4(){
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Product> products = mercadoLibre.searchProductByName_Equals("");
        });
    }


    @Test
    public void searchProductByCategoryTest1(){
        setup();
        List<Product> products = mercadoLibre.searchProductByCategory("Clothes & Accessories");
        Assertions.assertEquals(products.size(), 6);
    }

    @Test
    public void searchProductByCategoryTest2(){
        setup();
        List<Product> products = mercadoLibre.searchProductByCategory("Food & Beverages");
        Assertions.assertEquals(products.size(), 1);
    }

    @Test
    public void searchProductByCategoryTest3(){
        setup();
        List<Product> products = mercadoLibre.searchProductByCategory("Electronics");
        Assertions.assertNull(products);
    }

    @Test
    public void searchProductByCategoryTest4(){
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Product> products = mercadoLibre.searchProductByCategory("");
        });
    }

    @Test
    public void searchProductByPriceTest1() {
        setup();
        List<Product> products = mercadoLibre.searchProductByPrice(20);
        Assertions.assertEquals(products.size(), 6);
    }

    @Test
    public void searchProductByPriceTest2() {
        setup();
        List<Product> products = mercadoLibre.searchProductByPrice(5);
        Assertions.assertEquals(products.size(), 1);
    }

    @Test
    public void searchProductByPriceTest3() {
        setup();
        List<Product> products = mercadoLibre.searchProductByPrice(100);
        Assertions.assertNull(products);
    }

    @Test
    public void searchProductByPriceTest4() {
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Product> products = mercadoLibre.searchProductByPrice(-1);
        });
    }

    @Test
    public void searchProductByPriceTest5() {
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Product> products = mercadoLibre.searchProductByPrice(10001);
        });
    }

    @Test
    public void searchProductByPriceRangeTest1() {
        setup();
        List<Product> products = mercadoLibre.searchProductByPriceRange(5, 20);
        Assertions.assertEquals(products.size(), 7);
    }

    @Test
    public void searchProductByPriceRangeTest2() {
        setup();
        List<Product> products = mercadoLibre.searchProductByPriceRange(5, 5);
        Assertions.assertEquals(products.size(), 1);
    }

    @Test
    public void searchProductByPriceRangeTest3() {
        setup();
        List<Product> products = mercadoLibre.searchProductByPriceRange(100, 200);
        Assertions.assertNull(products);
    }

    @Test
    public void searchProductByPriceRangeTest4() {
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Product> products = mercadoLibre.searchProductByPriceRange(-1, 20);
        });
    }

    @Test
    public void searchProductByPriceRangeTest5() {
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Product> products = mercadoLibre.searchProductByPriceRange(20, -1);
        });
    }

    @Test
    public void searchProductByPriceRangeTest6() {
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Product> products = mercadoLibre.searchProductByPriceRange(10001, 200);
        });
    }

    @Test
    public void searchProductByPriceRangeTest7() {
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Product> products = mercadoLibre.searchProductByPriceRange(20, 10001);
        });
    }

    @Test
    public void searchOrderByNameTest1() {
        setup();
        List<Order> order = mercadoLibre.searchOrderByName("John");
        Assertions.assertEquals(order.size(), 1);
    }

    @Test
    public void searchOrderByNameTest2() {
        setup();
        List<Order> order = mercadoLibre.searchOrderByName("Juan");
        Assertions.assertEquals(order.size(), 1);
    }

    @Test
    public void searchOrderByNameTest3() {
        setup();
        List<Order> order = mercadoLibre.searchOrderByName("Pedro");
        Assertions.assertNull(order);
    }

    @Test
    public void searchOrderByNameTest4() {
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Order> order = mercadoLibre.searchOrderByName("");
        });
    }

    @Test
    public void searchOrderByDateTest1() {
        setup();
        List<Order> order = mercadoLibre.searchOrderByDate(LocalDate.now());
        Assertions.assertEquals(order.size(), 2);
    }

    @Test
    public void searchOrderByDateTest2() {
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Order> order = mercadoLibre.searchOrderByDate(LocalDate.of(2029, 10, 10));
        });
    }

    @Test
    public void searchOrderByDateTest3() {
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Order> order = mercadoLibre.searchOrderByDate(LocalDate.of(1999, 10, 10));
        });
    }

    @Test
    public void searchOrderByDateTest4() {
        setup();
        Assertions.assertNull(mercadoLibre.searchOrderByDate(LocalDate.of(2020, 10, 10)));
    }

    @Test
    public void searchOrderByTotalPriceTest1() {
        setup();
        List<Order> order = mercadoLibre.searchOrderByTotalPrice(60);
        Assertions.assertEquals(order.size(), 1);
    }

    @Test
    public void searchOrderByTotalPriceTest2() {
        setup();
        List<Order> order = mercadoLibre.searchOrderByTotalPrice(100);
        Assertions.assertNull(order);
    }

    @Test
    public void searchOrderByTotalPriceTest3() {
        setup();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Order> order = mercadoLibre.searchOrderByTotalPrice(-1);
        });
    }
}
