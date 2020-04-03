package pl.kejbi.youthresearch.utils;

import pl.kejbi.youthresearch.model.Member;
import pl.kejbi.youthresearch.model.TutorsGroup;
import pl.kejbi.youthresearch.model.TutorsGroupJoinRequest;
import pl.kejbi.youthresearch.repository.TutorsGroupJoinRequestRepository;

import java.util.Map;

public class JoinRequestBuilder {

    public static TutorsGroupJoinRequest buildRequest(Map<String,Object> props, TutorsGroupJoinRequestRepository repository) {

        Long id = props.containsKey("id") ? (Long) props.get("id") : 1L;
        TutorsGroup group = (TutorsGroup) props.get("group");
        Member member = (Member) props.get("member");
        Boolean accepted = props.containsKey("accepted") ? (Boolean) props.get("accepted") : false;

        TutorsGroupJoinRequest request = new TutorsGroupJoinRequest();
        request.setId(id);
        request.setTutorsGroup(group);
        request.setMember(member);
        request.setAccepted(accepted);

        return repository.save(request);
    }
}
