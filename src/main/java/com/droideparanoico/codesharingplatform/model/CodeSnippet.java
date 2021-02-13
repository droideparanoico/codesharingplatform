package com.droideparanoico.codesharingplatform.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name="snippets")
public class CodeSnippet {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String uuid;
    private LocalDateTime date;
    @Lob
    @Column
    private String code;
    private Long time = 0L;
    private Integer views = 0;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean timeRestricted;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean viewRestricted;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @JsonProperty("date")
    public String getFormattedDate() {
        return date == null ? "" : formatter.format(date);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getTime() {
        return time;
    }

    @JsonGetter("time")
    public Long getTimeAvailable() {
        return time == 0 ? 0 : time - date.until(LocalDateTime.now(), ChronoUnit.SECONDS);
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isTimeRestricted() {
        return timeRestricted;
    }

    public void setTimeRestricted(boolean timeRestricted) {
        this.timeRestricted = timeRestricted;
    }

    public boolean isViewRestricted() {
        return viewRestricted;
    }

    public void setViewRestricted(boolean viewRestricted) {
        this.viewRestricted = viewRestricted;
    }
}
