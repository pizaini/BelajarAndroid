package org.pizaini.mahasiswaonline;

import org.pizaini.mahasiswaonline.entities.Mahasiswa;
import org.pizaini.mahasiswaonline.server.ServerRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class DetailMahasiswa extends Activity {
	private EditText textNim, textNama, textTelp, textAlamat;
	private Mahasiswa mahasiswa;
	private ServerRequest server;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_mahasiswa);
		mahasiswa = new Mahasiswa();
		server = new ServerRequest();
		initView();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	private void initView(){
		textNim = (EditText) findViewById(R.id.add_new_nim);
		textNama = (EditText) findViewById(R.id.add_new_nama);
		textTelp = (EditText) findViewById(R.id.add_new_telp);
		textAlamat = (EditText) findViewById(R.id.add_new_alamat);
		
		String id = getIntent().getStringExtra("id");
		String nim = getIntent().getStringExtra("nim");
		String nama = getIntent().getStringExtra("nama");
		String telp = getIntent().getStringExtra("telp");
		String alamat = getIntent().getStringExtra("alamat");
		
		textNim.setText(nim);
		textNama.setText(nama);
		textTelp.setText(telp);
		textAlamat.setText(alamat);
		
		mahasiswa.setId(Integer.valueOf(id));
		mahasiswa.setNim(nim);
		mahasiswa.setNama(nama);
		mahasiswa.setTelp(telp);
		mahasiswa.setAlamat(alamat);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_action, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			goToMainActivity();
			break;
			
		case R.id.action_menu_edit:
			Intent in = new Intent(getApplicationContext(), FormMahasiswa.class);
			in.putExtra("id", mahasiswa.getId().toString());
			in.putExtra("nim", mahasiswa.getNim());
			in.putExtra("nama", mahasiswa.getNama());
			in.putExtra("telp", mahasiswa.getTelp());
			in.putExtra("alamat", mahasiswa.getAlamat());
			startActivity(in);
			break;
			
		case R.id.action_menu_delete:
			delete();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void goToMainActivity(){
		Intent in = new Intent(getApplicationContext(), MainActivity.class);
		in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(in);
	}
	
	private void delete(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Delete "+mahasiswa.getNama()+" ?");
		builder.setTitle("Delete");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new DetailMahasiswaAsync().execute();
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
	private class DetailMahasiswaAsync extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			server.sendGetRequest(ServerRequest.urlDelete+"?id="+mahasiswa.getId().toString());
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			Intent in = new Intent(getApplicationContext(), MainActivity.class);
			in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
		}
		
	}
	
}
