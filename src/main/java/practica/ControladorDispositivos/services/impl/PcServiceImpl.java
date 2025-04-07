package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.entities.Pc;
import practica.ControladorDispositivos.models.repositories.PcRepository;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;
import java.util.Optional;

@Service("Pc")
public class PcServiceImpl implements IGenericDispService<Pc,String> {
    private final PcRepository pcRepository;

    public PcServiceImpl(PcRepository pcRepository) {
        this.pcRepository = pcRepository;
    }

    @Override
    public List<Pc> findAll() {
        return pcRepository.findAll();
    }

    @Override
    public Optional<Pc> findById(String mac) {
        return pcRepository.findById(mac);
    }

    @Override
    public Pc save(Pc pc) {
        return pcRepository.save(pc);
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
    public Optional<Pc> update(Pc pc) {
        Optional<Pc> pcOptional = pcRepository.findById(pc.getMacAddress());
        if (pcOptional.isPresent()){
            return Optional.of(pcRepository.save(pc));
        }
        return Optional.empty();
    }
}
