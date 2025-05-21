package practica.ControladorDispositivos.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.ApDTO;
import practica.ControladorDispositivos.models.entities.Ap;
import practica.ControladorDispositivos.models.entities.Ip;
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
    private final ModelMapper modelMapper;

    public ApServiceImpl(ApRepository apRepository, IDtoConverterService dtoConverterService, ModelMapper modelMapper) {
        this.apRepository = apRepository;
        this.dtoConverterService = dtoConverterService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ApDTO> findAll() {
        return apRepository.findAll()
                .stream()
                .map(entity->(modelMapper.map(entity, ApDTO.class)))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ApDTO> findById(String macAddress) {
        return apRepository.findById(macAddress).map(entity->modelMapper.map(entity, ApDTO.class));
    }

    @Override
    public ApDTO save(Ap entity) {

        for(Ip ip : entity.getIps()){
            ip.setDispositivo(entity);
        }
        Ap savedAp =  apRepository.save(entity);
        return modelMapper.map(savedAp, ApDTO.class);
    }

    @Override
    public boolean deleteById(String mac) {
        Optional<Ap> apOptional = apRepository.findById(mac);
        if (apOptional.isPresent()){
            apRepository.deleteById(mac);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ApDTO> update(Ap ap) {
        Optional<Ap> apOptional = apRepository.findById(ap.getMacAddress());
        if (apOptional.isPresent()){
            apOptional.get().getIps().clear();
            for(Ip ip : ap.getIps()){
                ip.setDispositivo(apOptional.get());

                apOptional.get().addIp(ip);
            }
            Ap updatedAp = apRepository.save(apOptional.get());
            return Optional.of(dtoConverterService.converToApDTO(updatedAp)) ;
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<ApDTO>> findBySede(String sede) {
        List<ApDTO> apDTOList =apRepository.findBySede(sede).stream()
                .map(entity->modelMapper.map(entity, ApDTO.class))
                .toList();
        if (apDTOList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(apDTOList);
    }

    @Override
    public Page<ApDTO> findAllPaginated(Pageable pageable, Specification<Ap> spec) {
        Page<Ap> apPage = apRepository.findAll(spec,pageable);

        return apPage.map(entity -> modelMapper.map(entity, ApDTO.class));
    }


}
