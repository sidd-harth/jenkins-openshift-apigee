package com.techprimers.test.testcontrollerexample;


import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.openshift.jenkins.Controller;
import com.openshift.jenkins.CheckService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CheckResourceTest {


    private MockMvc mockMvc;

    @Mock
    private CheckService checkService;

    @InjectMocks
    private Controller checkResource;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(checkResource)
                .build();
    }

    @Test
    public void testCheck() throws Exception {

        when(checkService.check()).thenReturn("hello");

        mockMvc.perform(get("/check"))
                .andExpect(status().isOk());

        verify(checkService).check();
    }

    @Test
    public void testOpenshiftdJson() throws Exception {
        mockMvc.perform(get("/check/openshift")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.is("Openshift-Jenkins")))
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
    }

}