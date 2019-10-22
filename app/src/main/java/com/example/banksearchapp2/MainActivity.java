package com.example.banksearchapp2;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banksearchapp2.model.Banks;
import com.example.banksearchapp2.network.APIClient;
import com.example.banksearchapp2.network.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    GridView gridView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.search_view_menu, menu);
        final SearchView searchView=(SearchView) menu.findItem(R.id.searchview).getActionView();
        SearchManager searchManager=(SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                final CustomAdapter[] customAdapter = new CustomAdapter[1];
                Call call=apiInterface.getBanks(query);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        List<Banks> banksList=(List<Banks>) response.body();
                        customAdapter[0] = new CustomAdapter((List<Banks>) response.body(), MainActivity.this);
                        gridView.setAdapter(customAdapter[0]);

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridview);
        final CustomAdapter[] customAdapter = new CustomAdapter[1];

        //make network call

        Call<List<Banks>> call = APIClient.apIinterface().getBanks("MUMBAI");
        call.enqueue(new Callback<List<Banks>>() {
            @Override
            public void onResponse(Call<List<Banks>> call, Response<List<Banks>> response) {
                if (response.isSuccessful()) {
                    customAdapter[0] = new CustomAdapter(response.body(), MainActivity.this);
                    gridView.setAdapter(customAdapter[0]);

                } else {

                    Toast.makeText(getApplicationContext(), "An error Occured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Banks>> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"An error Occured"+ t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public class CustomAdapter extends BaseAdapter{

        public List<Banks> banksList;
        public Context context;

        public CustomAdapter(List<Banks> banksList, Context context) {
            this.banksList = banksList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return banksList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            @SuppressLint("ViewHolder") View view= LayoutInflater.from(context).inflate(R.layout.row_data,null);
            //find view
            TextView ifsc=view.findViewById(R.id.textview);
            TextView branch=view.findViewById(R.id.textview2);
            TextView bank_name=view.findViewById(R.id.textview3);
            TextView city=view.findViewById(R.id.textview4);
            //setdata

            ifsc.setText(banksList.get(position).getIfsc());
            branch.setText(banksList.get(position).getBranch());
            bank_name.setText(banksList.get(position).getBank_name());
            city.setText(banksList.get(position).getCity());

            return view;
        }
    }
}