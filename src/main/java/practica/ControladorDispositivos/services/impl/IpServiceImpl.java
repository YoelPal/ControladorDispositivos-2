package practica.ControladorDispositivos.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practica.ControladorDispositivos.models.dto.IpDTO;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.Ip;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.DispositivoRepository;
import practica.ControladorDispositivos.models.repositories.IpRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.IIpService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ip")
public class IpServiceImpl implements IIpService {
    private final IpRepository ipRepository;
    private final ModelMapper modelMapper;
    private final DispositivoRepository dispositivoRepository;


    public IpServiceImpl(IpRepository ipRepository, ModelMapper modelMapper, DispositivoRepository dispositivoRepository) {
        this.ipRepository = ipRepository;
        this.modelMapper = modelMapper;
        this.dispositivoRepository = dispositivoRepository;
    }

    @Override
    public List<IpDTO> findAll() {

        return ipRepository.findAll()
                .stream()
                .map(entity -> (modelMapper.map(entity, IpDTO.class)))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<IpDTO> findById(Long id) {
        return ipRepository.findById(id).map(entity ->modelMapper.map(entity, IpDTO.class));
    }

    @Override
    public IpDTO save(Ip entity) {
        Ip ip = ipRepository.save(entity);
        return modelMapper.map(ip, IpDTO.class);
    }

    @Override
    public boolean deleteById(Long aLong) {
        Optional<Ip> ipOptional = ipRepository.findById(aLong);
        if (ipOptional.isPresent()){
            ipRepository.deleteById(aLong);
            return true;
        }
        return false;
    }

    @Override
    public Optional<IpDTO> update(Ip entity) {
        return Optional.empty();
    }

    @Override
    public Optional<List<IpDTO>> findBySede(String sede) {
        return Optional.empty();
    }

    public Optional<Ip> findByIpAddress(String ip){
        return ipRepository.findByIpAddress(ip);
    }

    @Override
    public Page<IpDTO> findAllPaginated(Pageable pageable, Specification<Ip> spec) {
        Page<Ip> ips = ipRepository.findAll(spec,pageable);
        return ips.map(entity -> modelMapper.map(entity, IpDTO.class));
    }
    @Override
    @Transactional
    public void saveNewIpsByMac(List<MacAddressLog> macsConocidas){

        for (MacAddressLog logCoincidente: macsConocidas){
            Optional<Dispositivo> dispositivo = dispositivoRepository.findById(logCoincidente.getMacAddress());
            if (dispositivo.isPresent()){
                List<Ip> ipsDispositivo = dispositivo.get().getIps();
                String ipStringLog = logCoincidente.getIp();
                boolean existe = false;
                for (Ip ipDisp: ipsDispositivo){

                    if (ipStringLog.equals(ipDisp.getIpAddress())){
                        existe= true;
                        break;
                    }
                }
                if (!existe && ipStringLog!=null && !ipStringLog.isEmpty()){
                    Ip nuevaIp = new Ip();
                    nuevaIp.setIpAddress(ipStringLog);
                    nuevaIp.setDispositivo(dispositivo.get());
                    ipRepository.save(nuevaIp);
                    ipsDispositivo.add(nuevaIp);
                }
            }
        }

    }

}
