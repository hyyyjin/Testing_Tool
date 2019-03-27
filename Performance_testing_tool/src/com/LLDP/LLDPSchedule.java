package com.LLDP;

import java.util.Timer;

import com.LLDP.LLDPTimerTask;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version ����ʱ�䣺2018��6��12�� ����4:19:09 ��˵��
 */
public class LLDPSchedule {
	Timer timer;

	public LLDPSchedule() {
		timer = new Timer();
		timer.schedule(new LLDPTimerTask(), 0, 3000);
	}

}
