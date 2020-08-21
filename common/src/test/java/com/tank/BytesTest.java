package com.tank;

import cn.hutool.core.util.StrUtil;
import lombok.val;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author tank198435163.com
 */
public class BytesTest {

  @Test
  public void testBytes() {
    val str = "hello";
    val welcome = ",this is my world";
    val buffer = ByteBuffer.allocate(128);
    buffer.put(str.getBytes());
    buffer.rewind();
    final byte[] hello = new byte[str.length()];
    buffer.get(hello);
    buffer.flip();
    buffer.clear();
    buffer.put(welcome.getBytes());
    buffer.rewind();
    final byte[] second = new byte[welcome.length()];
    buffer.get(second);
    final byte[] total = new byte[second.length + hello.length];
    System.arraycopy(hello, 0, total, 0, hello.length);
    System.arraycopy(second, 0, total, hello.length, second.length);
    System.out.println(new String(total));
  }

  @Test
  public void testBuffer() {
    val str = "hello";
    val buffer = ByteBuffer.allocate(str.length());
    printBuffer(buffer);
    buffer.rewind();
    printBuffer(buffer);
    buffer.flip();
    printBuffer(buffer);
  }

  private void printBuffer(ByteBuffer buffer) {
    System.out.println(StrUtil.format("position:[{}]", buffer.position()));
    System.out.println(StrUtil.format("limit:[{}]", buffer.limit()));
    System.out.println(StrUtil.format("capacity:[{}]", buffer.capacity()));
    System.out.println("=========================================================");
  }


}
