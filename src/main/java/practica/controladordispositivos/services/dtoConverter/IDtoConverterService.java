package practica.controladordispositivos.services.dtoConverter;

import practica.controladordispositivos.models.dto.*;
import practica.controladordispositivos.models.entities.*;

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

