package com.richardhenke.cit360sandbox1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by richard on 1/23/15.
 * #4 - This is where we will make our request to the web for data.
 */
public class HttpManager {
   public static String getData(String uri) {

      BufferedReader reader = null;

      try {
         URL url = new URL(uri);
         HttpURLConnection con = (HttpURLConnection) url.openConnection();

         StringBuilder sb = new StringBuilder();
         reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

         String line;
         while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
         }
         return sb.toString();

      } catch (Exception e) {
         e.printStackTrace();
         return null;
      } finally {
         if (reader != null) {
            try {
               reader.close();
            } catch (IOException e) {
               e.printStackTrace();
               return null;
            }
         }
      }
   }
}
