package practica.ControladorDispositivos.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.*;
import practica.ControladorDispositivos.models.entities.*;
import practica.ControladorDispositivos.models.repositories.DispositivoRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.dtoConverter.IDtoConverterService;

import java.util.List;
import java.util.Optional;

@Service("dispositivo")
@RequiredArgsConstructor
public class DispositivoServiceImpl implements IGenericDispService<DispositivoDTO,Dispositivo,String> {
    private final DispositivoRepository dispositivoRepository;
    private final IDtoConverterService dtoConverterService;

    @Override
    public List<DispositivoDTO> findAll(){

         return dispositivoRepository.findAll()
                .stream()
                .map(dtoConverterService::converToDispositivoDTO)
                 .toList();
    }

    @Override
    public Optional<DispositivoDTO> findById(String macAddress) {
        return dispositivoRepository.findById(macAddress).map(dtoConverterService::converToDispositivoDTO);
    }


    @Override
    public DispositivoDTO save(Dispositivo dispositivo) {
        Dispositivo savedDispositivo = dispositivoRepository.save(dispositivo);
        return dtoConverterService.converToDispositivoDTO(savedDispositivo);
    }

    @Override
    public boolean deleteById(String mac) {
        if (dispositivoRepository.existsById(mac)){
            dispositivoRepository.deleteById(mac);
            return true;
        }
        return false;
    }


    @Override
    public Optional<DispositivoDTO> update(Dispositivo dispositivo) {
        Optional<Dispositivo> dispositivoOpt = dispositivoRepository.findById(dispositivo.getMacAddress());
        if (dispositivoOpt.isPresent()){
            Dispositivo updatedDispositivo = dispositivoRepository.save(dispositivo);
           return Optional.of(dtoConverterService.converToDispositivoDTO(updatedDispositivo)) ;
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<DispositivoDTO>> findBySede(String sede) {
        List<DispositivoDTO> dispositivoDTOList = dispositivoRepository.findBySede(sede).stream()
                .map(dtoConverterService::converToDispositivoDTO)
                .toList();
        if (dispositivoDTOList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(dispositivoDTOList);
    }
}
