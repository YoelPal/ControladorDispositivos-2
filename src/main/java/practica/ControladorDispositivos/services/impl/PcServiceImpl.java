package practica.ControladorDispositivos.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.PcDTO;
import practica.ControladorDispositivos.models.entities.Ip;
import practica.ControladorDispositivos.models.entities.Pc;
import practica.ControladorDispositivos.models.repositories.PcRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("Pc")
public class PcServiceImpl implements IGenericDispService<PcDTO,Pc,String> {
    private final PcRepository pcRepository;
    private final ModelMapper modelMapper;

    public PcServiceImpl(PcRepository pcRepository,ModelMapper modelMapper) {
        this.pcRepository = pcRepository;
        
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PcDTO> findAll() {
        return pcRepository.findAll()
                .stream()
                .map(entity->modelMapper.map(entity, PcDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PcDTO> findById(String macAddress) {
        return pcRepository.findById(macAddress).map(entity->modelMapper.map(entity, PcDTO.class));
    }

    @Override
    public PcDTO save(Pc pc) {
        for(Ip ip : pc.getIps()){
            ip.setDispositivo(pc);
        }
        Pc savedPc = pcRepository.save(pc);
        return modelMapper.map(savedPc, PcDTO.class);
    }

    @Override
    public boolean deleteById(String mac) {
        Optional<Pc> pcOpt = pcRepository.findById(mac);
        if (pcOpt.isPresent()){
            pcRepository.deleteById(mac);
            return true;
        }
        return false;
    }

    @Override
    public Optional<PcDTO> update(Pc pc) {
        Optional<Pc> pcOptional = pcRepository.findById(pc.getMacAddress());
        if (pcOptional.isPresent()){
            pcOptional.get().getIps().clear();
            for (Ip ip : pc.getIps()){
                ip.setDispositivo(pcOptional.get());
                pcOptional.get().addIp(ip);
            }
            Pc updatedPc = pcRepository.save(pc);
            return Optional.of(modelMapper.map(updatedPc, PcDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<PcDTO>> findBySede(String sede) {
        List<PcDTO> pcDTOList = pcRepository.findBySede(sede).stream()
                .map(entity->modelMapper.map(entity,PcDTO.class))
                .toList();
        if (pcDTOList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(pcDTOList);
    }


    @Override
    public Page<PcDTO> findAllPaginated(Pageable pageable, Specification<Pc> spec) {
        Page<Pc> pcPage = pcRepository.findAll(spec,pageable);

        return pcPage.map(entity -> modelMapper.map(entity, PcDTO.class));
    }



}
