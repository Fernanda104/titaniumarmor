package com.titaniumarmor.catalogo_service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.titaniumarmor.catalogo_service.model.Categoria;
import com.titaniumarmor.catalogo_service.model.Producto;
import com.titaniumarmor.catalogo_service.repository.CategoriaRepository;
import com.titaniumarmor.catalogo_service.repository.ProductoRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();
        Random random = new Random();


        String[] nombresCategorias = {
                "Ropa Deportiva",
                "Calzado Deportivo",
                "Suplementos",
                "Accesorios de Entrenamiento"
        };

        List<Categoria> categorias = new ArrayList<>();

        for (String nombreCategoria : nombresCategorias) {

            Categoria categoria = new Categoria();
            categoria.setNombre(nombreCategoria);

            categorias.add(
                    categoriaRepository.save(categoria)
            );
        }

        String[][] productos = {
                {"Polera Dry Fit Hombre", "Ropa Deportiva", "Nike"},
                {"Short Deportivo Mujer", "Ropa Deportiva", "Adidas"},
                {"Zapatillas Running Pro", "Calzado Deportivo", "Asics"},
                {"Zapatillas Training Flex", "Calzado Deportivo", "Reebok"},
                {"Proteína Whey Chocolate", "Suplementos", "Optimum Nutrition"},
                {"Creatina Monohidratada", "Suplementos", "Universal Nutrition"},
                {"Guantes de Gimnasio", "Accesorios de Entrenamiento", "Everlast"},
                {"Botella Shaker 700ml", "Accesorios de Entrenamiento", "Titanium Armor"}
        };

        for (String[] datosProducto : productos) {

            Producto producto = new Producto();

            producto.setNombre(datosProducto[0]);
            producto.setDescripcion(faker.lorem().sentence(8));
            producto.setPrecio((double) random.nextInt(10000, 90000));
            producto.setMarca(datosProducto[2]);

            Categoria categoria = categorias.stream()
                    .filter(c -> c.getNombre().equals(datosProducto[1]))
                    .findFirst()
                    .orElse(categorias.get(0));

            producto.setCategoria(categoria);

            productoRepository.save(producto);
        }
    }
}