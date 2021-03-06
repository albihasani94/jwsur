package jwsur.chap1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class SimpleHttpClient {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: SimpleHttpClient <url>");
        }

        try {
            URL url = new URL(args[0]);
            String host = url.getHost();
            String path = url.getPath();
            int port = url.getPort();
            if (port < 0) {
                port = 80;
            }

            String request = "GET " + path + " HTTP/1.1\n";
            request = request + "host: " + host;
            request = request + "\n\n";

            Socket sock = new Socket(host, port);

            PrintWriter writer = new PrintWriter(sock.getOutputStream());
            writer.println(request);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String next_record = null;
            while ((next_record = reader.readLine()) != null) {
                System.out.println(next_record);
            }

            sock.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Please try again. Unknown host.\n" + e);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Please try again. Bad URL.\n" + e);
        } catch (IOException e) {
            throw new RuntimeException("Please try again. Something's wrong.\n" + e);
        }
    }
}
