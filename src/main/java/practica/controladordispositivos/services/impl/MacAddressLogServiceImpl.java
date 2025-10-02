package practica.ControladorDispositivos.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.dto.IpDTO;
import practica.ControladorDispositivos.models.dto.MacAddressLogDTO;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.Ip;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.MacAddressLogRepository;
import practica.ControladorDispositivos.models.repositories.specification.MacAddressLogSpecs;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.IMacAddressLogService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
@Slf4j
@Service("MacAddressLog")
public class MacAddressLogServiceImpl implements IMacAddressLogService {

    private final MacAddressLogRepository macAddressLogRepository;
    private final ModelMapper modelMapper;
    private final IGenericDispService<DispositivoDTO,Dispositivo,String> dispService;

    public MacAddressLogServiceImpl(MacAddressLogRepository macAddressLogRepository, ModelMapper modelMapper,@Qualifier("dispositivo") IGenericDispService<DispositivoDTO, Dispositivo, String> dispService) {
        this.macAddressLogRepository = macAddressLogRepository;
        this.modelMapper = modelMapper;
        this.dispService = dispService;
    }


    @Override
    public List<MacAddressLogDTO> findAll() {
        return macAddressLogRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity,MacAddressLogDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MacAddressLogDTO> findById(Long aLong) {
        return Optional.empty();
    }


    @Override
    public MacAddressLogDTO save(MacAddressLog macAddressLog) {
        MacAddressLog savedLog = macAddressLogRepository.save(macAddressLog);
        return modelMapper.map(savedLog, MacAddressLogDTO.class);
    }

    @Override
    public boolean deleteById(Long id) {
        macAddressLogRepository.deleteById(id);
        return true;
    }

    public Optional<MacAddressLog> findByMacAddress(String macAddress){
        return macAddressLogRepository.findByMacAddress(macAddress);
    }

    @Override
    public Optional<MacAddressLogDTO> update(MacAddressLog macAddressLog) {
        return Optional.empty();
    }



    @Override
    public Optional<List<MacAddressLogDTO>> findBySede(String sede){
        List<MacAddressLogDTO> listaLogs = macAddressLogRepository.findBySede(sede)
                .stream()
                .map(entity->modelMapper.map(entity,MacAddressLogDTO.class))
                .toList();
        if (!listaLogs.isEmpty()){
            return Optional.of(listaLogs);
        }
        return Optional.empty();
    }

    @Override
    public Page<MacAddressLogDTO> findAllPaginated(Pageable pageable, Specification<MacAddressLog> spec) {
        return null;
    }

    @Override
    public Page<MacAddressLogDTO> findAllPaginated(Pageable pageable, String macAddress, String sede, Boolean noCoincidentes) {

            Specification<MacAddressLog> spec =
                    Specification.where(MacAddressLogSpecs.macContaining(macAddress))
                            .and(MacAddressLogSpecs.sedeContaining(sede));

            if (Boolean.TRUE.equals(noCoincidentes)) {
                spec = spec.and(MacAddressLogSpecs.noCoincidentesPorMac());
            }

            return macAddressLogRepository
                    .findAll(spec, pageable)
                    .map(log -> modelMapper.map(log, MacAddressLogDTO.class));
        }

    @Override
    @Transactional
    public boolean deleteLogsUpdated() {
        List<MacAddressLog> logs = macAddressLogRepository.findAll();
        boolean eliminado = false;

        List<Long> idsToDelete = new ArrayList<>();
        for (MacAddressLog log : logs){
            Optional<DispositivoDTO> dispositivo = dispService.findById(log.getMacAddress());
            if (dispositivo.isPresent()){
                List<IpDTO> ipsDispositivo = dispositivo.get().getIps();
                for (IpDTO ip : ipsDispositivo){
                    if (ip.getIpAddress().equals(log.getIp())){
                        idsToDelete.add(log.getId());
                        break;
                    }
                }
            }
        }
        int count = 0;

            for (Long id : idsToDelete) {
                try {
                    macAddressLogRepository.deleteById(id);
                    count++;
                    eliminado = true;

                } catch (EmptyResultDataAccessException e) {
                    // El log ya no existe. Esto es esperado si hay concurrencia.
                    log.warn("Log no encontrado para eliminación, probablemente ya fue eliminado por otra transacción.");
                } catch (Exception e) {
                    // Capturar otras excepciones inesperadas durante el borrado
                    log.error("Error al eliminar log " + e.getMessage(), e);
                    // Decide si quieres re-lanzar o continuar
                }
            }
        log.info("Logs eliminados: " + count);

        return eliminado;
    }

}
