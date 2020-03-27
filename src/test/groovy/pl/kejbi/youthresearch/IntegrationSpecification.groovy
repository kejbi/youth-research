package pl.kejbi.youthresearch

import groovy.json.JsonBuilder
import groovyx.net.http.RESTClient
import org.apache.groovy.json.internal.LazyMap
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import pl.kejbi.youthresearch.model.User
import pl.kejbi.youthresearch.repository.MemberRepository
import pl.kejbi.youthresearch.repository.TutorRepository
import pl.kejbi.youthresearch.repository.TutorsGroupJoinRequestRepository
import pl.kejbi.youthresearch.security.JwtProvider
import spock.lang.Specification


@SpringBootTest(classes = [YouthresearchApplication],
        properties = "application.environment=integration",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationSpecification extends Specification {

    private Logger log = LoggerFactory.getLogger(IntegrationSpecification.getClass())

    @Value('${local.server.port}')
    protected int port

    @Autowired
    protected JwtProvider jwtProvider

    @Autowired
    protected JdbcTemplate jdbcTemplate

    @Autowired
    protected TutorRepository tutorRepository

    @Autowired
    protected MemberRepository memberRepository

    @Autowired
    protected TutorsGroupJoinRequestRepository tutorsGroupJoinRequestRepository

    protected RESTClient restClient

    def setup() {

        cleanUpTables()
        restClient = new RESTClient("http://localhost:$port", "application/json")
        setHeaders()
        restClient.handler.failure = restClient.handler.success
    }

    def setHeaders(Map<String, String> headers = [:]) {

        restClient.setHeaders(["Content-Type" : "application/json"] + headers)
    }

    def authenticate(User user) {

        def username = user.getUsername()
        def token = jwtProvider.generateToken(username)
        def header = ["Authorization": "Bearer " + token]
        setHeaders(header)
    }

    def postRequest(String endpoint, String content) {

        def response = restClient.post([
                path: endpoint,
                body: content
        ])

        if (response.status == 500) {
            log.error(new JsonBuilder(response).toPrettyString())
        }

        return response
    }

    def getRequest(String endpoint) {

        def response = restClient.get([
                path: endpoint
        ])

        if (response.status == 500) {
            log.error(new JsonBuilder(response).toPrettyString())
        }

        return response
    }

    def deleteRequest(String endpoint) {

        def response = restClient.delete([
                path: endpoint,
        ])

        if (response.status == 500) {
            log.error(new JsonBuilder(response).toPrettyString())
        }

        return response
    }

    def putRequest(String endpoint, String content) {

        def response = restClient.put([
                path: endpoint,
                body: content
        ])

        if (response.status == 500) {
            log.error(new JsonBuilder(response).toPrettyString())
        }

        return response
    }

    def cleanUpTables() {

        tutorRepository.deleteAll()
        memberRepository.deleteAll()
        tutorsGroupJoinRequestRepository.deleteAll()
    }

}
