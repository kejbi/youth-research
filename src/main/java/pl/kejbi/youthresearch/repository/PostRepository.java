package pl.kejbi.youthresearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kejbi.youthresearch.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
