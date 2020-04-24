//package com.tancheng.p2p;
//
//import net.tomp2p.peers.PeerAddress;
//import net.tomp2p.rpc.ObjectDataReply;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class ObjectReplyHandler implements ObjectDataReply {
//
//    private static final Logger log = LoggerFactory.getLogger(ObjectReplyHandler.class);
//
//    public Object reply(PeerAddress pa, Object o) throws Exception {
//    	System.out.println(pa.toString());
//    	System.out.println(o);
//        return null;
//    }
//}