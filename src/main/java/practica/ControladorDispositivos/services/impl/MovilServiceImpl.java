package practica.ControladorDispositivos.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.MovilDTO;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.models.repositories.MovilRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import java.util.List;
import java.util.Optional;


@Service("Movil")
public class MovilServiceImpl implements IGenericDispService<MovilDTO,Movil,String> {
    private final MovilRepository movilRepository;

    private final ModelMapper modelMapper;

    public MovilServiceImpl(MovilRepository movilRepository,ModelMapper modelMapper) {
        this.movilRepository = movilRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MovilDTO> findAll() {
        return  movilRepository.findAll()
                .stream()
                .map(entity->(modelMapper.map(entity, MovilDTO.class)))
                .toList();
    }

    @Override
    public Optional<MovilDTO> findById(String mac) {

        return movilRepository.findById(mac).map(movil->modelMapper.map(movil, MovilDTO.class));
    }

    @Override
    public MovilDTO save(Movil movil) {
        Movil savedMovil = movilRepository.save(movil);
        return modelMapper.map(savedMovil,MovilDTO.class) ;
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
            return Optional.of(modelMapper.map(updatedMovil, MovilDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<MovilDTO>> findBySede(String sede) {
        List<MovilDTO> apDTOList =movilRepository.findBySede(sede).stream()
                .map(entity->modelMapper.map(entity, MovilDTO.class))
                .toList();
        if (apDTOList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(apDTOList);
    }
}
