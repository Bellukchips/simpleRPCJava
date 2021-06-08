/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorrpcserver;

import java.io.IOException;

/**
 *
 * @author nothing
 */
public class CalculatorRPCServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        RPCServer server = new RPCServer();
        server.runServer(3000);
    }

}
