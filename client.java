import java.net.*;
import java.nio.channels.SocketChannel;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.FontUIResource;

import java.awt.Font;
import java.awt.Image;
import java.io.*;

public class client extends JFrame {
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    // COMPONENT 
    // private JLabel heading = new JLabel("USERS");
    // private JTextArea output = new JTextArea();
    // private JTextField input = new JTextField();
    // private Font  font = new FontUIResource("Roboto", Font.PLAIN, 20);
    

    // constuctor
    public client() {
        try {
            System.out.println("sending request to server");
            socket = new Socket("127.0.0.1", 9694); // 127.0.0.1 own // 193.36.45.3 other
            System.out.println("connection done.");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());
               
          // createGUI();
            startReading();
            startwirting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // private void createGUI(){
    //     ImageIcon i = new ImageIcon("chat.jpg");
    //     this.setTitle("USERs MESSANGER");
    //     this.setSize(500,500);
    //     this.setLocationRelativeTo(null);
    //     this.setIconImage(i.getImage());
    //     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     this.setForeground(i.getImage());
    //     heading.setFont(font);
    //     output.setFont(font);
    //     input.setFont(font);
    //     this.setVisible(true);

   // }
    /// write

    
    public void startwirting() { // send to data for client\
        System.out.println("start writing ....");
        Runnable r2 = () -> {
            while (true) {

                try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    out.println();

                    String content = br1.readLine();
                    out.println(content);

                    out.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        new Thread(r2).start();
    }

    // reading
    public void startReading() { // thread data ko read krke dega
        Runnable r1 = () -> {

            System.out.println("reader started..");
            while (true) {

                try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("client terminated the chat");
                        break;
                    }
                    System.out.println("server : " + msg);

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        };
        new Thread(r1).start();

    }

    // debuging

    public static void main(String[] args) {
        System.out.println("this is client...");
        new client();
    }
}
