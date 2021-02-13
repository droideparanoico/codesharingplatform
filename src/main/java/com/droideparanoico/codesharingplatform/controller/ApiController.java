package com.droideparanoico.codesharingplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import com.droideparanoico.codesharingplatform.model.CodeSnippet;
import com.droideparanoico.codesharingplatform.service.CodeSnippetService;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/code")
public class ApiController {

    private CodeSnippetService codeSnippetService;

    @Qualifier("codeSnippetService")
    @Autowired
    public void setService(CodeSnippetService service) {
        this.codeSnippetService = service;
    }

    @GetMapping(path = "/{uuid}")
    public CodeSnippet getCodeApi(@PathVariable String uuid) {
        return codeSnippetService.getCodeSnippetById(uuid);
    }

    @GetMapping(path = "/latest")
    public Collection<CodeSnippet> getLatestCodeApi() {
        return codeSnippetService.findLatestUnrestricted();
    }

    @PostMapping(path = "/new", produces = "application/json;charset=UTF-8")
    public Map<String, String> createNewCodeApi(@RequestBody CodeSnippet codeSnippet) {
        CodeSnippet responseCode = codeSnippetService.saveCodeSnippet(codeSnippet);
        return Map.of("id", responseCode.getUuid());
    }
}
