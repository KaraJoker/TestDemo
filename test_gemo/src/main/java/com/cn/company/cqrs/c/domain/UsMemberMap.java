package com.cn.company.cqrs.c.domain;

import cn.hutool.core.util.ObjectUtil;
import com.cn.company.cqrs.q.bean.PT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: TestDemo
 * @description: 在线用户
 * @author: Outcaster
 * @create: 2019-10-08 16:39
 */
public class UsMemberMap {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Map<String, String> memberIdSessionIdMap = new ConcurrentHashMap<>();

    private Map<String, String> sessionIdMemberIdMap = new ConcurrentHashMap<>();

    private Map<String, Long> sessionIdActiveTimeMap = new ConcurrentHashMap<>();

    /**
     * 有效时间：30天
     */
    private static final long keepTime = 24L * 60 * 60 * 30;

    private Map<String, Map<String, UsMember>> gameMap = new HashMap();

    private Map<String, Object> memberMap = new HashMap();

    public UsMemberMap() {
    }

    public void join(String playerId, String nickName, String avatar, String gameId){
        UsMember usMember;
        if (this.gameMap.containsKey(gameId)) {
            Map<String, UsMember> watchMap = (Map)this.gameMap.get(gameId);
            if (!watchMap.containsKey(playerId) && watchMap.size() >= 2) {
                usMember=null;
            } else {
                usMember = new UsMember();
                usMember.setId(playerId);
                usMember.setNickName(nickName);
                usMember.setAvatar(avatar);
                usMember.setJoinTime(System.currentTimeMillis());
                watchMap.put(playerId, usMember);
            }
        } else {
            Map<String, UsMember> newUsMember = new HashMap();
            usMember = new UsMember();
            usMember.setId(playerId);
            usMember.setNickName(nickName);
            usMember.setAvatar(avatar);
            usMember.setJoinTime(System.currentTimeMillis());
            newUsMember.put(playerId, usMember);
            this.gameMap.put(gameId, newUsMember);
        }
    }

    public void leave(String playerId, String gameId) {
        if (this.gameMap.containsKey(gameId) && ((Map)this.gameMap.get(gameId)).containsKey(playerId)) {
            ((Map)this.gameMap.get(gameId)).remove(playerId);
            if (this.gameMap.get(gameId) == null || ((Map)this.gameMap.get(gameId)).isEmpty()) {
                this.gameMap.remove(gameId);
            }
        }

    }

    public String timeoutLeave(String playerId) {
        String gameId = "";
        if (this.gameMap != null) {
            Iterator iter = this.gameMap.entrySet().iterator();

            while(iter.hasNext()) {
                Map.Entry<String, Map<String, UsMember>> entry = (Map.Entry)iter.next();
                if (((Map)entry.getValue()).containsKey(playerId)) {
                    gameId = (String)entry.getKey();
                    ((Map)entry.getValue()).remove(playerId);
                    if (entry.getValue() == null || ((Map)entry.getValue()).isEmpty()) {
                        iter.remove();
                    }
                    break;
                }
            }
        }

        return gameId;
    }

    public void recycleWatch(PT pt) {
        this.memberMap.put("pt",pt);
    }

    public Map<String, UsMember> getWatch(String gameId) {
        return this.gameMap.get(gameId) == null ? new HashMap() : new HashMap((Map)this.gameMap.get(gameId));
    }

    public String generateSessionForMember(String memberId) {
        String uuid = UUID.randomUUID().toString().replace("-", "") + "_member";
        redisTemplate.opsForValue().set(uuid, memberId, keepTime, TimeUnit.DAYS);
        memberIdSessionIdMap.put(memberId, uuid);
        sessionIdMemberIdMap.put(uuid, memberId);
        sessionIdActiveTimeMap.put(uuid, System.currentTimeMillis());
        return uuid;
    }

    public String getMemberIdBySessionId(String sessionId) {
        try {
            return sessionIdMemberIdMap.get(sessionId);
        }catch(Exception ex){
            return null;
        }
    }

    public boolean verifyActiveTime(String sessionId) {
        try {
            long activeTime = sessionIdActiveTimeMap.get(sessionId);
            if(ObjectUtil.isNotNull(activeTime)) {
                return System.currentTimeMillis() - activeTime > keepTime ? false : true;
            }else{
                return false;
            }
        }catch(Exception ex){
            return false;
        }
    }

    public void removeSession(String sessionId) {
        String memberId = redisTemplate.opsForValue().get(sessionId);
        redisTemplate.delete(sessionId);
        memberIdSessionIdMap.remove(memberId);
        sessionIdMemberIdMap.remove(sessionId);
        sessionIdActiveTimeMap.remove(sessionId);
    }
}


