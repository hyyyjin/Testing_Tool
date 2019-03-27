package com.Global;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.component.Node;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version ����ʱ�䣺2018��6��12�� ����4:25:28 ��˵��
 */
public class NodeList {
	public static ConcurrentMap<Long, Node> nodeList = new ConcurrentHashMap<Long, Node>();
}
