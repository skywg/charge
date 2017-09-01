package com.iycharge.server.ccu.core;

import java.util.List;

import com.iycharge.server.ccu.msg.AbstractMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;

/**
 * 做TCP粘包和拆包的处理
 * @author bwang
 */
public class MsgByteArrayDecoder extends ByteArrayDecoder {
    
    byte[] buffer;
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        byte[] temp = new byte[msg.readableBytes()];
        msg.getBytes(0, temp);
     
        if(buffer != null) {
            msg.clear();
            msg.writeBytes(buffer);
            msg.writeBytes(temp);

            buffer = null;
        }
    
        int readalbleLength = msg.readableBytes();
        if(readalbleLength >= AbstractMsg.MIN_MSG_LEN && msg.getByte(0) ==  AbstractMsg.MSG_HEAD) {
            int frameLength = 0;
            
            int index =  msg.readerIndex();
            
            while(index < readalbleLength) {
                frameLength = (msg.getByte(index + 1) & 0xFF) + (msg.getByte(index + 2) << 8 & 0xFF00);
                
                if(index + 3 + frameLength <= readalbleLength) {
                    //取出自己定义的packet包返回给ChannelRead
                    ByteBuf frame = msg.slice(index, 3 + frameLength).retain();
                    
                    byte[] array = new byte[frame.readableBytes()];
                    frame.getBytes(0, array);
                    out.add(array);  
                    
                    index += frameLength + 3;
                    
                    //这一步一定要有，不然其实bytebuf的readerIndex没有变，netty会一直从这里开始读取，将readerIndex移动就相当于把前面的数据处理过了废弃掉了。
                    msg.readerIndex(index);
                    frame.release();
                } else {
                    buffer = new byte[msg.readableBytes()];
                    msg.readBytes(buffer);
                    break;
                }
            }
        }
    }
}
