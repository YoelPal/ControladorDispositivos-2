package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor

public abstract class GenericDeviceController<DTO, Ent, ID> {

    protected final IGenericDispService<DTO, Ent, ID> tipoService;
    protected final IGenericDispService<DTO, Ent, ID> dispositivoService;
    protected final ModelMapper mapper;


    @GetMapping
    @Operation(summary = "Listar dispositivos", description = "Devuelve todos los dispositivos del tipo.")
    public ResponseEntity<List<DTO>> listAll() {
        List<DTO> list = tipoService.findAll();
        return list.isEmpty()
                ? ResponseEntity.ok().body(Collections.emptyList())
                : ResponseEntity.ok(list);
    }

    @PostMapping
    @Operation(summary = "Crear dispositivo", description = "Crea un nuevo dispositivo, validando unicidad de ID.")
    public ResponseEntity<?> create(
            @Parameter(description = "DTO del dispositivo en formato JSON")
            @RequestBody DTO dto
    ) {
        ID id = extractId(dto);
        if (dispositivoService.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El identificador ya está asignado");
        }
        Ent entity = mapper.map(dto, getEntityClass());
        DTO saved = tipoService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar dispositivo", description = "Actualiza un dispositivo existente por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dispositivo actualizado"),
            @ApiResponse(responseCode = "404", description = "La Mac en la URL no coincide con la Mac del cuerpo."),
            @ApiResponse(responseCode = "204", description = "Dispositivo no encontrado con esa Mac")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "MAC del dispositivo a actualizar") @PathVariable ID id,
            @Parameter(description = "DTO con datos a actualizar en formato JSON") @RequestBody DTO dto)

    {
        if (!id.equals(extractId(dto))) {
            return ResponseEntity.badRequest()
                    .body("El ID en la URL no coincide con el ID del cuerpo.");
        }
        Optional<DTO> existing = tipoService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Dispositivo no encontrado con ID: " + id);
        }
        Ent entity = mapper.map(dto, getEntityClass());
        Optional<DTO> updated = tipoService.update(entity);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/sede/{sede}")
    @Operation(summary = "Buscar por sede", description = "Devuelve dispositivos que coinciden con la sede.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay dispositivos en esa sede.")
    })
    public ResponseEntity<List<DTO>> findBySede(
            @Parameter(description = "Sede para filtrar") @PathVariable ID sede
    ) {
        Optional<List<DTO>> list = tipoService.findBySede(sede);
        return list.isPresent() && !list.get().isEmpty()
                ? ResponseEntity.ok(list.get())
                : ResponseEntity.noContent().build();
    }

    /** Extrae el ID (p.ej. MAC) de un DTO */
    protected abstract ID extractId(DTO dto);

    /** Provee la clase de entidad para el mapeo ModelMapper */
    protected abstract Class<Ent> getEntityClass();
}
