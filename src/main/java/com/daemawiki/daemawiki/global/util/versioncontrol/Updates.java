package com.daemawiki.daemawiki.global.util.versioncontrol;

import java.util.Map;

public record Updates(
        Map<Integer, String> removed,
        Map<Integer, String> added
) {
}
