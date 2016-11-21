package com.ezdi.mt.worklist;

import com.ezdi.mt.worklist.application.WorklistApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by EZDI\atul.r on 3/6/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({WorklistApplication.class})
@WebAppConfiguration
public class WorkListManagementTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getTranscriptDocumentListByWorkType() throws Exception {
        String successString = "{\n\"success\": true,\n\"code\": 200,\n\"message\": \"success\"\n}";
        String requestString = "{  \"pagination\": {    \"start\": 1  },  \"sort\": {    \"key\": \"worktype_value\"  },  \"other\": [    {      \"categoryName\": \"new_file\",      \"categoryType\": null,      \"suggestionString\": null,      \"suggestionLabel\": null,\n" +
                "      \"suggestionCategory\": null,      \"categoryOptions\": [        {          \"optionName\": null,\n" +
                "          \"optionValue\": null        }      ]    }  ],  \"userId\" : 12}";

        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> successResult = mapper.readValue(successString, new TypeReference<LinkedHashMap<String, Object>>() {
        });

        mockMvc.perform(post("/search/getWorklist")
                .content(requestString)
                .contentType(contentType))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header").value(successResult))
        ;
    }

    @Test
    public void getTranscriptDocumentListByTat() throws Exception {
        String successString = "{\n\"success\": true,\n\"code\": 200,\n\"message\": \"success\"\n}";
        String requestString = "{  \"pagination\": {    \"start\": 1  },  \"sort\": {    \"key\": \"remaining_tat\"  },  \"other\": [    {      \"categoryName\": \"new_file\",      \"categoryType\": null,      \"suggestionString\": null,      \"suggestionLabel\": null,\n" +
                "      \"suggestionCategory\": null,      \"categoryOptions\": [        {          \"optionName\": null,\n" +
                "          \"optionValue\": null        }      ]    }  ],  \"userId\" : 12}";

        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> successResult = mapper.readValue(successString, new TypeReference<LinkedHashMap<String, Object>>() {
        });

        mockMvc.perform(post("/search/getWorklist")
                .content(requestString)
                .contentType(contentType))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header").value(successResult))
        ;
    }

    @Test
    public void getTranscriptDocumentListByHospitalName() throws Exception {
        String successString = "{\n\"success\": true,\n\"code\": 200,\n\"message\": \"success\"\n}";
        String requestString = "{  \"pagination\": {    \"start\": 1  },  \"sort\": {    \"key\": \"hospital_value\"  },  \"other\": [    {      \"categoryName\": \"new_file\",      \"categoryType\": null,      \"suggestionString\": null,      \"suggestionLabel\": null,\n" +
                "      \"suggestionCategory\": null,      \"categoryOptions\": [        {          \"optionName\": null,\n" +
                "          \"optionValue\": null        }      ]    }  ],  \"userId\" : 12}";

        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> successResult = mapper.readValue(successString, new TypeReference<LinkedHashMap<String, Object>>() {
        });

        mockMvc.perform(post("/search/getWorklist")
                .content(requestString)
                .contentType(contentType))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header").value(successResult))
        ;
    }

    @Test
    public void getTranscriptDocumentListByPhysicianName() throws Exception {
        String successString = "{\n\"success\": true,\n\"code\": 200,\n\"message\": \"success\"\n}";
        String requestString = "{  \"pagination\": {    \"start\": 1  },  \"sort\": {    \"key\": \"dictating_physician\"  },  \"other\": [    {      \"categoryName\": \"new_file\",      \"categoryType\": null,      \"suggestionString\": null,      \"suggestionLabel\": null,\n" +
                "      \"suggestionCategory\": null,      \"categoryOptions\": [        {          \"optionName\": null,\n" +
                "          \"optionValue\": null        }      ]    }  ],  \"userId\" : 12}";

        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> successResult = mapper.readValue(successString, new TypeReference<LinkedHashMap<String, Object>>() {
        });

        mockMvc.perform(post("/search/getWorklist")
                .content(requestString)
                .contentType(contentType))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header").value(successResult))
        ;
    }

    @Test
    public void getSortList() throws Exception {
        String successString = "{\n\"success\": true,\n\"code\": 200,\n\"message\": \"success\"\n}";
       /* String requestString = "{  \"pagination\": {    \"start\": 1  },  \"sort\": {    \"key\": \"remaining_tat\"  },  \"other\": [    {      \"categoryName\": \"new_file\",      \"categoryType\": null,      \"suggestionString\": null,      \"suggestionLabel\": null,\n" +
                "      \"suggestionCategory\": null,      \"categoryOptions\": [        {          \"optionName\": null,\n" +
                "          \"optionValue\": null        }      ]    }  ],  \"userId\" : 12}";*/

        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> successResult = mapper.readValue(successString, new TypeReference<LinkedHashMap<String, Object>>() {
        });

        mockMvc.perform(get("/search/getSortValues")
                .contentType(contentType))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header").value(successResult))
        ;
    }
}
