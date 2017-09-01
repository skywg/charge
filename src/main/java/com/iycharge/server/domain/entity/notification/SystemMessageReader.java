package com.iycharge.server.domain.entity.notification;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 系统消息的读者
 * 
 * @author bwang
 */
@Entity
public class SystemMessageReader extends BaseEntity {

    /**
     * 系统消息
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    private SystemMessage systemMessage;

    /**
     * 系统消息读者id
     */
    private long accountId;

    public SystemMessageReader() {

    }

    public SystemMessage getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(SystemMessage systemMessage) {
        this.systemMessage = systemMessage;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
