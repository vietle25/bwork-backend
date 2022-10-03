package com.ieltshub.repository;

import com.ieltshub.entity.Conversation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends CrudRepository<Conversation, Long> {

//    @Query(value = "SELECT cm.* " +
//            "FROM   conversation_member cm " +
//            "       JOIN conversation c " +
//            "         ON cm.conversation_id = c.id " +
//            "WHERE  c.id = :conversationId " +
//            "       AND cm.user_id != :userId ",
//            nativeQuery = true)
//    ConversationMember getMemberChatInConversation(@Param("conversationId") Long id,
//                                                 @Param("userId") Long idUser);

    @Query(value = "SELECT c.* " +
            "FROM   conversation c " +
            "JOIN conversation_member cm ON c.id = cm.conversation_id " +
            "AND cm.user_id = :userId " +
            "AND cm.deleted_conversation != true " +
            "WHERE  c.id = :conversationId " +
            "AND c.status != :status ",
            nativeQuery = true)
    Conversation getConversationById(@Param("conversationId") Long conversationId, @Param("userId") Long userId, @Param("status") Integer status);
}
