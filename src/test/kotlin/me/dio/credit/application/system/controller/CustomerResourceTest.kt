package me.dio.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.dio.credit.application.system.dto.CustomerDto
import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerResourceTest {
    @Autowired lateinit var customerRepository: CustomerRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    companion object{
        const val URL: String = "/api/customers"
    }

    @BeforeEach fun setup() = customerRepository.deleteAll()

    @AfterEach fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `should create a customer and return 201 status`(){
        //given
        val customerDto: CustomerDto = buildCustomerDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("William"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Elesbão"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("05882648068"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("william@email.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("91030380"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("rua do william"))
            .andDo(MockMvcResultHandlers.print())
    }

    private fun buildCustomerDto(
        firstName: String = "William",
        lastName: String = "Elesbão",
        cpf: String = "05882648068",
        email: String = "william@email.com",
        income: BigDecimal = BigDecimal.valueOf(2000.00),
        password: String = "william123",
        zipCode: String = "91030380",
        street: String = "rua do william"
    ) = CustomerDto(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        income = income,
        password = password,
        zipCode = zipCode,
        street = street
    )
}