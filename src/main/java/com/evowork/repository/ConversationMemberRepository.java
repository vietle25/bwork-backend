package com.evowork.repository;

import com.evowork.entity.ConversationMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationMemberRepository extends CrudRepository<ConversationMember, Long> {

    @Query(value = "SELECT cm.* " +
            "FROM   conversation_member cm " +
            "       JOIN conversation c " +
            "         ON cm.conversation_id = c.id " +
            "WHERE  c.id = :conversationId " +
            "       AND cm.user_id = :userId ",
            nativeQuery = true)
    ConversationMember getMemberChatInConversation(@Param("conversationId") Long id,
                                                   @Param("userId") Long idUser);

    @Query(value = "SELECT cm.* " +
            "FROM   conversation_member cm " +
            "       JOIN conversation c " +
            "         ON cm.conversation_id = c.id " +
            "WHERE  c.id = :conversationId ",
            nativeQuery = true)
    List<ConversationMember> getConversationMemberById(@Param("conversationId") Long conversationId);

    @Query(value = "SELECT com.* " +
            "FROM   conversation co " +
            "       JOIN conversation_member cm " +
            "            JOIN conversation_member com " +
            "              ON cm.conversation_id = com.conversation_id " +
            "         ON co.id = cm.conversation_id \n" +
            "WHERE  co.status != :status " +
            "       AND cm.conversation_name_search LIKE :paramsSearch " +
            "       AND cm.deleted_conversation = false " +
            "       AND cm.user_id = :userId " +
            "       AND com.user_id != :userId ",
            nativeQuery = true)
    List<ConversationMember> searchConversation(@Param("paramsSearch") String paramsSearch,
                                                @Param("userId") Long userId, @Param("status") int status);

    @Query(value = "SELECT cm.conversation_id " +
            "FROM   conversation_member cm " +
            "WHERE  cm.conversation_id = :conversationId " +
            "       AND cm.user_id = :userId " +
            "       AND cm.deleted_conversation = false) ",
            nativeQuery = true)
    Long checkExistConversationGroup(@Param("conversationId") Long conversationId, @Param("userId") Long userId);

    @Query(value = "SELECT cm.conversation_id " +
            "FROM   conversation_member cm " +
            "       JOIN conversation_member cmb " +
            "         ON cmb.conversation_id = cm.conversation_id " +
            "WHERE  (cmb.user_id = :userChatId " +
            "       AND cmb.deleted_conversation = false) " +
            "       AND (cm.user_id = :userId " +
            "       AND cm.deleted_conversation = false) ",
            nativeQuery = true)
    Long checkExistConversation(@Param("userChatId") Long userChatId, @Param("userId") Long userId);

    @Query(value = "select count(*) from conversation_member where user_id = :userId and deleted_conversation = " +
            "false ",
            nativeQuery = true)
    Integer checkConversationActive(@Param("userId") Long id);

    @Query(value = "SELECT (total_member.rows - exit_member.rows) AS member_current "
            + "FROM "
            + "     (SELECT count(*) AS rows "
            + "     FROM conversation_member cm "
            + "     WHERE cm.conversation_id = :conversationId) AS total_member "
            + "CROSS JOIN "
            + "    (SELECT count(*) AS rows "
            + "    FROM conversation_member cm "
            + "    WHERE cm.conversation_id = :conversationId "
            + "      AND deleted_conversation = true) AS exit_member ",
            nativeQuery = true)
    Integer checkMemberCurrentConversation(@Param("conversationId") Long conversationId);
}
