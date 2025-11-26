package com.safa.cabezon_backend.Servicios;

import com.safa.cabezon_backend.Dto.CrearResenyaClienteDTO;
import com.safa.cabezon_backend.Dto.ResenyaClienteDTO;
import com.safa.cabezon_backend.Mapper.ResenyaClienteMapper;
import com.safa.cabezon_backend.Modelos.Cliente;
import com.safa.cabezon_backend.Modelos.Producto;
import com.safa.cabezon_backend.Modelos.ResenyaCliente;
import com.safa.cabezon_backend.Repositorios.IClienteRepository;
import com.safa.cabezon_backend.Repositorios.IResenyaClienteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ResenyaClienteService {

    @Autowired
    private IResenyaClienteRepository resenyaClienteRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private IClienteRepository clienteRepository;

    private ResenyaClienteMapper resenyaClienteMapper;




    @Transactional
    public List<ResenyaClienteDTO> BuscarResenyaCliente() {
        return resenyaClienteMapper.toListResenyaClienteDTO(resenyaClienteRepository.findAll());
    }

    @Transactional
    public ResenyaClienteDTO BuscarResenyaClientePorId(Integer id) {
        return resenyaClienteMapper.toResenyaClienteDTO(resenyaClienteRepository.findById(id).orElse(null));
    }

    public void CrearResenyaCliente(CrearResenyaClienteDTO dto) {
        resenyaClienteRepository.save(resenyaClienteMapper.toEntity(dto));
    }

    public void EditarResenyaCliente(Integer id, CrearResenyaClienteDTO dto) {
        ResenyaCliente resenyaCliente = resenyaClienteRepository.findById(id).orElse(null);
        resenyaClienteMapper.actualizarEntityFromDto(dto, resenyaCliente);
        assert resenyaCliente != null;
        resenyaClienteRepository.save(resenyaCliente);
    }

    public void EliminarResenyaCliente(Integer id) {
        resenyaClienteRepository.deleteById(id);
    }
}
