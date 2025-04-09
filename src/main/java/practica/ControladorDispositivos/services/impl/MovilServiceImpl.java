package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.MovilDTO;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.models.repositories.MovilRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.dtoConverter.DtoConverterService;
import practica.ControladorDispositivos.services.dtoConverter.IDtoConverterService;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("Movil")
public class MovilServiceImpl implements IGenericDispService<MovilDTO,Movil,String> {
    private final MovilRepository movilRepository;
    private final IDtoConverterService dtoConverterService;

    public MovilServiceImpl(MovilRepository movilRepository, IDtoConverterService dtoConverterService) {
        this.movilRepository = movilRepository;
        this.dtoConverterService = dtoConverterService;
    }

    @Override
    public List<MovilDTO> findAll() {
        return  movilRepository.findAll()
                .stream()
                .map(dtoConverterService::converToMovilDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MovilDTO> findById(String mac) {
        return  movilRepository.findById(mac).map(dtoConverterService::converToMovilDTO);
    }

    @Override
    public MovilDTO save(Movil movil) {
        Movil savedMovil = movilRepository.save(movil);
        return dtoConverterService.converToMovilDTO(savedMovil) ;
    }

    @Override
    public boolean deleteById(String mac) {
        if (movilRepository.existsById(mac)) {
            movilRepository.deleteById(mac);
            return true;
        }
        return false;
    }

    @Override
    public Optional<MovilDTO> update( Movil movil){
        Optional<Movil> movilOpt = movilRepository.findById(movil.getMacAddress());
        if (movilOpt.isPresent()){
            Movil updatedMovil = movilRepository.save(movil);
            return Optional.of(dtoConverterService.converToMovilDTO(updatedMovil));
        }
        return Optional.empty();
    }
}
