package com.richardhenke.cit360sandbox1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RichTest1 extends ActionBarActivity {

   TextView output;
   ProgressBar pb;
   List<MyTask> tasks;

   /************************************************************************************************
    * This is the first method created, you might think of it as the main() in other languages.
    ***********************************************************************************************/
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_rich_test1);

      // Initialize the TextView for vertical scrolling
      output = (TextView) findViewById(R.id.textView);
      output.setMovementMethod(new ScrollingMovementMethod());

      pb = (ProgressBar) findViewById(R.id.progressBar1);
      pb.setVisibility(View.INVISIBLE);

      tasks = new ArrayList<>();
   }


   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_rich_test1, menu);
      return true;
   }


   /************************************************************************************************
    * #1 - User taps the "DO TASK" Button and we check to see if there is a network connection.
    * If there IS a connection we initiate the requestData method with the URL.
    ***********************************************************************************************/
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.

      if (item.getItemId() == R.id.action_do_task) {

         if (isOnline()) {
            requestData("http://services.hanselandpetal.com/feeds/flowers.xml");
         } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
         }

      }
      return false;
   }

   /************************************************************************************************
    * #2 - This will create what's called an "Async task" and the second line execute it passing in
    * the uri.
    ***********************************************************************************************/
   private void requestData(String uri) {
      MyTask task = new MyTask();
      task.execute(uri);
   }

   /************************************************************************************************
    * #5 - This pushes the content to the screen separated by new lines.
    ***********************************************************************************************/
   protected void updateDisplay(String message) {
      output.append(message + "\n");
   }

   /************************************************************************************************
    * This checks to see if there is there is a network connection.
    ***********************************************************************************************/
   protected boolean isOnline() {
      ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo netInfo = cm.getActiveNetworkInfo();

      if (netInfo != null && netInfo.isConnectedOrConnecting()) {
         return true;
      } else {
         return false;
      }
   }

   private class MyTask extends AsyncTask<String, String, String> {

      /********************************************************************************************
       * This sets the progress bar to visible if there are tasks in the task list.
       *******************************************************************************************/
      @Override
      protected void onPreExecute() {
         updateDisplay("Starting task");

         // Sets visibility only if the que in the tasks list is zero
         if (tasks.size() == 0) {
            pb.setVisibility(View.VISIBLE);
         }
         tasks.add(this);

      }

      /********************************************************************************************
       * #3 - This calls the static method HttpManager getData method and passes that uri through
       * the params. (#4 - is in the getData() class)
       *******************************************************************************************/
      @Override
      protected String doInBackground(String... params) {
         String content = HttpManager.getData(params[0]);
         return content;
      }

      /********************************************************************************************
       * #5 - This gets the return form the Async class and pushes it to the updateDisplay (#5).
       * This also sets the progress bar to invisible when there are no more tasks left in the list
       * que.
       *******************************************************************************************/
      @Override
      protected void onPostExecute(String result) {
         updateDisplay(result);
         tasks.remove(this);
         // Sets invisibility of the progress bar only if the que in the tasks list is zero
         if (tasks.size() == 0) {
            pb.setVisibility(View.INVISIBLE);
         }
      }

      /********************************************************************************************
       * This updates the main thread since this background thread can't communicate directly to it.
       *******************************************************************************************/
      @Override
      protected void onProgressUpdate(String... values) {
         updateDisplay(values[0]);
      }
   }

}
