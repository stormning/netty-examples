package com.slyak.examples.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * .
 *
 * @author stormning 2017/4/18
 * @since 1.3.0
 */
public class ByteBufOps {

    //duplicate()，slice()，slice(int,int)，readOnly()和order(ByteOrder)这些都是衍生ByteBuf，共享数据存储
    //copy是新建一个独立副本

    public void sliceBuf() {
        ByteBuf buf = Unpooled.copiedBuffer("Netty in action rocks!", CharsetUtil.UTF_8);

        ByteBuf sliced = buf.slice(0, 14);

        System.out.println(sliced.toString(CharsetUtil.UTF_8));

        buf.setByte(0, (byte) 'J');
        assert buf.getByte(0) == sliced.getByte(0);
    }

    public void copyBuf() {
        ByteBuf buf = Unpooled.copiedBuffer("Netty in action rocks!", CharsetUtil.UTF_8);
        ByteBuf copy = buf.copy(0, 14);
        System.out.println(copy.toString(CharsetUtil.UTF_8));

        buf.setByte(0, (byte) 'j');
        assert buf.getByte(0) != copy.getByte(0);
    }

    public void readAndWrite() {
        ByteBuf buf = Unpooled.copiedBuffer("Netty in action rocks!", CharsetUtil.UTF_8);
        System.out.println((char) buf.readByte());
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.writeByte((byte) '?');

        assert readerIndex == buf.readerIndex();
        assert writerIndex != buf.writerIndex();
    }

    public void byteBufAllocator() {
        ByteBufAllocator.DEFAULT.buffer();
    }

    public static void main(String[] args) {
        ByteBufOps bufOps = new ByteBufOps();
        bufOps.sliceBuf();
        bufOps.copyBuf();
        bufOps.readAndWrite();
    }
}
