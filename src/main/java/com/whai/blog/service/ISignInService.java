package com.whai.blog.service;

import java.util.Map;
import java.util.Set;

public interface ISignInService {
    void signIn();

    void signIn(Integer day);

    Map<String, Set<Integer>> userSignStatus();

    Set<Integer> mySignStatus();
}
