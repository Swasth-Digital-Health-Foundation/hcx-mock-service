package org.swasth.hcx.controllers.v1;

import io.hcxprotocol.utils.Operations;
import kong.unirest.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.swasth.hcx.controllers.BaseController;
import org.swasth.hcx.dto.Response;
import org.swasth.hcx.exception.ClientException;
import org.swasth.hcx.service.BeneficiaryService;
import org.swasth.hcx.service.CloudStorageClient;
import org.swasth.hcx.utils.Constants;

import java.util.List;
import java.util.Map;

import static org.swasth.hcx.utils.Constants.*;

@RestController
@RequestMapping(Constants.VERSION_PREFIX)
public class BeneficiaryController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);

    @Autowired
    private CloudStorageClient cloudStorageClient;
    @Autowired
    private BeneficiaryService beneficiaryService;
    @Value("${phone.beneficiary-register}")
    private String beneficiaryRegisterContent;

    @PostMapping(BSP_REQUEST_LIST)
    public ResponseEntity<Object> requestList(@RequestBody Map<String, Object> requestBody) {
        try {
            String app = (String) requestBody.getOrDefault("app", "");
            if (requestBody.containsKey("mobile")) {
                String mobile = (String) requestBody.getOrDefault("mobile", "");
                return beneficiaryService.getRequestByMobileAndSender("mobile", mobile, app);
            } else if (requestBody.containsKey("sender_code")) {
                String senderCode = (String) requestBody.getOrDefault("sender_code", "");
                return beneficiaryService.getRequestByMobileAndSender("sender_code", senderCode, app);
            } else if (requestBody.containsKey("workflow_id")) {
                return beneficiaryService.getRequestByWorkflowId(requestBody);
            } else {
                throw new ClientException("Please provide valid request body");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + ex.getMessage());
        }
    }

    @PostMapping(SEND_OTP)
    public ResponseEntity<Object> sendOTP(@RequestBody Map<String, Object> requestBody) {
        try {
            String mobile = (String) requestBody.get(MOBILE);
            beneficiaryService.sendOTP(mobile, beneficiaryRegisterContent);
            return ResponseEntity.ok(Map.of("message", "OTP sent successfully", "mobile", mobile));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(VERIFY_OTP)
    public ResponseEntity<Object> verifyOTP(@RequestBody Map<String, Object> requestBody) {
        try {
            return beneficiaryService.verifyOTP(requestBody);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/check/communication/request")
    public ResponseEntity<Object> checkCommunicationRequest(@RequestBody Map<String, Object> requestBody) {
        try {
            if (requestBody.isEmpty() || !requestBody.containsKey("request_id")) {
                throw new ClientException("Request body is empty or request_id is missing");
            }
            Map<String, Object> responseMap = beneficiaryService.checkCommunicationRequest(requestBody);
            Response response = new Response(responseMap);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("Message", e.getMessage()));
        }
    }

}
