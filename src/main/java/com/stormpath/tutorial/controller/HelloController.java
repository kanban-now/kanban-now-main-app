/*
 * Copyright 2015 Stormpath, Inc.
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
 */
package com.stormpath.tutorial.controller;

import com.stormpath.sdk.servlet.account.AccountResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @since 1.0.RC5
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    String home(HttpServletRequest req, Model model) {
        model.addAttribute("status", req.getParameter("status"));
        return "home";
    }

    @RequestMapping("/restricted")
    String restricted(HttpServletRequest req) {
        if (AccountResolver.INSTANCE.getAccount(req) != null) {
            return "restricted";
        }

        return "redirect:/login";
    }


    @RequestMapping("/archivedCardsPage")
    String archivedCardsPage(HttpServletRequest req) {
        if (AccountResolver.INSTANCE.getAccount(req) != null) {
            return "archivedCardsPage";
        }

        return "redirect:/login";
    }

}