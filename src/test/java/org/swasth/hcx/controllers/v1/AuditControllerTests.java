package org.swasth.hcx.controllers.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.swasth.hcx.utils.JSONUtils;
import org.swasth.hcx.dto.SearchRequestDTO;
import org.swasth.hcx.controllers.BaseSpec;

public class AuditControllerTests extends BaseSpec{


    @Value("${hcx_application.api_version}")
    private String api_version;


    @Test
    public void audit_search_success_scenario() throws Exception {
        String uri = "/"+ api_version + "/audit/search";
        SearchRequestDTO searchrequest = new SearchRequestDTO();
        HashMap<String,String> filters = new HashMap<String, String>();
        filters.put("status","submitted");
        searchrequest.setFilters(filters);
        searchrequest.setLimit(10);
        searchrequest.setOffset(0);
        MvcResult mvcResult =  mockMvc.perform(post(uri).content(JSONUtils.serialize(searchrequest)).contentType(MediaType.APPLICATION_JSON)).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(200, status);
    }
}