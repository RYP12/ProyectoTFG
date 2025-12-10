package com.safa.cabezon_backend.Controladores;

import com.safa.cabezon_backend.Dto.BuscarClienteAdminDTO;
import com.safa.cabezon_backend.Dto.BuscarClienteDTO;
import com.safa.cabezon_backend.Dto.ClienteDTO;
import com.safa.cabezon_backend.Dto.CrearClienteDTO;
import com.safa.cabezon_backend.Mapper.ClienteMapper;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import com.safa.cabezon_backend.Servicios.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
//Controlador de clientes
public class ClienteController {

    private final ClienteService clienteService;
    private IClienteRepository clienteRepository;

    private ClienteMapper clienteMapper;

    //Solictar todos los clientes(BuscarClienteDTO)
    @GetMapping("/all")
    public List<BuscarClienteDTO> getClientes(){return clienteMapper.listToDTO(clienteRepository.findAll());}

    // Solicitar clientes paginados de 5 en 5 (BuscarClienteAdminDTO)
    @GetMapping("/admin")
    public ResponseEntity<Page<BuscarClienteAdminDTO>> getClientesAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<BuscarClienteAdminDTO> pagina = clienteService.buscarClienteAdminPaginados(pageable);
        return ResponseEntity.ok(pagina);
    }

    //Solicitar cliente por id(BuscarClienteDTO)
    @GetMapping("/{id}")
    public BuscarClienteDTO getClienteById(@PathVariable Integer id){return clienteMapper.toDTO(clienteRepository.findById(id).orElse(null));}

    //Guardar cliente en base de datos(ClienteDTO)
    @PostMapping("/post")
    public void postCliente(@RequestBody CrearClienteDTO dto){
        clienteService.CrearCliente(dto);
    }

    //Editar Cliente(ClienteDTO)
    @PutMapping("/put/{id}")
    public void putCliente(@PathVariable Integer id, @RequestBody ClienteDTO dto){
        clienteService.EditarClientePorId(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCliente(@PathVariable Integer id){
        clienteService.EliminarClientePorId(id);
    }

    @GetMapping("/me")
    public ResponseEntity<BuscarClienteDTO> obtenerMiPerfil(Authentication authentication) {
        String username = authentication.getName();

        BuscarClienteDTO clienteDTO = clienteService.obtenerClientePorUsername(username);

        return ResponseEntity.ok(clienteDTO);
    }

    // Subir foto
    @PostMapping("/{id}/foto")
    public ResponseEntity<Map<String, String>> subirFoto(
            @PathVariable Integer id,
            @RequestParam("foto") MultipartFile file
    ) {
        try {
            // Validar que sea una imagen
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().build();
            }

            String fileName = id + "_" + System.currentTimeMillis() + ".jpg";

            String projectRoot = System.getProperty("user.dir");
            Path uploadPath = Paths.get(projectRoot, "uploads", "fotos");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Cliente cliente = clienteRepository.findById(id).orElse(null);
            if (cliente != null) {
                // Esta es la URL que se guarda en base de datos
                String url = "/uploads/fotos/" + fileName;
                cliente.setFoto(url);
                clienteRepository.save(cliente);

                Map<String, String> response = new HashMap<>();
                response.put("url", url);
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
