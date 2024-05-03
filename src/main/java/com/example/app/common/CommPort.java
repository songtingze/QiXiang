
package com.example.app.common;

import gnu.io.*;
import java.util.Enumeration;

public class CommPort {
	static Enumeration<CommPortIdentifier> portList;
	static CommPortIdentifier portId;
 
	/**
	 * �г��������ӵ����д�������
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		portList = CommPortIdentifier.getPortIdentifiers();
		while(portList.hasMoreElements()){
			portId = (CommPortIdentifier)portList.nextElement();
			if(portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
				System.out.println(portId.getName());
			}
		}
	}
}
