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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    //Borrar cliente segun id
    @DeleteMapping("/delete/{id}")
    public void deleteCliente(@PathVariable Integer id){
        clienteService.EliminarClientePorId(id);
    }
}
