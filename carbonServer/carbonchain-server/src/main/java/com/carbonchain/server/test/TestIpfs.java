package com.carbonchain.server.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.digest.DigestUtils;

import com.carbonchain.server.util.SecurityUtil;

import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;

public class TestIpfs {

	public static void main(String[] args) throws Exception {
		/*IPFS ipfs = new IPFS("/ip4/192.168.137.196/tcp/5001");
		Multihash filePointer = Multihash.fromBase58("Qmb5c8NkxTk6m63tC5sKELASkNJYH1taExzoGeHD73txsU");
		byte[] fileContents = ipfs.cat(filePointer);
		
		System.out.println(new String(fileContents));*/
		//getLocalMac();
		//SecurityUtil.encode3Des(key, srcStr)
		
		System.out.println(DigestUtils.md5Hex("b86ec9bf79a376092202956e3af722ee60cec7aa0789c25131686222acafb5a6a48242aed2ffbc4c0e39b34eecfd050060e172c33983972b044c8296ac0e25a1"));
	}
	
    private static void getLocalMac() throws Exception {
    	System.out.println("进行single net address 测试===》");

        InetAddress ip;
        ip = InetAddress.getLocalHost();
        System.out.println("Current IP address : " + ip.getHostAddress());

        NetworkInterface network = NetworkInterface.getByInetAddress(ip);

        byte[] mac = network.getHardwareAddress();

        System.out.print("Current MAC address : ");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        System.out.println(sb.toString());

    }
}
