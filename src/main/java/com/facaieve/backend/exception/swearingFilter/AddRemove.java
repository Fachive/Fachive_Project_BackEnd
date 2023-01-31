package com.facaieve.backend.exception.swearingFilter;


import java.util.*;

public interface AddRemove {
    void add(String...texts);
    void add(List<String> texts);
    void add(Set<String> texts);
    void remove(String...texts);
    void remove(List<String> texts);
    void remove(Set<String> texts);
}