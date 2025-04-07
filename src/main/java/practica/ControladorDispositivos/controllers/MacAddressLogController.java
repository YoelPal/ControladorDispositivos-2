package practica.ControladorDispositivos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practica.ControladorDispositivos.services.IGenericDispService;

@RestController
@RequestMapping("/Logs")
public class MacAddressLogController {

    private final IGenericDispService genericDispService;

    @Autowired
    public MacAddressLogController(@Qualifier("MacAddressLog") IGenericDispService genericDispService) {
        this.genericDispService = genericDispService;
    }


}
