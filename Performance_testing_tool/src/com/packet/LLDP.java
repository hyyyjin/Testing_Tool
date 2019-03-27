package com.packet;

import com.component.Port;
import com.packet.EthernetHeader;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version ����ʱ�䣺2018��6��12�� ����6:32:22 ��˵��
 */
public class LLDP {

	public static byte[] LLDP(Port port) {
		EthernetHeader header = new EthernetHeader();
		header.setSourceMac("02:eb:9f:67:c9:42");
		header.setDestinationMac("a5:23:05:00:00:01");
		header.setEthernetType("lldp");

		byte[] data = new byte[81];

		data = header.toByte(data);

		data[14] = 0x02;
		data[15] = 0x07;
		data[16] = 0x04;

		// dpid
		byte[] temp = LongToBytes(port.belong2Node.NodeConfig.getDatapathId());

		data[17] = temp[0];
		data[18] = temp[1];
		data[19] = temp[2];
		data[20] = temp[3];
		data[21] = temp[4];
		data[22] = temp[5];

		// port Num
		data[23] = 0x04;
		data[24] = 0x05;
		data[25] = 0x02;

		temp = intToByteArray(port.getPortNo());
		data[26] = temp[0];
		data[27] = temp[1];
		data[28] = temp[2];
		data[29] = temp[3];

		// time to live
		data[30] = 0x06;
		data[31] = 0x02;
		data[32] = 0x00;
		data[33] = 0x78;

		// OpenNetw Unknown 1
		data[34] = (byte) 0xfe;
		data[35] = 0x12;
		data[36] = (byte) 0xa4;
		data[37] = 0x23;
		data[38] = 0x05;
		data[39] = 0x01;

		temp = "MIRLAB-LLDP     ".getBytes();
		data[40] = temp[0];
		data[41] = temp[1];
		data[42] = temp[2];
		data[43] = temp[3];
		data[44] = temp[4];
		data[45] = temp[5];
		data[46] = temp[6];
		data[47] = temp[7];
		data[48] = temp[8];
		data[49] = temp[9];
		data[50] = temp[10];
		data[51] = temp[11];
		data[52] = temp[12];
		data[53] = temp[13];

		// OpenNetw Unknown 2
		data[54] = (byte) 0xfe;
		data[55] = 0x17;
		data[56] = (byte) 0xa4;
		data[57] = 0x23;
		data[58] = 0x05;
		data[59] = 0x01;

		temp = port.belong2Node.getDPID().getBytes();
		data[60] = temp[0];
		data[61] = temp[1];
		data[62] = temp[2];
		data[63] = temp[3];
		data[64] = temp[4];
		data[65] = temp[5];
		data[66] = temp[6];
		data[67] = temp[7];
		data[68] = temp[8];
		data[69] = temp[9];
		data[70] = temp[10];
		data[71] = temp[11];
		data[72] = temp[12];
		data[73] = temp[13];
		data[74] = temp[14];
		data[75] = temp[15];
		data[76] = temp[16];
		data[77] = temp[17];
		data[78] = temp[18];

		// end
		data[79] = 0x00;
		data[80] = 0x00;

		return data;
	}

	public static int getPortNum(byte[] lldp) {

		byte[] bytes = new byte[4];

		for (int i = 0; i < 4; i++) {
			bytes[i] = lldp[26 + i];
		}
		int num = bytes[3] & 0xFF;
		num |= ((bytes[2] << 8) & 0xFF00);
		num |= ((bytes[1] << 16) & 0xFF0000);
		num |= ((bytes[0] << 24) & 0xFF0000);

		return num;
	}

	public static Long getDpid(byte[] lldp) {

		byte[] temp = new byte[19];

		for (int i = 0; i < 19; i++) {
			temp[i] = lldp[60 + i];
		}

		String s = new String(temp);

		return Long.parseLong(s.split(":")[1]);
	}

	private static byte[] intToByteArray(int a) {
		return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF) };
	}

	private static byte[] LongToBytes(long values) {
		byte[] buffer = new byte[6];
		for (int i = 0; i < 6; i++) {
			int offset = 48 - (i + 1) * 8;
			buffer[i] = (byte) ((values >> offset) & 0xff);
		}
		return buffer;
	}
}
