package com.ctrip.framework.apollo.core;

/**
 * 配置常量
 *
 */
public interface ConfigConsts {

  /**
   * 默认应用命名空间
   */
  String NAMESPACE_APPLICATION = "application";
  /**
   * 默认集群
   */
  String CLUSTER_NAME_DEFAULT = "default";

  /**
   * 命名空间 和 集群的分隔符
   */
  String CLUSTER_NAMESPACE_SEPARATOR = "+";

  /**
   * apollo集群名称配置项
   */
  String APOLLO_CLUSTER_KEY = "apollo.cluster";

  /**
   * apollo配置服务器地址前缀
   *
   */
  String APOLLO_META_KEY = "apollo.meta";

  /**
   *
   */
  String CONFIG_FILE_CONTENT_KEY = "content";

  /**
   *
   */
  String NO_APPID_PLACEHOLDER = "ApolloNoAppIdPlaceHolder";

  /**
   *
   */
  long NOTIFICATION_ID_PLACEHOLDER = -1;

}
