package practica.controladordispositivos.services.dtoConverter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import practica.controladordispositivos.models.dto.*;
import practica.controladordispositivos.models.entities.*;

@Service
public class DtoConverterService implements IDtoConverterService{

    private final ModelMapper modelMapper;

    public DtoConverterService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DispositivoDTO converToDispositivoDTO(Dispositivo dispositivo) {
        DispositivoDTO dto;
        if (dispositivo instanceof Movil) {
            dto = modelMapper.map(dispositivo, MovilDTO.class);
            dto.setTipoDispositivo("Movil");
        } else if (dispositivo instanceof Pc) {
            dto = modelMapper.map(dispositivo, PcDTO.class);
            dto.setTipoDispositivo("Pc");
        } else if (dispositivo instanceof Router) {
            dto =  modelMapper.map(dispositivo, RouterDTO.class);
            dto.setTipoDispositivo("Router");
        } else if (dispositivo instanceof Switch) {
            dto = modelMapper.map(dispositivo, SwitchDTO.class);
            dto.setTipoDispositivo("Switch");
        } else if (dispositivo instanceof Tablet) {
            dto = modelMapper.map(dispositivo, TabletDTO.class);
            dto.setTipoDispositivo("Tablet");
        } else if (dispositivo instanceof Ap) {
            dto = modelMapper.map(dispositivo, ApDTO.class);
            dto.setTipoDispositivo("Ap");
        } else {
            return modelMapper.map(dispositivo, DispositivoDTO.class);
        }
        return dto;
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
