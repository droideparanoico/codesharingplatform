package com.droideparanoico.codesharingplatform.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.droideparanoico.codesharingplatform.model.CodeSnippet;
import com.droideparanoico.codesharingplatform.repository.CodeSnippetRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service("codeSnippetService")
public class CodeSnippetService {

    private final CodeSnippetRepository codeSnippetRepository;

    public CodeSnippetService(CodeSnippetRepository codeSnippetRepository) {
        this.codeSnippetRepository = codeSnippetRepository;
    }

    public CodeSnippet getCodeSnippetById(final String uuid) {
        CodeSnippet codeSnippet = codeSnippetRepository.findById(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (codeSnippet.isViewRestricted()) {
            checkViewsRestriction(codeSnippet);
        }

        if (codeSnippet.isTimeRestricted()) {
            checkTimeRestriction(codeSnippet);
        }

        return codeSnippetRepository.save(codeSnippet);
    }

    public CodeSnippet saveCodeSnippet(final CodeSnippet codeSnippet) {
        codeSnippet.setUuid(UUID.randomUUID().toString());
        codeSnippet.setDate(LocalDateTime.now());
        codeSnippet.setTimeRestricted(codeSnippet.getTime() > 0);
        codeSnippet.setViewRestricted(codeSnippet.getViews() > 0);

        return codeSnippetRepository.save(codeSnippet);
    }

    public Collection<CodeSnippet> findLatestUnrestricted() {
        return codeSnippetRepository.findTop10ByTimeEqualsAndViewsEqualsOrderByDateDesc(0L, 0);
    }

    private void checkViewsRestriction(final CodeSnippet codeSnippet) {
        if (codeSnippet.getViews() > 0) {
            codeSnippet.setViews(codeSnippet.getViews() - 1);
        } else {
            deleteSnippet(codeSnippet);
        }
    }

    private void checkTimeRestriction(final CodeSnippet codeSnippet) {
        if (codeSnippet.getTimeAvailable() < 0) {
            deleteSnippet(codeSnippet);
        }
    }

    private void deleteSnippet(final CodeSnippet codeSnippet) {
        codeSnippetRepository.delete(codeSnippet);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
