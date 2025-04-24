/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;
import java.io.*;
import java.net.*;
/**
 *
 * @author ASUS
 */

public class Client {
    public static void sendFile(String ip, int port, File file, java.util.function.Consumer<String> logger) {
        try (Socket socket = new Socket(ip, port);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             FileInputStream fis = new FileInputStream(file)) {

            dos.writeUTF(file.getName());
            dos.writeLong(file.length());

            byte[] buffer = new byte[4096];
            int read;
            while ((read = fis.read(buffer)) > 0) {
                dos.write(buffer, 0, read);
            }
            logger.accept("File sent: " + file.getName());
        } catch (IOException e) {
            logger.accept("Error: " + e.getMessage());
        }
    }
}

