/*
1520 Assignment 1
Adhyaksa Pribadi
*/

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.net.Socket;
import java.net.ServerSocket;

public class Server {

  public static void main(String[] arguments) throws Exception {

    // we are going to construct a ServerSocket that will listen on port 8080.
    ServerSocket serverSocket = new ServerSocket(8080);
    Socket clientConnection = null;
    
    do {
    
      // this call will block until a client tries to connect.
      clientConnection = serverSocket.accept();
      
      // if you open your browser and go to http://localhost:8080/ this program will receive the
      // request.
      InputStream in = clientConnection.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      
      // we'll use the code below to read the HTTP Request line-by-line.
      String line = reader.readLine();
      String[] param = new String[line.length()];
      String[] temp = new String[line.length()];
      String user = null;
      
      while (line != null && !line.equals("")) {
        // we'll output each line to the console so you can see what the HTTP request looks like.
        if(line.contains("GET") && line.contains("?"))
        {
            String holder = line;
            String remove = holder.replace("HTTP/1.1", "");
            temp = remove.split("\\?");
            param = temp[1].split("&");
            
        }
        if(line.contains("User"))
        {
            user = line;
        }
        System.out.println(line);
        line = reader.readLine();
      }
            
      // this code will write a basic HTTP Response
      OutputStreamWriter out = new OutputStreamWriter(clientConnection.getOutputStream());
      out.write("HTTP/1.1 200 OK\n");
      out.write("Content-Type: text/html; charset=utf-8\n\n");
      out.write("<html><body>Adhyaksa Pribadi</body></html>");
      for(int i = 0; i<param.length;i++)
      {
          if(param[i] !=null)
           out.write("<html><p>"+param[i]+"</p></html>");
      }
      out.write("<html><p>"+user+"</p></html>");
      out.close();
    
    } while (clientConnection != null);
  }
}
