package org.pizaini.mahasiswaonline.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.pizaini.mahasiswaonline.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class ListAdapterMahasiswa extends BaseAdapter implements Filterable{
	private Context context;
	private List<Mahasiswa> list, filterd;
	
	public ListAdapterMahasiswa(Context context, List<Mahasiswa> list) {
		this.context = context;
		this.list = list;
		this.filterd = this.list;
	}
	
	@Override
	public int getCount() {
		return filterd.size();
	}

	@Override
	public Object getItem(int position) {
		return filterd.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(this.context);
			convertView = inflater.inflate(R.layout.list_row, null);
		}
		Mahasiswa mhs = filterd.get(position);
		TextView textNama = (TextView) convertView.findViewById(R.id.text_nama);
		textNama.setText(mhs.getNama());
		
		TextView textNim = (TextView) convertView.findViewById(R.id.text_nim);
		textNim.setText(mhs.getNim());
		
		return convertView;
	}
	
	@Override
	public Filter getFilter() {
		MahasiswaFilter filter = new MahasiswaFilter();
		return filter;
	}
	
	/** Class filter untuk melakukan filter (pencarian) */
	private class MahasiswaFilter extends Filter{
			
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			List<Mahasiswa> filteredData = new ArrayList<Mahasiswa>(); 
			FilterResults result = new FilterResults();
			String filterString = constraint.toString().toLowerCase(Locale.getDefault());
			for(Mahasiswa mhs: list){
				if(mhs.getNama().toLowerCase().contains(filterString) || mhs.getNim().toLowerCase().contains(filterString)){
					filteredData.add(mhs);
				}
			}
			result.count = filteredData.size();
			result.values =  filteredData;
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filterd =  (List<Mahasiswa>) results.values;
			notifyDataSetChanged();
		}

	}

}