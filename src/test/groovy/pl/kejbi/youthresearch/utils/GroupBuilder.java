package pl.kejbi.youthresearch.utils;

import pl.kejbi.youthresearch.model.Tutor;
import pl.kejbi.youthresearch.model.TutorsGroup;
import pl.kejbi.youthresearch.repository.TutorsGroupRepository;

import java.util.Map;

public class GroupBuilder {

    public static TutorsGroup buildGroup(Map<String,Object> props, TutorsGroupRepository repository) {

        Long id = props.containsKey("id") ? (Long) props.get("id") : 1L;
        String name = props.containsKey("name") ? (String) props.get("name") : "Grupa 1";
        Tutor tutor = (Tutor) props.get("tutor");

        TutorsGroup tutorsGroup = new TutorsGroup();
        tutorsGroup.setId(id);
        tutorsGroup.setName(name);
        tutorsGroup.setTutor(tutor);

        return repository.save(tutorsGroup);
    }
}
