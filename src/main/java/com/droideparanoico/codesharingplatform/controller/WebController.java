package com.droideparanoico.codesharingplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.droideparanoico.codesharingplatform.model.CodeSnippet;
import com.droideparanoico.codesharingplatform.service.CodeSnippetService;

import java.util.*;

@Controller
@RequestMapping("/")
public class WebController {

    private CodeSnippetService codeSnippetService;

    @Qualifier("codeSnippetService")
    @Autowired
    public void setService(CodeSnippetService service) {
        this.codeSnippetService = service;
    }

    @GetMapping(path = "/", produces = "text/html")
    public String index() {
        return "index";
    }

    @GetMapping(path = "/code/{uuid}", produces = "text/html")
    public String getCodeWeb(Model model, @PathVariable String uuid) {
        model.addAttribute("codeSnippet", codeSnippetService.getCodeSnippetById(uuid));
        return "snippet";
    }

    @GetMapping(path = "/code/latest", produces = "text/html")
    public String getLatestCodeWeb(Model model) {
        if (codeSnippetService.findLatestUnrestricted() != null) {
            Collection<CodeSnippet> snippetCollection = codeSnippetService.findLatestUnrestricted();
            List<CodeSnippet> snippetList = new ArrayList<>(snippetCollection);
            model.addAttribute("codeSnippets",snippetList);
        }
        return "latest";
    }

    @GetMapping(path = "/code/new", produces = "text/html")
    public String createNewCodeWeb() {
        return "new_snippet";
    }
}
