// SendMessage.java - Sample application.
//
// This application shows you the basic procedure for sending messages.
// You will find how to send synchronous and asynchronous messages.
//
// For asynchronous dispatch, the example application sets a callback
// notification, to see what's happened with messages.

package com.example.app.common;

import org.smslib.*;
import org.smslib.Message.MessageEncodings;
import org.smslib.modem.SerialModemGateway;
public class SendMessage {
    public Result<String> doIt(String Mobile,String Content) throws Exception
    {
        Service srv;
        OutboundMessage msg;
        OutboundNotification outboundNotification = new OutboundNotification();
        System.out.println("Example: Send message from a serial gsm modem.");
        System.out.println(Library.getLibraryDescription());
        System.out.println("Version: " + Library.getLibraryVersion());
        srv = new Service();

        //COM8  115200
        SerialModemGateway gateway = new SerialModemGateway("modem.com1", "COM8", 115200, "wavecom", "17254");//115200�ǲ����ʣ�һ��Ϊ9600������ͨ�������ն˲��Գ���
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin("0000");

        try{
            gateway.setOutboundNotification(outboundNotification);
            srv.addGateway(gateway);
            srv.startService();

            System.out.println("Modem Information:");
            System.out.println("  Manufacturer: " + gateway.getManufacturer());
            System.out.println("  Model: " + gateway.getModel());
            System.out.println("  Serial No: " + gateway.getSerialNo());
            System.out.println("  SIM IMSI: " + gateway.getImsi());
            System.out.println("  Signal Level: " + gateway.getSignalLevel() + "%");
            System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
            System.out.println();
            // Send a message synchronously.

            String[] phones = Mobile.split(",");
            for(String phone:phones){
                msg = new OutboundMessage(phone, Content);
                msg.setEncoding(MessageEncodings.ENCUCS2);
                srv.sendMessage(msg);
            }

//            System.out.println(msg);
            System.out.println("Now Sleeping - Hit <enter> to terminate.");
//            System.in.read();
            srv.stopService();
            return Result.success("发送成功");
        }catch (GatewayException exception){
            return Result.error("-2","未连接短信猫");
        }

    }

    public class OutboundNotification implements IOutboundMessageNotification
    {
        public void process(String gatewayId, OutboundMessage msg)
        {
            System.out.println("Outbound handler called from Gateway: " + gatewayId);
            System.out.println(msg);
        }
    }
//
//    public static void main(String args[])
//    {
//        SendMessage app = new SendMessage();
//        try
//        {
//            app.doIt("15653408465","123");
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
}