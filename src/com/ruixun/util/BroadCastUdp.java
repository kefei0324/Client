package com.ruixun.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.util.Log;

public class BroadCastUdp extends Thread {

	private String dataString;
	DatagramSocket udpSocket;
	byte[] buffer = null;
	private static final String TAG = "BROADCASTUDP";

	private String remark;
	
	public BroadCastUdp(String dataString,String remarks) {
		this.dataString = dataString;
		this.remark=remarks;
	}

	public void run() {
		DatagramPacket dataPacket = null;
		try {
			buffer = dataString.getBytes();
			udpSocket = new DatagramSocket(9966);
			udpSocket.setBroadcast(true);
			String ipAndPort=remark;
			String []ad=ipAndPort.split(":",2);
			dataPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(/*"192.168.0.136"*/ad[0]),Integer.parseInt(ad[1]));
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG + "1", e.toString());
		}
		try {
			udpSocket.send(dataPacket);

//			DatagramPacket dpIn = null;
//			byte[] bufferIn = new byte[256];
//			dpIn = new DatagramPacket(bufferIn, bufferIn.length);
//			udpSocket.receive(dpIn);
//			System.out.println(new String(bufferIn, 0, buffer.length));
		} catch (Exception e) {
			e.printStackTrace();
//			Log.e(TAG + "2", e.toString());
		}
		if (udpSocket != null)
			udpSocket.close();
	}

}
