package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.ApDTO;
import practica.ControladorDispositivos.models.entities.Ap;
import practica.ControladorDispositivos.models.repositories.ApRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.dtoConverter.IDtoConverterService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ap")
public class ApServiceImpl implements IGenericDispService<ApDTO,Ap,String> {
    private final ApRepository apRepository;
    private final IDtoConverterService dtoConverterService;

    public ApServiceImpl(ApRepository apRepository, IDtoConverterService dtoConverterService) {
        this.apRepository = apRepository;
        this.dtoConverterService = dtoConverterService;
    }

    @Override
    public List<ApDTO> findAll() {
        return apRepository.findAll()
                .stream()
                .map(dtoConverterService::converToApDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ApDTO> findById(String mac) {
        return apRepository.findById(mac).map(dtoConverterService::converToApDTO);
    }

    @Override
    public ApDTO save(Ap entity) {
        Ap savedAp =  apRepository.save(entity);
        return dtoConverterService.converToApDTO(savedAp);
    }

    @Override
    public boolean deleteById(String mac) {
        Optional<Ap> apOptional = apRepository.findById(mac);
        if (apOptional.isPresent()){
            apRepository.deleteById(mac);
        }
        return false;
    }

    @Override
    public Optional<ApDTO> update(Ap ap) {
        Optional<Ap> apOptional = apRepository.findById(ap.getMacAddress());
        if (apOptional.isPresent()){
            Ap updatedAp = apRepository.save(ap);
            return Optional.of(dtoConverterService.converToApDTO(updatedAp)) ;
        }
        return Optional.empty();
    }
}
