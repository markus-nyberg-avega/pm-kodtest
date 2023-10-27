package se.pensionsmyndigheten.library;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import se.pensionsmyndigheten.library.book.BookDto;
import se.pensionsmyndigheten.library.customer.CustomerDto;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = { LibraryApplication.class })
@AutoConfigureMockMvc
class LibraryApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void Should_Get2Books_Given_AuthorIsStiegLarsson() throws Exception {

    MvcResult result = mockMvc.perform(get("/book/author/Stieg Larsson"))
                              .andExpect(status().isOk())
                              .andReturn();

    List<BookDto> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

    assertThat(actual).hasSize(2);
  }

  @Test
  void Should_GetOneBook_Given_TileIsMoln() throws Exception {

    MvcResult result = mockMvc.perform(get("/book/title/Moln"))
                              .andExpect(status().isOk())
                              .andReturn();

    List<BookDto> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

    assertThat(actual).hasSize(1);
  }

  @DisplayName(
          """
          Given fifteen books
          and orderBy is title
          When calling /book/orderBy/
          Then expect fifteen books returned
          in ascending case insensitive order
          """)
  @Test
  void orderByTitle() throws Exception {

    MvcResult result = mockMvc.perform(get("/book/orderBy/title"))
                              .andExpect(status().isOk())
                              .andReturn();

    List<BookDto> actual = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});

    assertThat(actual).extracting(BookDto::getTitle).containsExactly(
            "En komikers uppväxt",
            "En man som heter Ove",
            "En väktares bekännelser",
            "flickan som lekte med elden",
            "Hemsöborna",
            "Kejsaren av Portugallien",
            "Moln",
            "Män som hatar kvinnor",
            "Mårbackasviten",
            "Nils Holgerssons underbara resa",
            "Nobels testamente",
            "Röda rummet",
            "Sprängaren",
            "Studio sex",
            "Torka aldrig tårar utan handskar"
    );
  }

  @DisplayName(
          """
          Given an incorrect order by value
          When calling api
          Then expect BadRequest
          """)
  @Test
  void incorrectOrderByValue() throws Exception {
    MvcResult result = mockMvc
            .perform(get("/book/orderBy/notValid"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMsg =
            result
                    .getResponse()
                    .getContentAsString(StandardCharsets.UTF_8);

    assertThat(errorMsg).isEqualTo("Unsupported order by value, notValid");
  }

  @DisplayName(
          """
          Given a Customer
          When fetching customer
          Then return matching Customer
          """)
  @Test
  void findById() throws Exception {
    // Given
    var customer = createCustomer();
    mockMvc
            .perform(post("/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer)))
            .andReturn();
    MvcResult findResult = mockMvc
            .perform(get("/customer/" + customer.getUuid()))
            .andExpect(status().isOk())
            .andReturn();

    CustomerDto foundCustomer = objectMapper.readValue(findResult.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {});

    // Then
    assertThat(foundCustomer.getUuid()).isEqualTo(customer.getUuid());
  }

  @DisplayName(
          """
          Given a Customer exist
          When calling delete
          Then customer is deleted
          """)
  @Test
  void deleteCustomer() throws Exception {
    var customer = createCustomer();

    // save
    mockMvc
            .perform(post("/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer)));

    // exist
    mockMvc
            .perform(get("/customer/{id}", customer.getUuid()))
            .andExpect(status().isOk());

    // delete
    mockMvc
            .perform(delete("/customer/{id}", customer.getUuid()))
            .andExpect(status().isOk());

    // exist
    mockMvc
            .perform(get("/customer/{id}", customer.getUuid()))
            .andExpect(status().isNotFound());

  }

  @DisplayName(
          """
          Given a invalid UUID
          When fetching customer
          Then return BAD_REQUEST
          """)
  @Test
  void invalidUuid() throws Exception {
    // Given
    MvcResult findResult = mockMvc
            .perform(get("/customer/invalidUuid"))
            .andExpect(status().isBadRequest())
            .andReturn();

    String errorMsg = findResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

    // Then
    assertThat(errorMsg).isEqualTo("Invalid UUID, invalidUuid");
  }

  @DisplayName(
          """
          Given book X is borrowed
          When  book is returned
          Then return true
          """)
  @Test
  void returnBook() throws Exception {
    // Lend book
    mockMvc
            .perform(get("/customer/22d073e4-8b1a-4378-adb8-761e38bf471b/borrow/9789187707469"))
            .andExpect(status().isOk());

    // Return book
    var result = mockMvc
            .perform(get("/customer/22d073e4-8b1a-4378-adb8-761e38bf471b/return/9789187707469"))
            .andExpect(status().isOk())
            .andReturn();

    String resultMsg = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

    // Then
    assertThat(resultMsg).isEqualTo("true");
  }

  @DisplayName(
          """
          Given book X
          When book X is borrowed
          Then return true
          """)
  @Test
  void borrowBook() throws Exception {
    // Lend book
    var result = mockMvc
            .perform(get("/customer/22d073e4-8b1a-4378-adb8-761e38bf471b/borrow/9789187707469"))
            .andExpect(status().isOk())
            .andReturn();

    String resultMsg = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

    // Then
    assertThat(resultMsg).isEqualTo("true");
  }


  @DisplayName(
          """
          Given a customer with uuid X
          When inserting customer with uuid x
          Then return exception
          """)
  @Test
  @Disabled("Not finished")
  void canNotInsertCustomerWithSameUuid() throws Exception {
    // Lend book
    var customer = createCustomer();
    mockMvc
            .perform(post("/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer)));

    mockMvc
            .perform(post("/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer)))
            .andExpect(status().isInternalServerError());
  }


  private static CustomerDto createCustomer() {
    return new CustomerDto(
            UUID.randomUUID(),
            "Sven",
            "Svensson");
  }
}
