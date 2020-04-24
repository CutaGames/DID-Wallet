//package com.tancheng.p2p;
//
//import java.io.File;
//import java.util.List;
//import java.util.UUID;
//
//import net.tomp2p.peers.PeerAddress;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import com.google.common.collect.Lists;
//import com.google.common.net.HostAndPort;
//import com.google.gson.Gson;
//
///**
// *
// * @author panzi
// *
// */
//public class Bootstrap {
//	static Gson gson = new Gson();
//
//	public static void main(String[] args) {
//		String configPath = args[0];
//		if(StringUtils.isBlank(configPath)){
//			System.out.println("config file path is empty!");
//			System.exit(0);
//		}
//		File file = new File(configPath);
//		if(!file.exists()){
//			System.out.println("config file not exist!");
//			System.exit(0);
//		}
//		String configJson = JsonUtil.readJsonFile(file);
//		System.out.println("config loaded: ");
//		System.out.println(configJson);
//
//		Config config = gson.fromJson(configJson, Config.class);
//		List<String> peers = config.getPeers();
//		Integer port = config.getPort();
//
//		if(port == null){
//			port = 4000;
//		}
//		List<BootstrapPeer> booterList = Lists.newArrayList();
//		if(CollectionUtils.isNotEmpty(peers)){
//			for(String peerStr : peers){
//				try{
//					HostAndPort address = HostAndPort.fromString(peerStr);
//					BootstrapPeer booter = new BootstrapPeer(address.getHost(), address.getPort());
//					booterList.add(booter);
//					System.out.println("bootstrap peer: "+ address);
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//			}
//		}
//		P2POverlay p2p = new P2POverlay();
//		try {
//			//p2p.startServer(4000);
//
//			//P2POverlay p2p1 = new P2POverlay();
//			//p2p.startClientNAT(null, 4002);
//
//			boolean success = p2p.bootstrap(booterList, port);
//			/*System.out.println("node startup " + (success ? "success on "+ port : "failure!"));
//			p2p.setObjectDataReply(new ObjectReplyHandler());
//
//			String publicKey = UUID.randomUUID().toString();
//
//			PublicNodeInfo node = new PublicNodeInfo();
//			node.setPeerAddress(p2p.getPeerAddress());
//			node.setPublicKey(publicKey);
//			node.setType("1");
//			node.setVersion("v1");
//			System.out.println("node info : "+ node);
//			p2p.put(publicKey, gson.toJson(node));
//
//			Object obj = p2p.getBlocking("5a77b1f9-4d8f-4975-a9f3-a3eed103e14f");
//			if(obj != null){
//				PublicNodeInfo targetNode = gson.fromJson(obj.toString(), PublicNodeInfo.class);
//				PeerAddress recipient = targetNode.getPeerAddress();
//				p2p.sendBlocking(recipient, publicKey + " hello world ");
//			}*/
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}