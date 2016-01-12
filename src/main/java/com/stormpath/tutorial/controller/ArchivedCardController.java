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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ArchivedCardController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/archived-cards")
    public List<ArchivedCard> greeting(HttpServletRequest req) {
        Account account = AccountResolver.INSTANCE.getAccount(req);

        if (account == null) { throw new ForbiddenException(); }

        List<ArchivedCard> archivedCardList = new ArrayList<ArchivedCard>();
        archivedCardList.add( new ArchivedCard("Mike is cool", "Date1"));
        archivedCardList.add( new ArchivedCard("Ted is dum", "Date1"));
        archivedCardList.add( new ArchivedCard("Fred is not Ted", "Date3"));

        return archivedCardList;
    }
}