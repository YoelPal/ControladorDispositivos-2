package practica.ControladorDispositivos.services.dtoConverter;

import practica.ControladorDispositivos.models.dto.*;
import practica.ControladorDispositivos.models.entities.*;

public interface IDtoConverterService {
    DispositivoDTO converToDispositivoDTO(Dispositivo dispositivo);
    MovilDTO converToMovilDTO(Movil movil);
    PcDTO converToPcDTO(Pc pc);
    TabletDTO convertToTabletDTO(Tablet tablet);
    RouterDTO convertToRouterDTO(Router router);
    SwitchDTO convertToSwitchDTO(Switch switchA);
    ApDTO converToApDTO(Ap ap);
    MacAddressLogDTO convertToMacAddressLogDTO(MacAddressLog macAddressLog);

}

