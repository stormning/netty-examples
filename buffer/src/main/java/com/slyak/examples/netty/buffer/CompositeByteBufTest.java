package com.slyak.examples.netty.buffer;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.ByteProcessor;

/**
 * .
 *
 * @author stormning 2017/4/18
 * @since 1.3.0
 */
public class CompositeByteBufTest {

    public static void main(String[] args) {
        CompositeByteBuf byteBufs = new CompositeByteBuf(new UnpooledByteBufAllocator(false), false, 10);
        int length = byteBufs.readableBytes();
        byte[] array = new byte[length];
        byteBufs.getBytes(byteBufs.readerIndex(), array);

        while (byteBufs.isReadable()) {
            System.out.println(byteBufs.readByte());
        }

        byteBufs.forEachByte(ByteProcessor.FIND_NUL);
    }

}
