package com.tancheng.p2p;

import java.util.List;

public class Config {
	private Integer port;
	private List<String> peers;

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public List<String> getPeers() {
		return peers;
	}

	public void setPeers(List<String> peers) {
		this.peers = peers;
	}
}
