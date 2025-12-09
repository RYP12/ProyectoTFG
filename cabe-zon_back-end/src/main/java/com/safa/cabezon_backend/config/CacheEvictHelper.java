package com.safa.cabezon_backend.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component // ⬅️ Debe ser un componente de Spring
public class CacheEvictHelper {

    @CacheEvict(value = "productos", allEntries = true) // ⬅️ ANOTACIÓN CRÍTICA
    public void vaciarCacheProductos() {
        // Este método sólo existe para que Spring intercepte el @CacheEvict.
        System.out.println("--- CACHÉ 'productos' VACIADA CORRECTAMENTE ---");
    }
}
