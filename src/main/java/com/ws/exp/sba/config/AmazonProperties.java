package com.ws.exp.sba.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 把自定义配置集中管理
 *
 * @author Eric at 2020-04-22_10:59
 */
@Component
@ConfigurationProperties("amazon")
public class AmazonProperties {
    private String associateId;

    public String getAssociateId() {
        return associateId;
    }

    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }
}
