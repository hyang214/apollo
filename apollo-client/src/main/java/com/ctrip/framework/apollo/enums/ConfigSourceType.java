package com.ctrip.framework.apollo.enums;

/**
 * 配置来源枚举
 *
 * To indicate the config's source type, i.e. where is the config loaded from
 *
 * @since 1.1.0
 */
public enum ConfigSourceType {

  /**
   * 远程服务
   * 本地文件
   * 失败
   */
  REMOTE("Loaded from remote config service"),
  LOCAL("Loaded from local cache"),
  NONE("Load failed");

  private final String description;

  ConfigSourceType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
