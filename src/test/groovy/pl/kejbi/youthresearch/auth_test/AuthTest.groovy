package pl.kejbi.youthresearch.auth_test

import groovy.json.JsonBuilder
import pl.kejbi.youthresearch.IntegrationSpecification
import pl.kejbi.youthresearch.utils.UserBuilder
import spock.lang.Unroll

class AuthTest extends IntegrationSpecification {

    def "should persist tutor"() {

        given:
        def registerRequestBody = new JsonBuilder()
        registerRequestBody username: "tutor1",
                            name: "John",
                            surname: "Smith",
                            email: "smith@w.com",
                            password: "passwd",
                            secret: "AwdGb3",
                            tutor: true

        when:
        postRequest("/auth/register", registerRequestBody.toPrettyString())

        then:
        tutorRepository.findAll().size() == 1
        tutorRepository.findByUsername("tutor1").isPresent()
    }

    def "should return UserDTO with 200 status code"() {

        given:
        def registerRequestBody = new JsonBuilder()
        registerRequestBody username: "tutor1",
                name: "John",
                surname: "Smith",
                email: "smith@w.com",
                password: "passwd",
                secret: "AwdGb3",
                tutor: true

        when:
        def response = postRequest("/auth/register", registerRequestBody.toPrettyString())

        then:
        response.status == 200
        response.data.username == "tutor1"
    }

    @Unroll
    def "should not accept invalid request #message"() {

        given:
        def registerRequestBody = new JsonBuilder()
        registerRequestBody username: username,
                name: name,
                surname: surname,
                email: email,
                password: password,
                secret: secret,
                tutor: tutor

        when:
        def response = postRequest("/auth/register", registerRequestBody.toPrettyString())

        then:
        response.status == 400
        response.data.message == message

        where:
        username | name | surname | email | password | secret | tutor | message
        null | null | null | null | null | "AwdGb3" | true | "Invalid registration input"
        "John" | "John" | "Smith" | "withoutat.pl" | "passwd" | "AwdGb3" | true | "Invalid registration input"
        "John" | "John" | "Smith" | "with@" | "passwd" | "AwdGb3" | true | "Invalid registration input"
        "John" | "John" | "Smith" | "with@mail.com" | "passwd" | "AdGb" | true | "Incorrect secret code"
        "John" | "John" | "Smith" | "with@" | "passwd" | null | true | "Invalid registration input"

    }
}
