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

package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;
import com.stormpath.tutorial.exception.ForbiddenException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.List;


@RestController
public class ArchivedCardController {

    private static String accessKeyId  = System.getenv("stormpath_apiKey_id");
    private static String secretKey = System.getenv("stormpath_apiKey_secret");
    private static String userAndPassword = accessKeyId + ":" + secretKey;
    private static String userCredentials = userAndPassword;
    private static String basicAuth = "Basic " + DatatypeConverter.printBase64Binary(userCredentials.getBytes());

    @RequestMapping("/api/archived-cards")
    public List<ArchivedCard> greeting(HttpServletRequest req) {
        Account account = AccountResolver.INSTANCE.getAccount(req);
        if (account == null) { throw new ForbiddenException(); }

        String url = "https://archive-card-service-2-development.cfapps.io:443/archivedCards";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", basicAuth);
        HttpEntity entity = new HttpEntity(headers);
        HttpEntity<ArchivedCard[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, ArchivedCard[].class);
        ArchivedCard[] archivedCardArray = response.getBody();
        List<ArchivedCard> archivedCardList = Arrays.asList(archivedCardArray);
        return archivedCardList;
    }
}