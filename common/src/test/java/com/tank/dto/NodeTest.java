package com.tank.dto;

import lombok.val;
import org.junit.Test;

public class NodeTest {

  @Test
  public void print() {
    
    root = new Node<AbstractLeaf>();
    val node1 = new Node<AbstractLeaf>();

    val leaf1 = new Leaf().setChannelId("s0001");
    val leaf2 = new Leaf().setChannelId("s0002");
    val leaf3 = new Leaf().setChannelId("s0003");

    node1.setChannelId("node1");
    node1.add(leaf3);

    root.add(node1);
    root.add(leaf1);
    root.add(leaf2);

    root.print();

  }

  private Node<AbstractLeaf> root;
}