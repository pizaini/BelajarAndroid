package org.pizaini.mahasiswaonline;

import org.apache.http.HttpStatus;
import org.pizaini.mahasiswaonline.entities.Mahasiswa;
import org.pizaini.mahasiswaonline.server.ServerRequest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class FormMahasiswa extends Activity {
	private EditText textNim, textNama, textTelp, textAlamat;
	private ProgressDialog progressDialog;
	private ServerRequest server;
	private int replyCode;
	private Mahasiswa mahasiswa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_mahasiswa);
		initView();
		server = new ServerRequest();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		mahasiswa = new Mahasiswa();
		if(getIntent().hasExtra("id")){
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
		}else{
			mahasiswa.setId(0);
		}
	}
	
	private void initView(){
		textNim = (EditText) findViewById(R.id.add_new_nim);
		textNama = (EditText) findViewById(R.id.add_new_nama);
		textTelp = (EditText) findViewById(R.id.add_new_telp);
		textAlamat = (EditText) findViewById(R.id.add_new_alamat);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.form_mahasiswa, menu);
		return true;
	}
	
	private void goToMainActivity(){
		Intent in = new Intent(getApplicationContext(), MainActivity.class);
		in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(in);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			goToMainActivity();
			break;
			
		case R.id.option_menu_save:
			if(textNim.getText().toString().trim().isEmpty() || textNama.getText().toString().trim().isEmpty()){
				Toast.makeText(getApplicationContext(), "NIM dan Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
			}else{
				new FormMahasiswaAsync().execute();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void sendRequest(){
		String nim = textNim.getText().toString();
		String nama = textNama.getText().toString();
		String telp = textTelp.getText().toString();
		String alamat = textAlamat.getText().toString();
		mahasiswa.setNim(nim);
		mahasiswa.setNama(nama);
		mahasiswa.setTelp(telp);
		mahasiswa.setAlamat(alamat);
		/**Mengirimkan POST reques*/
		replyCode = server.sendPostRequest(mahasiswa, ServerRequest.urlSubmit);
	}
	
	private class FormMahasiswaAsync extends AsyncTask<String, String, String>{
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(FormMahasiswa.this);
			progressDialog.setMessage("saving data...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			sendRequest();
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			if(replyCode == HttpStatus.SC_OK){
				goToMainActivity();
			}else{
				Toast.makeText(getApplicationContext(), "save data problem", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

}
