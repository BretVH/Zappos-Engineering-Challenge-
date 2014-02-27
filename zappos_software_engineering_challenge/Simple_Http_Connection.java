/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zappos_software_engineering_challenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Bret
 */
public class Simple_Http_Connection 
{
    public static String httpGet(String urlStr) throws IOException {
    URL url = new URL(urlStr);
    HttpURLConnection conn =
      (HttpURLConnection) url.openConnection();

    if (conn.getResponseCode() != 200) {
    throw new IOException(conn.getResponseMessage());
    }
    StringBuilder sb;
      try (BufferedReader rd = new BufferedReader(
              new InputStreamReader(conn.getInputStream()))) {
          sb = new StringBuilder();
          String line;
          while ((line = rd.readLine()) != null) {
              sb.append(line);
          }   }

    conn.disconnect();
    return sb.toString();

    }
}
