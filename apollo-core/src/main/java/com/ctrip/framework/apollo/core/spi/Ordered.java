package com.ctrip.framework.apollo.core.spi;

/**
 * 排序接口，表面类实现了顺序
 * 加载或者使用的时候可以按照对应的值的顺序进行
 *
 * {@code Ordered} is an interface that can be implemented by objects that
 * should be <em>orderable</em>, for example in a {@code Collection}.
 *
 * <p>The actual {@link #getOrder() order} can be interpreted as prioritization,
 * with the first object (with the lowest order value) having the highest
 * priority.
 *
 * @since 1.0.0
 */
public interface Ordered {

  /**
   * 最高优先级
   *
   * Useful constant for the highest precedence value.
   * @see java.lang.Integer#MIN_VALUE
   */
  int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

  /**
   * 最低优先级
   *
   * Useful constant for the lowest precedence value.
   * @see java.lang.Integer#MAX_VALUE
   */
  int LOWEST_PRECEDENCE = Integer.MAX_VALUE;


  /**
   * 优先级顺序
   * + 相同值的顺序任意
   *
   * Get the order value of this object.
   * <p>Higher values are interpreted as lower priority. As a consequence,
   * the object with the lowest value has the highest priority (somewhat
   * analogous to Servlet {@code load-on-startup} values).
   * <p>Same order values will result in arbitrary sort positions for the
   * affected objects.
   * @return the order value
   * @see #HIGHEST_PRECEDENCE
   * @see #LOWEST_PRECEDENCE
   */
  int getOrder();
}
