package com.server.gateway.cache;

import java.util.Iterator;
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

    /**
     * 从多个群中移除掉sessionId
     *
     * @param sessionId
     * @param groups
     */
    public static void removeMemberFromGroups(String sessionId, Set<String> groups) {
        Iterator<String> iterator = groups.iterator();
        while (iterator.hasNext()) {
            String                group   = iterator.next();
            LinkedHashSet<String> members = GROUPS.get(group);
            if (members != null) {
                members.remove(sessionId);
            }
        }
    }

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
