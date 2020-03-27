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

    @Unroll
    def "should return UserDTO with 200 status code"() {

        given:
        def registerRequestBody = new JsonBuilder()
        registerRequestBody username: username,
                            name: "John",
                            surname: "Smith",
                            email: "smith@w.com",
                            password: "passwd",
                            secret: "AwdGb3",
                            tutor: tutor

        when:
        def response = postRequest("/auth/register", registerRequestBody.toPrettyString())

        then:
        response.status == 200
        response.data.username == username

        where:
        username | tutor
        "tutor1" | true
        "member1" | false
    }

    @Unroll
    def "should not accept invalid request"() {

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
        "" | "" | "" | "" | "" | "AwdGb3" | true | "Invalid registration input"
        "John" | "John" | "Smith" | "withoutat.pl" | "passwd" | "AwdGb3" | true | "Invalid registration input"
        "John" | "John" | "Smith" | "with@" | "passwd" | "AwdGb3" | true | "Invalid registration input"
        "John" | "John" | "Smith" | "with@mail.com" | "passwd" | "AdGb" | true | "Incorrect secret code"
        "John" | "John" | "Smith" | "with@" | "passwd" | null | true | "Invalid registration input"
    }

    def "should persist member"() {

        given:
        def registerRequestBody = new JsonBuilder()
        registerRequestBody username: "member1",
                name: "John",
                surname: "Smith",
                email: "smith@w.com",
                password: "passwd",
                tutor: false

        when:
        postRequest("/auth/register", registerRequestBody.toPrettyString())

        then:
        memberRepository.findAll().size() == 1
        memberRepository.findByUsername("member1").isPresent()
    }

    @Unroll
    def "should not accept existing username or email"() {

        given:
        UserBuilder.buildTutor(Map.of("email", "email@e.pl", "username", "user1"), tutorRepository)
        UserBuilder.buildMember(Map.of("email", "email2@e.pl", "username", "user2"), memberRepository)

        and:
        def registerRequestBody = new JsonBuilder()
        registerRequestBody username: username,
                name: "John",
                surname: "Smith",
                email: email,
                password: "passwd",
                secret: "AwdGb3",
                tutor: tutor

        when:
        def response = postRequest("/auth/register", registerRequestBody.toPrettyString())

        then:
        response.status == 400
        response.data.message == message

        where:
        username | email | tutor | message
        "user1" | "different@e.pl" | true | "Username or email already taken: " + username + " or " + email
        "user1" | "different@e.pl" | false | "Username or email already taken: " + username + " or " + email
        "user2" | "different@e.pl" | true | "Username or email already taken: " + username + " or " + email
        "user2" | "different@e.pl" | false | "Username or email already taken: " + username + " or " + email
        "different" | "email@e.pl" | true | "Username or email already taken: " + username + " or " + email
        "different" | "email@e.pl" | false | "Username or email already taken: " + username + " or " + email
        "different" | "email2@e.pl" | true | "Username or email already taken: " + username + " or " + email
        "different" | "email2@e.pl" | false | "Username or email already taken: " + username + " or " + email
    }


    def "should return jwt, username and tutor role"() {

        given:
        UserBuilder.buildTutor(Map.of(), tutorRepository)

        and:
        def loginRequestBody = new JsonBuilder()
        loginRequestBody username: "Tutor1",
               password: "tutor1"

        when:
        def response = postRequest("/auth/login", loginRequestBody.toPrettyString())

        then:
        response.status == 200
        response.data.token != null
        response.data.role == "tutor"
    }

    def "should return jwt, username and member role"() {

        given:
        UserBuilder.buildMember(Map.of(), memberRepository)

        and:
        def loginRequestBody = new JsonBuilder()
        loginRequestBody username: "Member1",
                password: "member1"

        when:
        def response = postRequest("/auth/login", loginRequestBody.toPrettyString())

        then:
        response.status == 200
        response.data.token != null
        response.data.role == "member"
    }
}
