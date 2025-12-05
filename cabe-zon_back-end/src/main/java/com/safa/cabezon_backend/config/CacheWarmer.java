package com.safa.cabezon_backend.config;

import com.safa.cabezon_backend.Servicios.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CacheWarmer implements CommandLineRunner {

    private final ProductoService productoService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("---------------------------------------------");
        System.out.println("⚡ INICIANDO CARGA PREVIA DE DATOS (WARMING) ⚡");

        long inicio = System.currentTimeMillis();

        // ESTE METODO DETECTA QUE LA CACHE ESTA VACIA
        // LUEGO VA A LA BD Y GUARDA LOS DATOS
        productoService.BuscarProductos();

        long fin = System.currentTimeMillis();

        System.out.println("Productos cargados en caché en: " + (fin - inicio) + "ms");
        System.out.println("---------------------------------------------");
    }
}
