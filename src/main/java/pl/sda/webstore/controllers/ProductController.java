package pl.sda.webstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.webstore.domains.Product;
import pl.sda.webstore.exceptions.AttemptToBindDisallowedFieldException;
import pl.sda.webstore.services.ProductService;
import pl.sda.webstore.services.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @RequestMapping("/all")
    public ModelAndView listAllProducts() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", productService.getAllProducts());
        modelAndView.setViewName("products");
        return modelAndView;
    }

    @RequestMapping("/{category}")
    public String listProductsByCategory(Model model, @PathVariable("category") String category) {
        model.addAttribute("products", productService.getProductByCategory(category));
        return "products";
    }

    @RequestMapping("/filter/{ByCriteria}")
    public String listProductsByCriteria(
            @MatrixVariable(pathVar = "ByCriteria") Map<String, List<String>> filterParams, Model model) {
        model.addAttribute("products", productService.getProductsByFilter(filterParams));
        return "products";
    }

    @RequestMapping("/product")
    public String getProductById(@RequestParam("id") String productId, Model model) {
        model.addAttribute("product", productService.getProductById(productId));
        return "product";
    }

    @RequestMapping("/{ByCategory}/{ByPrice}")
    public String listProductsByPriceManufacturerAndCategory(
            @RequestParam("manufacturer") String manufacturer,
            @MatrixVariable(pathVar = "ByPrice") Map<String, List<String>> priceParams,
            @PathVariable("ByCategory") String category,
            Model model) {

        model.addAttribute("products", ((ProductServiceImpl) productService).getProductsByPriceManufacturerAndCategory(
                priceParams, manufacturer, category));
        return "products";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddNewProductForm(Model model) {
        Product newProduct = new Product();
        model.addAttribute("newProduct", newProduct);
        return "addProduct";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddNewProductForm(@ModelAttribute("newProduct") Product newProduct, BindingResult result) {
        String[] suppressedFields = result.getSuppressedFields();
        if (suppressedFields.length > 0) {
            throw new AttemptToBindDisallowedFieldException(
                    "Próba wiązania niedozwolonych pól:" + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }
        productService.addProduct(newProduct);
        return "redirect:/products";
    }

    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {
        binder.setDisallowedFields("unitsInOrder", "discontinued");
    }
}
