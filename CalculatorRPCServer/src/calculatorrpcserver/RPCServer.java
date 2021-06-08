/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorrpcserver;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author nothing
 */
public class RPCServer {

    private ServerSocket serverSocket;

    //constructor
    public RPCServer() {

    }

    public void runServer(int port) throws IOException {

        serverSocket = new ServerSocket(port);
        String localIp = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Server is running on : " + localIp + ":" + port);

        while (true) {
            Socket rpSocket = serverSocket.accept();
            String address = rpSocket.getRemoteSocketAddress().toString();
            System.out.println("New Client Connected : " + address);
            new Thread(() -> {
                try {
                    hookCommand(rpSocket);
                } catch (IOException e) {
                    System.err.println("Client disconnected : " + address);
                }
                System.err.println("Client disconnected : " + address);

            }).start();
        }

    }

    public void hookCommand(Socket socket) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;

        while ((line = rd.readLine()) != null) {
            System.out.println("Client request : " + line);
            String[] commands = line.split(":",3);
            int result;
            int operand1 = Integer.parseInt(commands[1]);
            int operand2 = Integer.parseInt(commands[2]);
            String msg = "";

            switch (commands[0]) {
                case "+":
                    result = (operand1 + operand2);
                    msg = operand1 + " + " + operand2 + " = " + result;
                    break;
                case "-":
                    result = (operand1 - operand2);
                    msg = operand1 + " - " + operand2 + " = " + result;
                    break;
                case "*":
                    result = (operand1 * operand2);
                    msg = operand1 + " * " + operand2 + " = " + result;
                    break;
                case "/":
                    result = (operand1 / operand2);
                    msg = operand1 + " / " + operand2 + " = " + result;
                    break;
                case "%":
                    result = (operand1 % operand2);
                    msg = operand1 + " % " + operand2 + " = " + result;
                    break;

            }
            PrintStream printStream = new PrintStream(socket.getOutputStream(), true);
            printStream.println(msg);
        }
    }

}
