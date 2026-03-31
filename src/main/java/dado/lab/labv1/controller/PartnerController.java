package dado.lab.labv1.controller;

import dado.lab.labv1.model.InfoDTO;
import dado.lab.labv1.model.Status;
import dado.lab.labv1.service.PartnerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/integration")
public class PartnerController {

    private final PartnerService partnerservice;

    public PartnerController(PartnerService partnerservice) {
        this.partnerservice = partnerservice;
    }

    @GetMapping
    public InfoDTO getIntegrationsByStatus(@RequestParam(required = false) Status status) {
        return partnerservice.getByStatus(status);
    }

}
