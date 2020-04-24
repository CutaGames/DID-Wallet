package com.tancheng.p2p;

/**
 * 引导节点
 * @author panzi
 *
 */
public class BootstrapPeer {
	private String host;
	private int port;
	
	public BootstrapPeer(String host,int port){
		this.host = host;
		this.port = port;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
