package com.xantrix.webapp.UnitTest;

//import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xantrix.webapp.Application;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest
{
    private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}
	
	String JsonData =  " {\n" + 
			"        \"userId\": \"Mario\",\n" + 
			"        \"password\": \"VerySecretPwd\",\n" + 
			"        \"attivo\": \"Si\",\n" + 
			"        \"ruoli\": [\n" + 
			"            \"USER\"\n" + 
			"        ]\n" + 
			"}";
	
	@Test
	public void A_testInsUtente() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/utenti/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	public void B_listUserByUserId() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/utenti/cerca/userid/Mario")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				  
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.userId").exists())
				.andExpect(jsonPath("$.userId").value("Mario"))
				.andExpect(jsonPath("$.password").exists())
				//.andExpect(jsonPath("$.password").value(""))
				.andExpect(jsonPath("$.attivo").exists())
				.andExpect(jsonPath("$.attivo").value("Si"))
				  
				.andExpect(jsonPath("$.ruoli[0]").exists())
				.andExpect(jsonPath("$.ruoli[0]").value("USER")) 
				.andDo(print());
	}

	
	@Test
	public void C_testDelUtente() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/utenti/elimina/Mario")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value(200))
				.andExpect(jsonPath("$.message").value("Eliminazione Utente Mario Eseguita Con Successo"))
				.andDo(print());
	}
	
	
	String JsonData2 = " {\n" + 
			"        \"userId\": \"Nicola\",\n" + 
			"        \"password\": \"SimS@l@Bin\",\n" + 
			"        \"attivo\": \"Si\",\n" + 
			"        \"ruoli\": [\n" + 
			"            \"USER\",\n" + 
			"            \"ADMIN\"\n" + 
			"        ]\n" + 
			"}";
	
	@Test
	public void D_testInsUtente() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/utenti/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData2)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	
	@Test
	public void E_testDelUtente() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/utenti/elimina/Nicola")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value(200))
				.andExpect(jsonPath("$.message").value("Eliminazione Utente Nicola Eseguita Con Successo"))
				.andDo(print());
	}
	
}


