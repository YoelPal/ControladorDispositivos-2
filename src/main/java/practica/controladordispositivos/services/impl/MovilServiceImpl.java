package practica.ControladorDispositivos.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practica.ControladorDispositivos.models.dto.MovilDTO;
import practica.ControladorDispositivos.models.dto.PcDTO;
import practica.ControladorDispositivos.models.entities.Ip;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.models.entities.Pc;
import practica.ControladorDispositivos.models.repositories.MovilRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import java.util.List;
import java.util.Optional;


@Service("Movil")
public class MovilServiceImpl implements IGenericDispService<MovilDTO, Movil, String> {
    private final MovilRepository movilRepository;

    private final ModelMapper modelMapper;

    public MovilServiceImpl(MovilRepository movilRepository, ModelMapper modelMapper) {
        this.movilRepository = movilRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MovilDTO> findAll() {
        return movilRepository.findAll()
                .stream()
                .map(entity -> (modelMapper.map(entity, MovilDTO.class)))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MovilDTO> findById(String macAddress) {
        Optional<Movil> movilOptional = movilRepository.findById(macAddress);

        return movilOptional.map(movil -> modelMapper.map(movil, MovilDTO.class));
    }

    @Override
    public MovilDTO save(Movil movil) {
        for(Ip ip : movil.getIps()){
            ip.setDispositivo(movil);
        }
        Movil savedMovil = movilRepository.save(movil);
        return modelMapper.map(savedMovil, MovilDTO.class);
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
    public Optional<MovilDTO> update(Movil movil) {
        Optional<Movil> movilOpt = movilRepository.findById(movil.getMacAddress());
        if (movilOpt.isPresent()) {
            movilOpt.get().getIps().clear();
            for (Ip ip : movil.getIps()){
                ip.setDispositivo(movilOpt.get());
            }
            Movil updatedMovil = movilRepository.save(movil);
            return Optional.of(modelMapper.map(updatedMovil, MovilDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<MovilDTO>> findBySede(String sede) {
        List<MovilDTO> apDTOList = movilRepository.findBySede(sede).stream()
                .map(entity -> modelMapper.map(entity, MovilDTO.class))
                .toList();
        if (apDTOList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(apDTOList);
    }

    @Override
    public Page<MovilDTO> findAllPaginated(Pageable pageable, Specification<Movil> spec) {
        Page<Movil> movilPage = movilRepository.findAll(spec,pageable);

        return movilPage.map(entity -> modelMapper.map(entity, MovilDTO.class));
    }


}
