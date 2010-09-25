package nl.trafex.android.project;

import java.net.URI;
import java.net.URISyntaxException;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ProjectAndroid extends ListActivity {
	
	//private static final int MENU_REFRESH = 0;
	private static final int MENU_ABOUT = 0;
	
	private Handler mHandler;
	private ProgressDialog loadingDialog;
	private ArrayAdapter<String> articles;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        loadingDialog = ProgressDialog.show(ProjectAndroid.this, "", 
                "Loading. Please wait...", true);
        mHandler = new Handler();
    	
        getFeed.start();
    }
    
    private Thread getFeed = new Thread() {
        public void run() {
	        RssReader reader = new RssReader();
			try {
				URI uri = new URI("http://feeds.feedburner.com/tweakers/mixed");
				articles = reader.getFeed(getApplicationContext(), uri);
				mHandler.post(showFeed);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    };
    
    private Runnable showFeed = new Runnable(){
       	public void run(){
       		updateUi();
        }
    };
    
    public void updateUi()
    {
    	setListAdapter(articles);		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		loadingDialog.dismiss();
		
		lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		    	
		      // When clicked, show a toast with the TextView text
		      Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
		          Toast.LENGTH_SHORT).show();
		    }
		  });
    }
    
    /* Creates the menu items */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_ABOUT, 0, "About").setIcon(android.R.drawable.ic_menu_info_details);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_ABOUT:	
        	showAbout();
            return true;
        }
        return false;
    }
    
    public void showAbout() {
    	Dialog dialog = new Dialog(this);

    	dialog.setContentView(R.layout.about);
    	dialog.setTitle("About");
    	dialog.show();
    }

}