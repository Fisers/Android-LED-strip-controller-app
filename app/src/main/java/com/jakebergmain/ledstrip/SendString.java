package com.jakebergmain.ledstrip;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 * Created by jake on 2/7/16.
 */
public class SendString extends AsyncTask<String, Void, Integer> {

    final static int SUCCESS = 0;
    final static int FAILURE = 1;

    static boolean waitForPacket = false;

    final int PORT = 2390;
    final int RESPONSE_PORT = 55056;

    final String LOG_TAG = SendString.class.getSimpleName();

    Context mContext;

    public SendString(Context context){
        mContext = context;
    }

    public void onPostExecute(Integer result) {
        if(result != null) {
            if (result == SUCCESS) {
                Log.v(LOG_TAG, "success");
            } else {
                Log.e(LOG_TAG, "error");
            }
        }
        else {
            Log.e(LOG_TAG, "error");
        }
    }

    public Integer doInBackground(String[] params){
        String pattern = params[0];


        Log.v(LOG_TAG, "pattern:" + pattern);

        DatagramSocket socket = null;

        InetAddress address;
        try {
            String ipAddrString = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0)
                    .getString(Constants.PREFERENCES_IP_ADDR, "");
            // remove slash at the beginning
            ipAddrString = ipAddrString.substring(1);
            address = InetAddress.getByName(ipAddrString);
        } catch (Exception e) {
            Log.w(LOG_TAG, "No valid IP in SharedPreferences");
            e.printStackTrace();
            return null;
        }

        // packet as a string
        String packetContents = pattern;

        try {
            // open a socket
            socket = new DatagramSocket(RESPONSE_PORT);
            // packet contents
            byte[] bytes = packetContents.getBytes();
            // send a packet with above contents to specified ip and port
            Log.v(LOG_TAG, "sending packet to " + address.toString());
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
            socket.send(packet);
            waitForPacket = true;

            // listen for a response
            byte[] response = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(response, response.length);
            socket.setSoTimeout(50);

            String text = "";
            try {
                Log.v(LOG_TAG, "Listening for a response");
                socket.receive(responsePacket);
                waitForPacket = false;
                text = new String(response, 0, responsePacket.getLength());
                Log.v(LOG_TAG, "Received packet.  contents: " + text);
            } catch (SocketTimeoutException e) {
                Log.w(LOG_TAG, "Socket timed out");
                waitForPacket = false;
                return FAILURE;
            }

            if(text.equals("acknowledged")) {
                return SUCCESS;
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in SendString doInBackground()");
            e.printStackTrace();

            return FAILURE;
        } finally {
            if(socket != null) {
                socket.close();
            }
        }

        return FAILURE;
    }
}
