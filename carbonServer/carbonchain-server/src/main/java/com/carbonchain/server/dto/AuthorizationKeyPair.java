package com.carbonchain.server.dto;

import java.math.BigInteger;


public class AuthorizationKeyPair extends DtoBase{
	private String publicKey; 
	private String privateKey;
	
	public AuthorizationKeyPair(){}
	
	public AuthorizationKeyPair(BigInteger publicKey,BigInteger privateKey){
		this.publicKey = publicKey.toString(16);
		this.privateKey = privateKey.toString(16);
	}
	
	public AuthorizationKeyPair(String publicKey,String privateKey){
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
}
