package dao;

import domain.Tag;
import dto.TagNumberTimesUsed;
import org.hibernate.Session;

import java.util.List;

public interface TagDao extends GenericDao<Tag, Long>{

    List<TagNumberTimesUsed> mostUsedTags(Session session);

    List<Tag> tagsWhereNameStartsLike(Session session, String contains);

}
