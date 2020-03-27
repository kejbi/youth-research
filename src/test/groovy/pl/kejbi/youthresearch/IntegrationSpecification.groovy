package pl.kejbi.youthresearch

import groovy.json.JsonBuilder
import groovyx.net.http.RESTClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import pl.kejbi.youthresearch.security.JwtProvider
import spock.lang.Ignore
import spock.lang.Specification


@SpringBootTest(classes = [YouthresearchApplication],
        properties = "application.environment=integration",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationSpecification extends Specification {

    private Logger log = LoggerFactory.getLogger(IntegrationSpecification.getClass())

    @Autowired
    protected JwtProvider jwtProvider

    @Autowired
    protected JdbcTemplate jdbcTemplate

    protected RESTClient restClient

    def setup() {

        restClient = new RESTClient("http://localhost:8080", "application/json")
        setHeaders()
        restClient.handler.failure = restClient.handler.success
    }

    def setHeaders(Map<String, String> headers = [:]) {

        restClient.setHeaders(["Content-Type" : "application/json"] + headers)
    }

    def postRequest(String endpoint, String content, Boolean errorExpected = false) {

        def response = restClient.post([
                path: endpoint,
                body: content
        ])

        log.info(response)

    }

    def "should return smth"(){
        given:
        def registerBody = ("""
            {
                "username": "member3",
                "name": " sikiliki",
                "surname": "basdf",
                "email": "asdf@w.pl",
                "password": "asdasdf",
                "secret": "sadgafg",
                "tutor": false}
        """)

        when:
        def response = postRequest("/auth/register", registerBody)

        then:
        response.status == 200
    }
}
