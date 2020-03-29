package com.ctrip.framework.apollo.model;

import com.ctrip.framework.apollo.enums.PropertyChangeType;

/**
 * 配置文件变更事件
 *
 * @author Jason Song(song_s@ctrip.com)
 */
public class ConfigFileChangeEvent {

  /**
   * 命名空间
   */
  private final String namespace;
  /**
   * 旧的值
   */
  private final String oldValue;
  /**
   * 新的值
   */
  private final String newValue;
  /**
   * 变更事件
   */
  private final PropertyChangeType changeType;

  /**
   * Constructor.
   *
   * @param namespace the namespace of the config file change event
   * @param oldValue the value before change
   * @param newValue the value after change
   * @param changeType the change type
   */
  public ConfigFileChangeEvent(String namespace, String oldValue, String newValue,
      PropertyChangeType changeType) {
    this.namespace = namespace;
    this.oldValue = oldValue;
    this.newValue = newValue;
    this.changeType = changeType;
  }

  public String getNamespace() {
    return namespace;
  }

  public String getOldValue() {
    return oldValue;
  }

  public String getNewValue() {
    return newValue;
  }

  public PropertyChangeType getChangeType() {
    return changeType;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ConfigFileChangeEvent{");
    sb.append("namespace='").append(namespace).append('\'');
    sb.append(", oldValue='").append(oldValue).append('\'');
    sb.append(", newValue='").append(newValue).append('\'');
    sb.append(", changeType=").append(changeType);
    sb.append('}');
    return sb.toString();
  }
}
