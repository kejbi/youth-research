package pl.kejbi.youthresearch.tutors_group_test

import groovyx.net.http.URIBuilder
import org.springframework.beans.factory.annotation.Autowired
import pl.kejbi.youthresearch.IntegrationSpecification
import pl.kejbi.youthresearch.model.Member
import pl.kejbi.youthresearch.model.Tutor
import pl.kejbi.youthresearch.model.TutorsGroup
import pl.kejbi.youthresearch.model.TutorsGroupJoinRequest
import pl.kejbi.youthresearch.repository.TutorsGroupJoinRequestRepository
import pl.kejbi.youthresearch.repository.TutorsGroupRepository
import pl.kejbi.youthresearch.utils.GroupBuilder
import pl.kejbi.youthresearch.utils.JoinRequestBuilder
import pl.kejbi.youthresearch.utils.UserBuilder

class TutorsGroupTest extends IntegrationSpecification {

    @Autowired
    private TutorsGroupRepository tutorsGroupRepository

    @Autowired
    private TutorsGroupJoinRequestRepository tutorsGroupJoinRequestRepository

    def "should return all groups without member"(){

        given:
        Member member = UserBuilder.buildMember(Map.of(), memberRepository)
        Tutor tutor = UserBuilder.buildTutor(Map.of(), tutorRepository)
        authenticate(member)

        and:
        TutorsGroup group = GroupBuilder.buildGroup(Map.of("tutor", tutor, "name", "Group1"), tutorsGroupRepository)
        TutorsGroup group2 = GroupBuilder.buildGroup(Map.of("tutor", tutor, "name", "Group2"), tutorsGroupRepository)
        TutorsGroup group3 = GroupBuilder.buildGroup(Map.of("tutor", tutor, "name", "Group3"), tutorsGroupRepository)
        group2.addMember(member)
        tutorsGroupRepository.save(group2)

        when:
        def response = getRequest("/tutorsgroup/joinable")


        then:
        response.status == 200
        response.data[0].name == "Group1"
        response.data[1].name == "Group3"
        response.data.size == 2
    }

    def "should add member to group after acceptation"() {

        given:
        Member member = UserBuilder.buildMember(Map.of("id", 1111L), memberRepository)
        Tutor tutor = UserBuilder.buildTutor(Map.of("id", 2222L), tutorRepository)
        authenticate(tutor)

        and:
        TutorsGroup group = GroupBuilder.buildGroup(Map.of("tutor", tutor, "name", "Group1"), tutorsGroupRepository)
        TutorsGroupJoinRequest request = JoinRequestBuilder.buildRequest(Map.of("group", group, "member", member), tutorsGroupJoinRequestRepository)

        when:
        def response = putRequest("/tutorsgroup/request/" + request.getId(), "")

        then:
        response.status == 200
        tutorsGroupJoinRequestRepository.findById(request.getId()).get().isAccepted()
        tutorsGroupRepository.findByIdWithMemberFetched(group.getId()).get().getMembers().contains(member)
    }

    def "should return only not accepted requests"() {

        given:
        Member member = UserBuilder.buildMember(Map.of("id", 1111L), memberRepository)
        Member member2 = UserBuilder.buildMember(Map.of("email", "newemail@w.pl", "id", 3333L, "username", "returnMember"), memberRepository)
        Tutor tutor = UserBuilder.buildTutor(Map.of("id", 2222L), tutorRepository)
        authenticate(tutor)

        and:
        TutorsGroup group = GroupBuilder.buildGroup(Map.of("tutor", tutor, "name", "Group1"), tutorsGroupRepository)
        TutorsGroupJoinRequest request = JoinRequestBuilder.buildRequest(Map.of("group", group, "member", member, "accepted", true), tutorsGroupJoinRequestRepository)
        TutorsGroupJoinRequest request2 = JoinRequestBuilder.buildRequest(Map.of("group", group, "member", member2), tutorsGroupJoinRequestRepository)

        when:
        def response = getRequest("/tutorsgroup/request?groupId=" + group.getId())

        then:
        response.status == 200
        response.data.size == 1
        response.data[0].groupName == "Group1"
        response.data[0].username == "returnMember"
    }

}
