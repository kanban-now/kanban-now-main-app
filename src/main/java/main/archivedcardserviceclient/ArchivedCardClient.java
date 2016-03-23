package main.archivedcardserviceclient;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface ArchivedCardClient {

    @RequestLine("GET /archivedCards/{userId}/")
    List<Card> cards(@Param("userId") String userId);

    @RequestLine("POST /archivedCards/{userId}/")
    @Headers("Content-Type: application/json")
    Card createCard(@Param("userId") String userId, Card aCard);

    @RequestLine("DELETE /archivedCards/{userId}/{cardId}")
    void deleteCard(@Param("userId") String userId, @Param("cardId") Long cardId);

}
