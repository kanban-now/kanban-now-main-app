/*
 * Copyright 2015, Michael Alan DuVall
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package main.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.impl.account.DefaultAccount;
import com.stormpath.sdk.servlet.account.AccountResolver;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import main.archivedcardserviceclient.ArchivedCardClient;
import main.archivedcardserviceclient.Card;
import main.exception.ForbiddenException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.List;


@RestController
public class ArchivedCardController {

    private static String kanbanNowServicesAccessKeyId  = System.getenv("kanban_now_services_apiKey_id");
    private static String kanbanNowServicesSecretKey = System.getenv("kanban_now_services_apiKey_secret");

    private static String userAndPassword = kanbanNowServicesAccessKeyId + ":" + kanbanNowServicesSecretKey;
    private static String userCredentials = userAndPassword;
    private static String basicAuth = "Basic " + DatatypeConverter.printBase64Binary(userCredentials.getBytes());

    private static String archiveCardServiceBaseUrl = System.getenv("archive_card_service_base_url");

    @RequestMapping("/api/archived-cards")
    public List<Card> greeting(
            HttpServletRequest req) {
        Account account = AccountResolver.INSTANCE.getAccount(req);
        if (account == null) { throw new ForbiddenException(); }

        String userStormpathId = getStormpathIdForAccount(account);
//        String url = archiveCardServiceBaseUrl + "/" + userStormpathId;

//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", basicAuth);
//        HttpEntity entity = new HttpEntity(headers);
//        HttpEntity<ArchivedCard[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, ArchivedCard[].class);
//        ArchivedCard[] archivedCardArray = response.getBody();
//        List<ArchivedCard> archivedCardList = Arrays.asList(archivedCardArray);
//        return archivedCardList;

        ArchivedCardClient archivedCardClient = Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .requestInterceptor(new BasicAuthRequestInterceptor(kanbanNowServicesAccessKeyId, kanbanNowServicesSecretKey))
                .target(ArchivedCardClient.class, archiveCardServiceBaseUrl);


        List<Card> cardList = archivedCardClient.cards(userStormpathId);
        return cardList;

    }

    @RequestMapping("/api/new-archived-cards")
    public List<ArchivedCard> newArchivedCards(
            HttpServletRequest req,
            @RequestParam(value="pageNumber", required= false) Integer pageNumber,
            @RequestParam(value="pageSize", required = false) Integer pageSize) {
        Account account = AccountResolver.INSTANCE.getAccount(req);
        if (account == null) { throw new ForbiddenException(); }


        String userStormpathId = getStormpathIdForAccount(account);
        String url = archiveCardServiceBaseUrl + "/" + userStormpathId;
        if(pageNumber != null && pageSize != null) {
            pageNumber = pageNumber - 1;
            url += "?pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        }


//        Update here to use FeignClient
//                Update FeignClient to call new paged endpoint that returns paging data
//                Fixup javascript controller to call this new endpoint and to use the return pagingData


//        String newUrl = archiveCardServiceBaseUrl + "/paged";
//        ArchivedCardClient archivedCardClient = Feign.builder()
//                .decoder(new GsonDecoder())
//                .encoder(new GsonEncoder())
//                .requestInterceptor(new BasicAuthRequestInterceptor(kanbanNowServicesAccessKeyId, kanbanNowServicesSecretKey))
//                .target(ArchivedCardClient.class, newUrl);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", basicAuth);
        HttpEntity entity = new HttpEntity(headers);
        HttpEntity<ArchivedCard[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, ArchivedCard[].class);
        ArchivedCard[] archivedCardArray = response.getBody();
        List<ArchivedCard> archivedCardList = Arrays.asList(archivedCardArray);
        return archivedCardList;
    }



    private String getStormpathIdForAccount(Account account) {
        DefaultAccount defaultAccount = (DefaultAccount) account;
        String href = defaultAccount.getHref();
        int lastSlash = href.lastIndexOf("/");
        String id = href.substring(lastSlash + 1, href.length());
        return id;
    }

}