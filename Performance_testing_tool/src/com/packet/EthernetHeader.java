package com.packet;

import java.util.StringTokenizer;

import com.packet.EthernetHeader;

public class EthernetHeader {
	private byte[] destinationMac;
	private byte[] sourceMac;
	private byte[] type;

	public EthernetHeader() {
		destinationMac = new byte[6];
		sourceMac = new byte[6];
		type = new byte[2];
	}

	public void setEthernetType(String str) {
		if (str.equals("ip")) {
			type[0] = 0x08;
			type[1] = 0x00;
		} else if (str.equals("arp")) {
			type[0] = 0x08;
			type[1] = 0x06;
		} else if (str.equals("lldp")) {
			type[0] = (byte) 0x88;
			type[1] = (byte) 0xcc;
		}

	}

	public void setDestinationMac(String str) {
		StringTokenizer st = new StringTokenizer(str, ":");
		for (int i = 0; i < 6; i++) {
			int temInt = Integer.parseUnsignedInt(st.nextToken(), 16);

			byte tempByte = (byte) temInt;

			this.destinationMac[i] = tempByte;
		}
	}

	public void setSourceMac(String str) {
		StringTokenizer st = new StringTokenizer(str, ":");
		for (int i = 0; i < 6; i++) {
			int temInt = Integer.parseUnsignedInt(st.nextToken(), 16);

			byte tempByte = (byte) temInt;

			this.sourceMac[i] = tempByte;
		}
	}

	public byte[] toByte(byte[] data) {
		byte total[] = new byte[14];

		for (int i = 0; i < 6; i++) {
			total[i] = this.destinationMac[i];
		}
		for (int i = 0; i < 6; i++) {
			total[i + 6] = this.sourceMac[i];
		}
		for (int i = 0; i < 2; i++) {
			total[i + 12] = this.type[i];
		}

		for (int i = 0; i < 14; i++) {
			data[i] = total[i];
		}

		return data;
	}

	public byte[] toByte() {
		byte total[] = new byte[14];

		for (int i = 0; i < 6; i++) {
			total[i] = this.destinationMac[i];
		}
		for (int i = 0; i < 6; i++) {
			total[i + 6] = this.sourceMac[i];
		}
		for (int i = 0; i < 2; i++) {
			total[i + 12] = this.type[i];
		}

		return total;
	}

	/* Test */
	public static void main(String[] args) {
		EthernetHeader e = new EthernetHeader();
		e.setSourceMac("ff:ff:ff:ff:ff:ff");
		e.setDestinationMac("f1:f1:f1:f1:f1:f1");
		byte[] temp = e.toByte();

		for (int i = 0; i < e.toByte().length; i++) {
			System.out.println(Byte.toUnsignedInt(temp[i]));
		}
	}
}
