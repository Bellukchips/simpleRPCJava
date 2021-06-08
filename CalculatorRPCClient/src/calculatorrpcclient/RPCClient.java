/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorrpcclient;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nothing
 */
public class RPCClient {

    private PrintStream printStream;
    FormCalculator form;

    public RPCClient(FormCalculator form) throws IOException {

        this.form = form;
        Socket rpcClient = new Socket(form.getTxtIP().getText(), Integer.parseInt(form.getTxtPort().getText()));
        new Thread(() -> {
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(rpcClient.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    System.out.println("Server respone : " + line);
                    form.getLblHasil().setText("Server response : " + line);
                }
            } catch (IOException ex) {
                System.out.println("\nDisconnected");
                System.exit(0);
            }
        }).start();
        printStream = new PrintStream(rpcClient.getOutputStream(), true);
    }
    
    public void sendRequestToServer(String opr){
        int value1 = Integer.parseInt(form.getTxtOpr1().getText());
        int value2 = Integer.parseInt(form.getTxtOpr2().getText());
        printStream.println(opr + ":" + value1 + ":" + value2);
    }

}
