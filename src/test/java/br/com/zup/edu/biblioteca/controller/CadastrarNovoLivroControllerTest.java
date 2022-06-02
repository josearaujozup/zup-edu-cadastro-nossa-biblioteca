package br.com.zup.edu.biblioteca.controller;

import br.com.zup.edu.biblioteca.model.Livro;
import br.com.zup.edu.biblioteca.repository.LivroRepository;
import br.com.zup.edu.biblioteca.util.MensagemDeErro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class CadastrarNovoLivroControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private LivroRepository repository;

    @BeforeEach
    void setUp(){
        this.repository.deleteAll();
    }

    @Test
    @DisplayName("Deve cadastrar um livro com dados validos")
    void test1() throws Exception{
        //cenario
        LivroRequest livroRequest = new LivroRequest("Inferno", "historia de ficção contada por dan brown",
                "978-8525064141", LocalDate.of(1991, 9, 05));

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        //acao e corretude
        mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isCreated()
                )
                .andExpect(
                        MockMvcResultMatchers.redirectedUrlPattern("http://localhost/livros/*")
                );

        List<Livro> livros = repository.findAll();
        assertEquals(1,livros.size());
    }

    @Test
    @DisplayName("Não deve cadastrar um livro com dados invalidos")
    void test2() throws Exception{
        //cenario
        LivroRequest livroRequest = new LivroRequest(null, null, null, null);

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        assertEquals(4,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo titulo não deve estar em branco",
                "O campo descricao não deve estar em branco",
                "O campo dataPublicacao não deve ser nulo",
                "O campo isbn não deve estar em branco"
        ));
    }

    @Test
    @DisplayName("Não deve cadastrar um livro com titulo maior que 200")
    void test3() throws Exception{
        //cenario
        LivroRequest livroRequest = new LivroRequest("Inferno historia de ficção contada por dan brown | 978-8525064141 | " +
                "Inferno historia de ficção contada por dan brown | 978-8525064141 | Inferno historia de ficção contada por dan brown " +
                "| 978-8525064141 | Inferno historia de ficção contada por dan brown | 978-8525064141", "historia de ficção contada por dan brown",
                "978-8525064141", LocalDate.of(1991, 9, 05));

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        assertEquals(1,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo titulo o comprimento deve ser entre 0 e 200"
        ));
    }

    @Test
    @DisplayName("Não deve cadastrar um livro com descricao maior que 2000")
    void test4() throws Exception{
        //cenario
        LivroRequest livroRequest = new LivroRequest("Inferno", "historia de ficção contada por dan brown " +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown" +
                "historia de ficção contada por dan brown historia de ficção contada por dan brown historia de ficção contada por dan brown",
                "978-8525064141", LocalDate.of(1991, 9, 05));

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        assertEquals(1,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo descricao o comprimento deve ser entre 0 e 2000"
        ));
    }

    @Test
    @DisplayName("Não deve cadastrar um livro com isbn invalido")
    void test5() throws Exception{
        //cenario
        LivroRequest livroRequest = new LivroRequest("Inferno", "historia de ficção contada por dan brown",
                "978-852425064141", LocalDate.of(1991, 9, 05));

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        assertEquals(1,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo isbn ISBN inválido"
        ));
    }

    @Test
    @DisplayName("Não deve cadastrar um livro com data de publicação no futuro")
    void test6() throws Exception{
        //cenario
        LivroRequest livroRequest = new LivroRequest("Inferno", "historia de ficção contada por dan brown",
                "978-8525064141", LocalDate.now().plusDays(5));

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        assertEquals(1,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo dataPublicacao deve ser uma data no passado ou no presente"
        ));
    }
}