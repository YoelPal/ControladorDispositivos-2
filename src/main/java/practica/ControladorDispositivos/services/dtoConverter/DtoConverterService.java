package practica.ControladorDispositivos.services.dtoConverter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.*;
import practica.ControladorDispositivos.models.entities.*;

@Service
public class DtoConverterService implements IDtoConverterService{

    private final ModelMapper modelMapper;

    public DtoConverterService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DispositivoDTO converToDispositivoDTO(Dispositivo dispositivo) {
        if (dispositivo instanceof Movil) {
            return modelMapper.map(dispositivo, MovilDTO.class);
        } else if (dispositivo instanceof Pc) {
            return modelMapper.map(dispositivo, PcDTO.class);
        } else if (dispositivo instanceof Router) {
            return modelMapper.map(dispositivo, RouterDTO.class);
        } else if (dispositivo instanceof Switch) {
            return modelMapper.map(dispositivo, SwitchDTO.class);
        } else if (dispositivo instanceof Tablet) {
            return modelMapper.map(dispositivo, TabletDTO.class);
        } else if (dispositivo instanceof Ap) {
            return modelMapper.map(dispositivo, ApDTO.class);
        } else {
            return modelMapper.map(dispositivo, DispositivoDTO.class);
        }
    }

    @Override
    public MovilDTO converToMovilDTO(Movil movil) {
        return modelMapper.map(movil,MovilDTO.class);
    }

    @Override
    public PcDTO converToPcDTO(Pc pc) {
        return modelMapper.map(pc,PcDTO.class);
    }

    @Override
    public TabletDTO convertToTabletDTO(Tablet tablet) {
        return modelMapper.map(tablet,TabletDTO.class);
    }

    @Override
    public RouterDTO convertToRouterDTO(Router router) {
        return modelMapper.map(router, RouterDTO.class);
    }

    @Override
    public SwitchDTO convertToSwitchDTO(Switch switchA) {
        return modelMapper.map(switchA,SwitchDTO.class);
    }

    @Override
    public ApDTO converToApDTO(Ap ap) {
        return modelMapper.map(ap,ApDTO.class);
    }

    @Override
    public MacAddressLogDTO convertToMacAddressLogDTO(MacAddressLog macAddressLog) {
        return modelMapper.map(macAddressLog, MacAddressLogDTO.class);
    }
}
