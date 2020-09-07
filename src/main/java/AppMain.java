import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.impl.ProductServiceImpl;
import java.math.BigDecimal;

public class AppMain {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductServiceImpl productServiceImpl =
                (ProductServiceImpl) Injector.getInstance(ProductService.class);

        Product audiR8 = new Product("audiR8", 10500);
        Product audiR9 = new Product("audiR9", 16000);
        Product audiR10 = new Product("audiR10", 12700);

        productServiceImpl.create(audiR8);
        productServiceImpl.create(audiR9);
        productServiceImpl.create(audiR10);
        productServiceImpl.getAllProducts().forEach(System.out::println);
        System.out.println(audiR8);
        audiR8.setPrice(BigDecimal.valueOf(200500));
        System.out.println(audiR8);
        productServiceImpl.update(audiR8);
        System.out.println(productServiceImpl.getById(3L));
        productServiceImpl.deleteById(3L);
        productServiceImpl.getAllProducts().forEach(System.out::println);
    }
}
