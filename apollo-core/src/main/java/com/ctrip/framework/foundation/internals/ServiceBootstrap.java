package com.ctrip.framework.foundation.internals;

import com.ctrip.framework.apollo.core.spi.Ordered;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * apollo类加载工具，负责根据class查到一个合适的实例
 *
 */
public class ServiceBootstrap {

  /**
   * 加载满足对象的第一个对象
   * + 根据类获取全部对应类型的实例
   * + 返回第一个
   *
   * @param clazz
   * @param <S>
   * @return
   */
  public static <S> S loadFirst(Class<S> clazz) {
    Iterator<S> iterator = loadAll(clazz);
    if (!iterator.hasNext()) {
      throw new IllegalStateException(String.format(
          "No implementation defined in /META-INF/services/%s, please check whether the file exists and has the right implementation class!",
          clazz.getName()));
    }
    return iterator.next();
  }

  /**
   * 根据类型，返回全部的实例
   * + 核心方法，本类的其他方法都依赖于此
   * + ServiceLoader是java本身提供的服务加载机制
   *    + 需要在META-INF/services中进行定义
   *    + 实现了iterator接口，实现了延迟加载，真正需要的时候，才会加载
   *
   *    + ServiceLoader是实现SPI一个重要的类。是Mark Reinhold在java1.6引入的类，为了解决接口与实现分离的场景。
   *    + 在资源目录META-INF/services中放置提供者配置文件，文件名以接口的类名命名，里面的内容为需要加载的实现类。
   *    + 然后在app运行时，遇到Serviceloader.load(XxxInterface.class)时，
   *    + 会到META-INF/services的配置文件中寻找这个接口对应的实现类全路径名，然后使用Class.forName()（传入设定的类加载器）
   *    + 完成类的加载。
   *
   * @param clazz
   * @param <S>
   * @return
   */
  public static <S> Iterator<S> loadAll(Class<S> clazz) {
    ServiceLoader<S> loader = ServiceLoader.load(clazz);

    return loader.iterator();
  }

  /**
   * 加载全部对应实例，但是按照Ordered接口指定的顺序，返回list
   * + 首先通过{@link #loadAll(Class)}加载全部
   * + 然后对齐进行排序
   *
   * @param clazz
   * @param <S>
   * @return
   */
  public static <S extends Ordered> List<S> loadAllOrdered(Class<S> clazz) {
    Iterator<S> iterator = loadAll(clazz);

    if (!iterator.hasNext()) {
      throw new IllegalStateException(String.format(
          "No implementation defined in /META-INF/services/%s, please check whether the file exists and has the right implementation class!",
          clazz.getName()));
    }

    List<S> candidates = Lists.newArrayList(iterator);
    Collections.sort(candidates, new Comparator<S>() {
      @Override
      public int compare(S o1, S o2) {
        // the smaller order has higher priority
        return Integer.compare(o1.getOrder(), o2.getOrder());
      }
    });

    return candidates;
  }

  /**
   * 按照顺序拉取全部对应类型的实例，返回指定顺序的第一个
   * 与{@link #loadFirst}相比，需要按照类实现Ordered的接口
   * + 其中需要加载的类，必须是实现了Ordered接口的，这个通过泛型的<S extends Ordered>来约束
   *
   * @param clazz
   * @param <S>
   * @return
   */
  public static <S extends Ordered> S loadPrimary(Class<S> clazz) {
    List<S> candidates = loadAllOrdered(clazz);

    return candidates.get(0);
  }
}
