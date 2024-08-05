package com.daemawiki.daemawiki.interfaces.user.dto;

import com.daemawiki.daemawiki.domain.user.model.detail.ClassInfo;
import com.daemawiki.daemawiki.domain.user.model.detail.UserInfo;

import java.util.List;

public record UserRegisterRequest(
        String name,
        String email,
        String password,

        UserInfo userInfo,

        List<ClassInfo> classInfos
) {
}
