package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.PcDTO;
import practica.ControladorDispositivos.models.entities.Pc;
import practica.ControladorDispositivos.models.repositories.PcRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.dtoConverter.DtoConverterService;
import practica.ControladorDispositivos.services.dtoConverter.IDtoConverterService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("Pc")
public class PcServiceImpl implements IGenericDispService<PcDTO,Pc,String> {
    private final PcRepository pcRepository;
    private final IDtoConverterService dtoConverterService;

    public PcServiceImpl(PcRepository pcRepository, IDtoConverterService dtoConverterService) {
        this.pcRepository = pcRepository;
        this.dtoConverterService = dtoConverterService;
    }

    @Override
    public List<PcDTO> findAll() {
        return pcRepository.findAll()
                .stream()
                .map(dtoConverterService::converToPcDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PcDTO> findById(String mac) {
        return pcRepository.findById(mac).map(dtoConverterService::converToPcDTO);
    }

    @Override
    public PcDTO save(Pc pc) {
        Pc savedPc = pcRepository.save(pc);
        return dtoConverterService.converToPcDTO(savedPc);
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
            Pc updatedPc = pcRepository.save(pc);
            return Optional.of(dtoConverterService.converToPcDTO(updatedPc));
        }
        return Optional.empty();
    }
}
