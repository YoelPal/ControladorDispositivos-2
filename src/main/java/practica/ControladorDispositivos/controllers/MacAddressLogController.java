package practica.ControladorDispositivos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practica.ControladorDispositivos.services.IMacAddressLogService;

@RestController
@RequestMapping("/Logs")
public class MacAddressLogController {

    private final IMacAddressLogService macAddressLogService;
    @Autowired
    public MacAddressLogController(IMacAddressLogService macAddressLogService) {
        this.macAddressLogService = macAddressLogService;
    }


}
