package com.server.gateway.cache;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session 分组信息
 * create by Lyon.Cao in 2021/01/19 23:59
 **/
public class MemorySessionGroupStore {
    private static final ConcurrentHashMap<String, LinkedHashSet<String>> GROUPS         = new ConcurrentHashMap<>(2000);
    private static final ConcurrentHashMap<String, Set<String>>           SESSION_GROUPS = new ConcurrentHashMap<>();

    public void addMemberToGroups(String sessionId, String[] groups) {
        for (String group : groups) {
            LinkedHashSet<String> strings = GROUPS.get(group);
            if (strings == null) {
                synchronized (group) {
                    if ((strings = GROUPS.get(group)) == null) {
                        LinkedHashSet<String> members = new LinkedHashSet<>();
                        GROUPS.put(group, members);
                    }
                }
            }
            strings.add(sessionId);
        }
    }

    public LinkedHashSet<String> getMembers(String group) {
        return GROUPS.get(group);
    }


}
