package org.swasth.hcx.controllers.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.swasth.hcx.dto.HeaderAudit;
import org.swasth.hcx.controllers.BaseSpec;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


public class ClaimsTests extends BaseSpec {

    @Value("${hcx_application.api_version}")
    private String api_version;
  
  @Test
  public void check_claim_submit_success_scenario() throws Exception {
      String requestBody = getRequestBodyClaims();
      MvcResult mvcResult = mockMvc.perform(post("/"+ api_version + "/claim/submit").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();
      System.out.println("saaaaaaa" + response.getContentAsString());
      int status = response.getStatus();
      assertEquals(202, status);
  }

  @Test
  public void check_claim_submit_exception_scenario() throws Exception {
      String requestBody = getExceptionRequestBody();
      MvcResult mvcResult = mockMvc.perform(post("/"+ api_version + "/claim/submit").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();
      int status = response.getStatus();
      assertEquals(500, status);
  }

  @Test
  public void check_claim_on_submit_success_scenario() throws Exception {
      when(headerAuditService.search(any())).thenReturn(Arrays.asList(new HeaderAudit("AUDIT", new Object(), new Object(), "1-2799b6a4-cf2d-45fe-a5e1-5f1c82979e0d", "93f908ba", "26b1060c-1e83-4600-9612-ea31e0ca5091", "1e83-460a-4f0b-b016-c22d820674e1", "5e934f90-111d-4f0b-b016-c22d820674e1", "2022-01-06T09:50:23+00", new Long("1642781095099"), new Long("1642781095099"), new Long("1642781095099"), "/v1/claim/submit", "59cefda2-a4cc-4795-95f3-fb9e82e21cef", "request.queued", Arrays.asList("provider"), Arrays.asList("payor"))));
      String requestBody = getRequestBodyClaims();
      MvcResult mvcResult = mockMvc.perform(post("/"+ api_version + "/claim/on_submit").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();
      int status = response.getStatus();
      assertEquals(202, status);
  }

  @Test
  public void check_claim_on_submit_exception_scenario() throws Exception {
      String requestBody = getExceptionRequestBody();
      MvcResult mvcResult = mockMvc.perform(post("/"+ api_version + "/claim/on_submit").content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn();
      MockHttpServletResponse response = mvcResult.getResponse();
      int status = response.getStatus();
      assertEquals(500, status);
  }

}