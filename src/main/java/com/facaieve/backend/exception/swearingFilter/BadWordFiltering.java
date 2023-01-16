package com.facaieve.backend.exception.swearingFilter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor

public class BadWordFiltering implements ForbiddenWords, AddRemove {
    private final Set<String> set = new HashSet<>(List.of(koreaWord1));// 욕설 패키지
    private String substituteValue = "*";

    //대체 문자 지정
    //기본값 : *
//    public BadWordFiltering(String substituteValue) {
//        this.substituteValue = substituteValue;
//    }//애너테이션으로 대체

    //특정 문자 추가, 삭제
    @Override
    public void add(String...texts) {
        set.addAll(List.of(texts));
    }

    @Override
    public void add(List<String> texts) {
        set.addAll(texts);
    }

    @Override
    public void add(Set<String> texts) {
        set.addAll(texts);
    }

    @Override
    public void remove(String...texts) {
        List.of(texts).forEach(set::remove);
    }

    @Override
    public void remove(List<String> texts) {
        texts.forEach(set::remove);
    }

    @Override
    public void remove(Set<String> texts) {
        texts.forEach(set::remove);
    }

    //비속어 있다면 대체
    public String checkAndChange(String text) {
        Set<String> s = set.stream()
                .filter(text::contains)
                .collect(Collectors.toSet());

        for (String v : s) {
            int textLen = v.length();
            String sub = this.substituteValue.repeat(textLen);
            text = text.replace(v, sub);// 비속어를 "*"으로 대체
        }

        return text;
    }

    //비속어가 1개라도 존재하면 true 반환
    public boolean check(String text) {
        return set.stream()
                .anyMatch(text::contains);
    }

    //공백을 없는 상태 체크
    public boolean blankCheck(String text) {
        String cpText = text.replace(" ", "");
        return check(cpText);
    }
}
