import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This is the UDP Server that will communicate and connect with the Client through a socket
 * The port is 6789 for this test.
 * It will wait for the client to send messages to it and it will respond back with the
 * same message but in all caps.
 */
public class UDPServer {
    public static void main(String[] args) {
        System.out.println("Server: ");

        Scanner in = new Scanner(System.in);

        //Create socket
        DatagramSocket aSocket = null;
        try{
            System.out.println("Enter the port number: ");
            int portNum = in.nextInt();

            System.out.println("Waiting for client: ");

            //port 6789
            //Assign socket with the port number
            aSocket = new DatagramSocket(portNum);

            //Continuously waits for messages from the client in order to respond
            while(true){
                byte[] buffer = new byte[1000];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                //Receive the message from the client
                aSocket.receive(request);

                //Get the message and convert to string and display on the server
                String message = new String(request.getData());
                System.out.println("Received Message: " + message);
                //Change the message to all caps
                message = message.toUpperCase();
                //Return the new message to bytes
                byte [] messageBytes = message.getBytes();

                //Send the reply back to the client in a packet
                System.out.println("Sending Reply: " + message);
                DatagramPacket reply = new DatagramPacket(messageBytes, messageBytes.length, request.getAddress(), request.getPort());
                aSocket.send(reply);

            }
        }
        catch (SocketException e){  //Socket issues
            System.out.println("Socket: " + e.getMessage());
        }
        catch (IOException e){      //IO issues
            System.out.println("IO: " + e.getMessage());
        }
        finally {           //Close so there is no resource leak
            if(aSocket != null){
                aSocket.close();
            }
        }
    }
}
