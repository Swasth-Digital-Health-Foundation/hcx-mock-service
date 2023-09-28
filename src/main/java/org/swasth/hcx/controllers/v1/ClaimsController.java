package org.swasth.hcx.controllers.v1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swasth.hcx.controllers.BaseController;
import org.swasth.hcx.utils.Constants;

import java.util.Map;

@RestController()
@RequestMapping(value =  "/"+ "${hcx_application.api_version}"+"/claim")
public class ClaimsController extends BaseController {

    @Value("${kafka.topic.claim}")
    private String kafkaTopic;

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseEntity<Object> claimSubmit(@RequestBody Map<String, Object> requestBody) throws Exception {
        return processRequest(requestBody, Constants.CLAIM_SUBMIT, Constants.CLAIM_ONSUBMIT, kafkaTopic);
    }

    @RequestMapping(value = "/on_submit", method = RequestMethod.POST)
    public ResponseEntity<Object> claimOnSubmit(@RequestBody Map<String, Object> requestBody) throws Exception {
        return processIncomingRequest(requestBody, Constants.CLAIM_ONSUBMIT);
    }

}
