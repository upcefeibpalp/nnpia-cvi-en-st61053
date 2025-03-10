package nnpia.controllers;

import nnpia.dto.UserRequestDto;
import nnpia.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import nnpia.domain.User;
import static org.hamcrest.Matchers.is;

 // předpokládané umístění repozitáře
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")

class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Vložení jednoho uživatele do H2 databáze před spuštěním testu
        testUser = new User();
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password123");
        testUser = userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        // Smazání všech záznamů z databáze po testu
        userRepository.deleteAll();
    }

    @Test
    public void testGetUserSuccess() throws Exception {
        mockMvc.perform(get("/api/v1/users/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Ověříme, že ID, email a heslo odpovídají očekávaným hodnotám
                .andExpect(jsonPath("$.id", is(testUser.getId())))
                .andExpect(jsonPath("$.email", is("testuser@example.com")))
                .andExpect(jsonPath("$.password", is("password123")));
    }

    @Test
    public void testGetUserNotFound() throws Exception {
        // Nejprve vymažeme všechny záznamy, aby testovali neexistujícího uživatele
        userRepository.deleteAll();

        mockMvc.perform(get("/api/v1/users/" + testUser.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUser() throws Exception {
        // Připravíme DTO pro vytvoření nového uživatele
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setEmail("create@example.com");
        userRequest.setPassword("password");

        // Odeslání POST požadavku na endpoint a ověření výsledku
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                // Očekáváme HTTP status 201 Created
                .andExpect(status().isCreated())
                // Ověříme, že odpověď je ve formátu JSON
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Pomocí JsonPath ověříme, že email a heslo odpovídají očekávaným hodnotám
                .andExpect(jsonPath("$.email", is("create@example.com")))
                .andExpect(jsonPath("$.password", is("password")))
                // Ověříme, že vrácená odpověď obsahuje i pole 'id'
                .andExpect(jsonPath("$.id").exists());
    }


    @Test
    public void testCreateUserConflict() throws Exception {
        // Připravíme DTO se stejným emailem
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setEmail("duplicate@example.com");
        userRequest.setPassword("password");

        // První pokus o vytvoření uživatele – očekáváme úspěšné vytvoření
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("duplicate@example.com")))
                .andExpect(jsonPath("$.id").exists());

        // Druhý pokus o vytvoření uživatele se stejným emailem – očekáváme 409 Conflict
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isConflict());
    }

}