import com.enzinior.sogo.comment.dto.CommentDto;
import com.enzinior.sogo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByUuid(String uuid);

    //@Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.review.uuid = :reviewUUID")
//    Optional<List<Comment>> searchAllComment(@Param("reviewUUID")String reviewUUiD);

    //@Query(value = "INSERT INTO COMMENTS VALUES (content, userId, parent, review)")
//    int insertComment(CommentDto.Post requestBody);






}
