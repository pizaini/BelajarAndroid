package org.pizaini.mahasiswaonline;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.pizaini.mahasiswaonline.entities.ListAdapterMahasiswa;
import org.pizaini.mahasiswaonline.entities.Mahasiswa;
import org.pizaini.mahasiswaonline.server.ServerRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

public class MainActivity extends Activity implements OnQueryTextListener{
	private static final String TAG = "MainActivity";
	private ListView listView;
	private ActionMode actionMode;
	private ActionMode.Callback amCallback;
	private ProgressDialog progressDialog;
	private ServerRequest serverRequest;
	private List<Mahasiswa> list;
	private ListAdapterMahasiswa adapter;
	private Mahasiswa selectedList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main);
        serverRequest = new ServerRequest();
        listView = (ListView) findViewById(R.id.listview_main);
        amCallback = new ActionMode.Callback() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				actionMode = null;
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				getMenuInflater().inflate(R.menu.activity_main_action, menu);
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch(item.getItemId()){
					case R.id.action_menu_edit:
						showUpdateForm();
						break;
					case R.id.action_menu_delete:
						delete();
						break;
				}
				mode.finish();
				return false;
			}
		};                                                                                                                                                                                                                     
        list = new ArrayList<Mahasiswa>();
        /** melakukan load data melalui AsyncTask */
        new MainActivityAsync().execute("load");
    }

	private void showUpdateForm(){
		Intent in = new Intent(getApplicationContext(), FormMahasiswa.class);
		in.putExtra("id", selectedList.getId().toString());
		in.putExtra("nim", selectedList.getNim());
		in.putExtra("nama", selectedList.getNama());
		in.putExtra("telp", selectedList.getTelp());
		in.putExtra("alamat", selectedList.getAlamat());
		startActivity(in);
	}
	
	private void delete(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Delete "+selectedList.getNama()+" ?");
		builder.setTitle("Delete");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new MainActivityAsync().execute("delete");
				list.remove(list.indexOf(selectedList));
				Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.setIcon(android.R.drawable.ic_menu_delete);
		alert.show();	
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.option_menu_search).getActionView();      
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("nama atau nim");
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.option_menu_new:
    		Intent in = new Intent(getApplicationContext(), FormMahasiswa.class);
    		startActivity(in);
    		break;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    
    private List<Mahasiswa> processResponse(String response){
    	List<Mahasiswa> list = new ArrayList<Mahasiswa>();
		try {
			JSONObject jsonObj = new JSONObject(response);
			JSONArray jsonArray = jsonObj.getJSONArray("mahasiswa");
			Log.d(TAG, "data lengt: "+jsonArray.length());
			Mahasiswa mhs = null;
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject obj = jsonArray.getJSONObject(i);
				mhs = new Mahasiswa();
				mhs.setId(obj.getInt("id"));
				mhs.setNim(obj.getString("nim"));
				mhs.setNama(obj.getString("nama"));
				mhs.setTelp(obj.getString("telp"));
				mhs.setAlamat(obj.getString("alamat"));
				list.add(mhs);
			}
		} catch (JSONException e) {
			Log.d(TAG, e.getMessage());
		}
		return list;
	}
    
    private void populateListView(){
    	adapter = new ListAdapterMahasiswa(getApplicationContext(), list);
    	listView.setAdapter(adapter);
    	listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View v, int pos, long id) {
				if(actionMode != null){
					return false;
				}
				actionMode = startActionMode(amCallback);
				v.setSelected(true);
				selectedList = (Mahasiswa) adapter.getItem(pos);
				return true;
			}
			
		});
    	
    	
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int pos,
					long id) {
				selectedList = (Mahasiswa) adapter.getItem(pos);
				Intent in = new Intent(getApplicationContext(), DetailMahasiswa.class);
				in.putExtra("id", selectedList.getId().toString());
				in.putExtra("nim", selectedList.getNim());
				in.putExtra("nama", selectedList.getNama());
				in.putExtra("telp", selectedList.getTelp());
				in.putExtra("alamat", selectedList.getAlamat());
				startActivity(in);
			}
		});
       }
    
    private class MainActivityAsync extends AsyncTask<String, Void, String>{
    	
    	@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("retrieving...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			if(params[0] == "delete"){
				serverRequest.sendGetRequest(ServerRequest.urlDelete+"?id="+selectedList.getId().toString());
			}else{
				/** Mengirimkan request ke server dan memproses JSON response */ 
				String response = serverRequest.sendGetRequest(ServerRequest.urlSelectAll);
				if(!response.isEmpty()){
					list = processResponse(response);
				}else{
					Log.d(TAG, "Empty response");
				}
			}
			return null;
		}
		
		@Override
    	protected void onPostExecute(String result) {
    		progressDialog.dismiss();
    		runOnUiThread(new Runnable() {				
				@Override
				public void run() {
					populateListView();
				}
			});
    	}
    	
    }

	@Override
	public boolean onQueryTextChange(String newText) {
		adapter.getFilter().filter(newText);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}
    
}