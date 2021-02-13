package com.droideparanoico.codesharingplatform.repository;

import org.springframework.data.repository.CrudRepository;
import com.droideparanoico.codesharingplatform.model.CodeSnippet;

import java.util.Collection;

public interface CodeSnippetRepository extends CrudRepository<CodeSnippet, String> {

    Collection<CodeSnippet> findTop10ByTimeEqualsAndViewsEqualsOrderByDateDesc(Long time, Integer views);
}