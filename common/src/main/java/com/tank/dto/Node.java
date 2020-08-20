package com.tank.dto;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tank198435163.com
 */
public class Node<T extends AbstractLeaf> extends AbstractLeaf {

  public boolean isLeaf() {
    return children.isEmpty();
  }

  public boolean add(@NonNull final T item) {
    return this.children.add(item);
  }

  public void print() {
    this.iteratorNode(this);
  }

  @SuppressWarnings("warning")
  private void iteratorNode(@NonNull final AbstractLeaf data) {
    if (data instanceof Leaf) {
      System.out.println(StrUtil.format("leaf:{},channel id:{}", counter.incrementAndGet(), data.getChannelId()));
    } else {
      val isNode = data instanceof Node;
      if (!isNode) {
        return;
      }
      Node<T> node = ((Node) data);
      for (T child : node.getChildren()) {
        this.iteratorNode(child);
      }

    }
  }

  @Getter
  private final List<T> children = Lists.newArrayList();

  private AtomicInteger counter = new AtomicInteger(0);
}
