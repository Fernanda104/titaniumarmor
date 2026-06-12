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
public class DataLoader implements CommandLineRunner{
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

   @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        //Creamos categorias manualmente, ya que datafaker no tiene categorias que nos sirvan
        String[] nombresCategorias = {
            "Gimnasio y Musculación", "Montañismo y Outdoor", 
            "Básquetbol", "Proteínas y Suplementos"
        };
        List<Categoria> categoriasGeneradas = new ArrayList<>(); //se crea lista para guardar las categorias y poder utilizarlas luego
            // Generar categorias
        for (int i = 0; i < nombresCategorias.length; i++) {
            Categoria cat = new Categoria(); 
            cat.setNombre(nombresCategorias[i]);
            categoriasGeneradas.add(cat);
            categoriaRepository.save(cat);
        }

                // Generar productos random, con nombres y todo random ya que no hay datos exactos para nuestro proyecto
        for (int i = 0; i < 4; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());  
            producto.setDescripcion((faker.lorem().sentence(6)));  //usamos lorem ipsum para agregar la "descripcion"
            producto.setPrecio(faker.number().randomDouble(0, 5000, 200000)); //se agrega un valor double entre 500 y 200000, con decimal 0
            producto.setMarca(faker.commerce().brand()); //agrega marcas random
            producto.setCategoria(categoriasGeneradas.get(i)); //agrega el nombre de la categoriade acuerdo al numero en la lista creada arriba
            productoRepository.save(producto);
        }

    }
}
