

import javax.swing.*;


public class TCPIPClient {
    public static void main(String args[]) throws ChatException, InterruptedException {
        //String text = "hit";
        System.out.println("about to create a ConnectionProxy object  thread="+Thread.currentThread().getName());
        var proxy = new ConnectionProxy("127.0.0.1",1300);
        System.out.println("we now have a ConnectionProxy object   thread="+Thread.currentThread().getName());
        proxy.addConsumer(new Consumer());
        proxy.start();
        System.out.println("consumer was added to the ConnectionProxy object   thread="+Thread.currentThread().getName());
        System.out.println("the thread that calls the readUTF has started   thread="+Thread.currentThread().getName());

        class GUIGenerator implements Runnable {

            @Override
            public void run() {
                System.out.println("inside the run() of the GUIGenerator class thread="+Thread.currentThread().getName());
                ClientGUI gui = new ClientGUI();
                System.out.println("The SimpleClientGUI class was instantiated thread="+Thread.currentThread().getName());
                gui.start();
                System.out.println("The start method was called on a SimpleClientGUI object thread="+Thread.currentThread().getName());
                gui.addConsumer(proxy);
                System.out.println("The ConnectionProxy object was added as a consumer to the SimpleClientGUI object thread="+Thread.currentThread().getName());

            }
        }
        SwingUtilities.invokeLater(new GUIGenerator());

        //System.out.println("we are about to call consume on the ConnectionProxy object in order to send "+text);
        //pr.consume(text);
        //System.out.println("the text '"+text+"' was sent successfully");
        //Thread.sleep(100000000);
    }
}

class Consumer implements StringConsumer {

    @Override
    public void consume(String text) throws ChatException {
        System.out.println("'"+text+"' has just arrived from the server   thread="+Thread.currentThread().getName());
    }
}