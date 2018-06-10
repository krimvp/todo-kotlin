package co.krimvp.todolist

import co.krimvp.todolist.todo.TodoEntity
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.h2.engine.User
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.BeforeClass
import org.junit.Ignore
import org.springframework.boot.jackson.JsonObjectDeserializer
import org.springframework.http.*
import java.time.ZonedDateTime
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient()
class TodoEndpointIntegrationTest {


    @Autowired
    lateinit var baseClient: TestRestTemplate

    private var mapper: ObjectMapper = ObjectMapper()

    @Test
    fun `GET todos should return an array`(){
        val response = this.baseClient.getForEntity("/todos", List::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        println(response.body)
        assert(response.body?.isNotEmpty()!!)
    }

    @Test
    fun `GET a todo by its id should return the details`(){
        val response = this.baseClient.getForEntity("/todo/1", TodoEntity::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    // Not the best way to do it using a Rest convention but ok...
    fun `POST a todo should return 201 CREATED and with the new created element`(){
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val baseRequest = HttpEntity("{\"title\":\"Some posted message\", \"description\":\"Some important description\"}", headers)
        val response = baseClient.postForEntity("/todos", baseRequest, Object::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat((response.body as Map<String, Any>).get("id")).isNotNull()
    }

    @Test
    fun `DELETE an existing todo should return 204 NO CONTENT`(){
        val request = HttpEntity<Object>(HttpHeaders())
        val response = baseClient.exchange("/todo/0", HttpMethod.DELETE, request, Any::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
        assertThat(response.body).isNull()
    }

    @Test
    fun `after deleting an existing todo, GET should return empty object`(){
        val request = HttpEntity<Object>(HttpHeaders())
        baseClient.exchange("/todo/2", HttpMethod.DELETE, request, Any::class.java)
        val response = this.baseClient.getForEntity("/todo/2", TodoEntity::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
        assertThat(response.body).isNull()
    }

    @Test
    fun `PUT an existing todo should return 200 OK and emtpy body`(){
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<JsonNode>(mapper.readTree("{\"title\":\"Some posted message\"," +
                "\"description\":\"Some important description\"," +
                "\"isFinished\":true}"),headers)
        val response = baseClient.exchange("/todo/0", HttpMethod.PUT, request, Object::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isNull()
    }



    @Test
    fun `after updating an existing todo, GET should return the updated object details`(){
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>("{\"title\":\"Some put message\"," +
                "\"description\":\"Some important description\"," +
                "\"isFinished\":true}",headers)

        baseClient.exchange("/todo/1", HttpMethod.PUT, request, Object::class.java)
        baseClient.exchange("/todo/2", HttpMethod.DELETE, request, Any::class.java)
        val response = this.baseClient.getForEntity("/todo/1", Object::class.java)
        assertThat((response.body as Map<String, Any>).get("title")).isEqualTo("Some put message")
    }

    @Test
    fun `trying to PUT (or POST) without the mandatory fields results in 404 NOT FOUND`(){
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>("{\"titl\":\"Some put message\"," +
                "\"description\":\"Some important description\"," +
                "\"isFinished\":true}",headers)

        val response = baseClient.exchange("/todo/1", HttpMethod.PUT, request, Object::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `trying to DELETE an unexisting todo should return 404`(){
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val response = baseClient.exchange("/todo/8", HttpMethod.DELETE, HttpEntity<Object>(headers), Any::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }



}
