//package com.tancheng.p2p;
//
//import net.tomp2p.connection.Bindings;
//import net.tomp2p.connection.StandardProtocolFamily;
//import net.tomp2p.dht.*;
//import net.tomp2p.futures.BaseFuture;
//import net.tomp2p.futures.BaseFutureAdapter;
//import net.tomp2p.futures.FutureBootstrap;
//import net.tomp2p.futures.FutureDirect;
//import net.tomp2p.futures.FutureDiscover;
//import net.tomp2p.nat.FutureNAT;
//import net.tomp2p.nat.PeerBuilderNAT;
//import net.tomp2p.nat.PeerNAT;
//import net.tomp2p.p2p.PeerBuilder;
//import net.tomp2p.p2p.Peer;
//import net.tomp2p.peers.Number160;
//import net.tomp2p.peers.PeerAddress;
//import net.tomp2p.replication.IndirectReplication;
//import net.tomp2p.rpc.ObjectDataReply;
//import net.tomp2p.storage.Data;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
///**
// *
// * @author panzi
// *
// */
//public class P2POverlay {
//
//    private static final Logger log = LoggerFactory.getLogger(P2POverlay.class);
//
//    private Peer peer;
//    private PeerDHT peerDHT;
//    private static Random rnd = new Random();
//    private List<CallBack> listeners = new ArrayList<CallBack>();
//
//    public void addListener(CallBack listener) {
//        listeners.add(listener);
//    }
//
//    public void notifySomethingHappened() {
//        for (CallBack listener : listeners) {
//            listener.futurePutIsASuccess();
//        }
//    }
//
//    public PeerAddress getPeerAddress() {
//        return peer.peerAddress();
//    }
//
//    public boolean put(String key, Object value) {
//        Data data;
//        try {
//            data = new Data(value);
//        } catch (IOException ex) {
//            return false;
//        }
//        FuturePut futurePut = peerDHT.put(Number160.createHash(key)).data(data).start().awaitUninterruptibly();
//
//        return futurePut.isSuccess();
//    }
//
//    public boolean putNonBlocking(String key, Object value) {
//        Data data;
//        try {
//            data = new Data(value);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return false;
//        }
//
//        FuturePut futurePut = peerDHT.put(Number160.createHash(key)).data(data).start();
//
//        futurePut.addListener(new BaseFutureAdapter<FuturePut>() {
//            public void operationComplete(FuturePut future) throws Exception {
//                if (future.isSuccess()) { // this flag indicates if the future was successful
//                    log.info("success");
//                    //MainWindow.futurputSuccess = true;
//                } else {
//                    log.info("failure");
//                }
//            }
//        });
//
//        return true;
//    }
//
//    public Object getBlocking(String key) {
//        FutureGet futureGet = peerDHT.get(Number160.createHash(key)).start().awaitUninterruptibly();
//
//        if (futureGet.isSuccess()) {
//            try {
//                return futureGet.data().object();
//            } catch (Exception ex) {
//                log.info("MY ARCH NEMESIS");
//                ex.printStackTrace();
//                return null;
//            }
//        } else {
//            log.info("MY ARCH NEMESIS TOO TBH");
//            return null;
//        }
//    }
//
//    public boolean sendBlocking(PeerAddress recipient, Object o) {
//        FutureDirect futureDirect = peer.sendDirect(recipient)
//                .object(o).start().awaitUninterruptibly();
//
//        return futureDirect.isSuccess();
//    }
//
//    public void sendNonBlocking(PeerAddress recipient, Object o, boolean forceUDP) {
//        FutureDirect futureDirect = peer.sendDirect(recipient)
//                .object(o).forceTCP(forceUDP).start();
//    }
//
//    public void getNonBLocking(String key, BaseFutureAdapter<FutureGet> baseFutureAdapter) {
//        FutureGet futureGet = peerDHT.get(Number160.createHash(key)).start();
//        futureGet.addListener(baseFutureAdapter);
//    }
//
//    public void startServer(int port) throws IOException, InterruptedException{
//    	Random r = new Random(42L);
//    	Bindings b = new Bindings();
//    	b.listenAny();
//		Peer peer = new PeerBuilder(new Number160(r)).bindings(b).ports(port).start();
//
//		System.out.println("peer started." + peer.peerAddress());
//		for (;;) {
//			for (PeerAddress pa : peer.peerBean().peerMap().all()) {
//					System.out.println("peer online (TCP):" + pa);
//			}
//			Thread.sleep(2000);
//		}
//    }
//
//    public void startClientNAT(List<BootstrapPeer> bootstrapList,int port) throws Exception{
//    	Bindings b = new Bindings();
//    	b.listenAny();
//    	peer = new PeerBuilder(new Number160(rnd)).bindings(b).ports(port).start();
//
//    	/*PeerAddress bootStrapServer = new PeerAddress(Number160.ZERO,
//                InetAddress.getByName("154.223.160.147"), 4000, 4000);
//    	FutureDiscover fd = peer.discover().peerAddress(bootStrapServer).start();
//    	fd.awaitUninterruptibly();
//    	if (fd.isSuccess())
//        {
//            System.out.println("*** FOUND THAT MY OUTSIDE ADDRESS IS " + fd.peerAddress());
//        }
//        else
//        {
//            System.out.println("*** FAILED " + fd.failedReason());
//        }
//    	bootStrapServer = fd.reporter();*/
//    	FutureBootstrap bootstrap = peer.bootstrap().inetAddress(InetAddress.getByName("154.223.160.147")).ports(4000).start();
//        bootstrap.awaitUninterruptibly();
//        if (!bootstrap.isSuccess()) {
//            System.out.println("*** COULD NOT BOOTSTRAP!");
//        }
//        else
//        {
//            System.out.println("*** SUCCESSFUL BOOTSTRAP");
//        }
//    }
//
//    public boolean bootstrap(List<BootstrapPeer> bootstrapList,int port) throws Exception {
//    	try {
//    		Bindings bindings = new Bindings();
//            bindings.listenAny();
//            peer = new PeerBuilder(new Number160(rnd)).bindings(bindings).ports(port).start();
//
//            peerDHT = new PeerBuilderDHT(peer).start();
//
//            //new IndirectReplication(peerDHT).start();
//    	} catch (IOException ex) {
//            throw new Exception("Port already in use. " + ex.getMessage());
//        }
//
//    	if(CollectionUtils.isNotEmpty(bootstrapList)){
//    		PeerNAT pn = null;
//    		InetAddress theAddress = null;
//    		FutureDiscover futureDiscover = null;
//    		for(BootstrapPeer booter : bootstrapList){
//    			try{
//    				InetAddress address = InetAddress.getByName(booter.getHost());
//        			int booterPort = booter.getPort();
//
//        			PeerAddress paOrig = peer.peerBean().serverPeerAddress();
//
//        			futureDiscover = peer.discover().inetAddress( address ).ports( booterPort ).start();
//        			futureDiscover.awaitUninterruptibly();
//
//        			/*if(futureDiscover.isFailed()){
//        				pn = new PeerBuilderNAT(peer).start();
//        				FutureNAT fn = pn.startSetupPortforwarding(futureDiscover);
//        				fn.awaitUninterruptibly();
//
//        				if(fn.isFailed()){
//        					System.out.println("UPNP Failed, checking direct port forwarding." + fn.failedReason());
//        					peer.peerBean().serverPeerAddress(paOrig);
//        					futureDiscover = peer.discover()
//        							.expectManualForwarding()
//        							.inetAddress(address)
//        							.ports(booterPort).start();
//        					futureDiscover.awaitUninterruptibly();
//        				}
//
//        				if(fn.isSuccess() || futureDiscover.isSuccess()){
//        					theAddress = address;
//        					break;
//        				}else{
//        					System.out.println("Discovering failed trying next node." + futureDiscover.failedReason());
//        				}
//        			}else {
//        				theAddress = address;
//        				break;
//        			}*/
//
//        			System.out.println("futureDiscover:");
//        			if(futureDiscover.isSuccess()){
//        				 System.out.println("bootstrap successfully to remote address: "+ booterPort );
//        			}else{
//        				System.err.println("bootstrap failed to remote address: "+ address.getAddress() +".Cause: "+ futureDiscover.failedReason());
//        			}
//
//        			FutureBootstrap bootstrap = peer.bootstrap().peerAddress(futureDiscover.peerAddress()).start();
//        			System.out.println("bootstrap:");
//        			bootstrap.awaitUninterruptibly();
//        			if(bootstrap.isSuccess()){
//        				System.out.println("bootstrap successfully to remote address: "+ booterPort );
//	       			}else{
//	       				System.err.println("bootstrap failed to remote address: "+ address.getAddress() +".Cause: "+ bootstrap.failedReason());
//	       			}
//
//        			FutureBootstrap futureBootstrap = peer.bootstrap().inetAddress( address ).ports( booterPort ).start();
//        			futureBootstrap.awaitUninterruptibly();
//        			System.out.println("futureBootstrap:");
//
//        			if(futureBootstrap.isSuccess()){
//        				System.out.println("bootstrap successfully to remote address: "+ booterPort );
//        			}else{
//	       				System.err.println("bootstrap failed to remote address: "+ address.getAddress() +".Cause: "+ futureBootstrap.failedReason());
//	       			}
//    			}catch(Exception e){
//    				e.printStackTrace();
//    			}
//    		}
//    		if(theAddress != null){
//    			System.out.println("链接成功！");
//    		}
//    	}
//        return true;
//    }
//
//    public void setObjectDataReply(ObjectDataReply replyHandler) {
//        peer.objectDataReply(replyHandler);
//    }
//
//    public void shutdown() {
//        if (peer != null) {
//            BaseFuture shutdownBuilder = peer.shutdown();
//            shutdownBuilder.awaitUninterruptibly();
//            peer = null;
//        }
//    }
//
//	public Peer getPeer() {
//		return peer;
//	}
//
//	public PeerDHT getPeerDHT() {
//		return peerDHT;
//	}
//}