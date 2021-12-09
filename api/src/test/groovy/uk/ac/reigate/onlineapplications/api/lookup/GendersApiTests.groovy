package uk.ac.reigate.onlineapplications.api.lookup

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.mockito.Mockito.when
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc

import uk.ac.reigate.onlineapplications.domain.lookup.Gender
import uk.ac.reigate.onlineapplications.service.lookup.GenderService

@WebMvcTest(GendersApi.class)
@AutoConfigureRestDocs
class GendersApiTests extends GeneralApiTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GenderService service;

	// Sample data
	Gender one = new Gender(id: 1, code: 'M', description: 'Male')
	Gender two = new Gender(id: 2, code: 'F', description: 'Female')

	@BeforeEach
	public void setupService() {
		when(service.findAll()).thenReturn([one, two])
	}

	@WithMockUser(value = "user@reigate.ac.uk", password = "password")
    @Test
	public void shouldReturnAnArrayOfJson() throws Exception {
		this.mockMvc.perform(get("/genders"))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$', hasSize(2)))
				.andExpect(jsonPath('$[0].id', is(one.id)))
				.andExpect(jsonPath('$[0].code', is(one.code)))
				.andExpect(jsonPath('$[0].description', is(one.description)))
				.andDo(print())
				.andDo(document("genders-api-get-all"));
	}

}
