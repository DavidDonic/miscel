package dado.lab.labv1.service;

import dado.lab.labv1.model.Info;
import dado.lab.labv1.model.InfoDTO;
import dado.lab.labv1.model.Status;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PartnerService {

    private List<InfoDTO> testData;
    
    PartnerService() {
        this.testData = Arrays.asList(new InfoDTO(1, "Da", Status.FINISH),
                                      new InfoDTO(2, "Do", Status.PROCESSING),
                                      new InfoDTO(3, "Ge", Status.PROCESSING));
    }
    
    public void createIntegration(Info info) {
        //...save(new InfoDTO(info));
        return;
    }
    
    public InfoDTO getByStatus(Status status) {
        return testData.stream().filter(info ->
                                         info.status().equals
                                                 ((status == null) ? Status.FINISH : status))
                                                  .findFirst().orElse(null);
    }
}