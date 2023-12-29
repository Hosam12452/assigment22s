package assaf.hosam.assiment22;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Population extends AppCompatActivity {
    private RequestQueue requestQueue;
    private Spinner spinner;
    Button show;
    Button perc;
    TextView populationtext;
    private String jsonContrey;
    String selectedname;
    String selectedCode;
    int selectedDate;
   public String selectedCountry;
    boolean flag=false;
    TextView usertxt;
CalendarView calender;
    private volatile AtomicInteger p = new AtomicInteger(1);
    private volatile AtomicInteger m = new AtomicInteger(1);
private JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         spinner=findViewById(R.id.countryspinner);
         show=findViewById(R.id.show);
         populationtext=findViewById(R.id.population);
         perc=findViewById(R.id.percantage);
        requestQueue = Volley.newRequestQueue(this);
        calender=findViewById(R.id.calendarView);
        usertxt=findViewById(R.id.username);
        Intent intent=getIntent();
        String username=intent.getStringExtra("firstName");
        usertxt.append("Welcome "+username);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = year ;
            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
              create_spinner();
            }
        });
        thread.start();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCountry="";
                if (i > 0) {
                    selectedCountry = (String) adapterView.getItemAtPosition(i).toString().trim();
                    String[] country = selectedCountry.split(",");
                    selectedname = country[0];
                    selectedCode = country[1];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

   perc.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           if(p.get()!=1&&flag!=true) {
               percent();
               flag=true;
           }else{
               Toast.makeText(Population.this, "Select Country First", Toast.LENGTH_SHORT).show();
           }
       }
   });
   show.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           if(!selectedCountry.equals("")){
               log2();

           }else {
               Toast.makeText(Population.this, "Select An Country!", Toast.LENGTH_SHORT).show();
           }
       }
   });
    }


    public void log2() {

            if (selectedCountry == null) {
                Toast.makeText(this, "Country data not available", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if(selectedDate==0||selectedDate>2022){
                    selectedDate=2022;
                }
                flag = false;
                populationtext.setText("");
                String api = "https://api.worldbank.org/v2/country/" + selectedCode + "/indicator/SP.POP.TOTL?date="+selectedDate+"&format=json";
                String malePopulationApi = "https://api.worldbank.org/v2/country/" + selectedCode + "/indicator/SP.POP.TOTL.MA.IN?date="+selectedDate+"&format=json";
                String femalePopulationApi = "https://api.worldbank.org/v2/country/" + selectedCode + "/indicator/SP.POP.TOTL.FE.IN?date="+selectedDate+"&format=json";
                Thread threadp = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        makeApiCall(api, "p");

                    }
                });

                Thread threadm = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        makeApiCall(malePopulationApi, "m");

                    }
                });
                Thread threadf = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        makeApiCall(femalePopulationApi, "f");


                    }
                });

                threadp.start();
                threadm.start();
                threadf.start();

                try {
                    threadp.join();
                    threadm.join();
                    threadf.join();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }




    private void create_spinner() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

          String API = "https://restcountries.com/v3.1/all";
        String countriesJsonString="";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray countriesJsonList = new JSONArray();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject countryJson = response.getJSONObject(i);
                                String name = countryJson.getJSONObject("name").getString("common");
                                String code = countryJson.getString("cca3");

                                JSONObject countryInfo = new JSONObject();
                                countryInfo.put("name", name);
                                countryInfo.put("code", code);

                                countriesJsonList.put(countryInfo);
                            }
                                List<String> countrylist = new ArrayList<>();
                                 countrylist.add("Select An Country");

                                 for (int i = 0; i < countriesJsonList.length(); i++) {
                                try {

                                    JSONObject countryObject = countriesJsonList.getJSONObject(i);
                                    String name = countryObject.getString("name");
                                    String code=countryObject.getString("code");
                                    String sp=name+" , "+code;
                                    countrylist.add(sp);

                                     }catch (JSONException e){
                                    e.printStackTrace();

                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Population.this, android.R.layout.simple_spinner_item, countrylist);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner.setAdapter(adapter);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(jsonArrayRequest);

    }

    private JSONArray jsonArray(String jsonString) {
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonArray;
    }
private void percent(){
    int malePopulation = m.get();
    int pop=p.get();
    int femalePopulation = pop - malePopulation;
    double femalePercentage = ((double) femalePopulation / pop) * 100;
    double malePercentage = ((double) malePopulation / pop) * 100;
    populationtext.setText(populationtext.getText() + "\n" +
            String.format("Female Per%% : %.2f%%\nMale Per%% : %.2f%%", femalePercentage, malePercentage));
}
    private void makeApiCall(String api,String type) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                api,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject dataObject = response.getJSONArray(1).getJSONObject(0);

                            int population = dataObject.getInt("value");
                            if(type.equals("p")){
                           populationtext.setText(populationtext.getText()+"Population : "+population+"\n");
                                p.set(population);


                            } else if (type.equals("m")) {
                                m.set(population);
                                populationtext.setText(populationtext.getText()+"Males : "+population+"\n");

                            }else{

                                populationtext.setText(populationtext.getText()+"Females : "+population+"\n");

                            }

                           // Toast.makeText(Population.this, "Population: " + population, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Population.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

}
