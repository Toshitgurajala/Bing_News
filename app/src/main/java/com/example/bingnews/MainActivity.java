package com.example.bingnews;
import static android.graphics.Paint.UNDERLINE_TEXT_FLAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
LinearLayout linearlayout;
ProgressDialog prgd;
SearchView srch;
    static String subscriptionKey = "Your_Subscription_Key";
    static String host = "https://api.bing.microsoft.com";
    static String searchTerm = "Toshit Gurajala";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        srch=findViewById(R.id.search);
        linearlayout= findViewById(R.id.layout);
        srch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                prgd = new ProgressDialog(MainActivity.this);
                prgd.setMessage("Fetching News");
                prgd.setCancelable(false);
                prgd.show();
                linearlayout.removeAllViewsInLayout();
                searchTerm=s;
//                Toast.makeText(getApplicationContext(),""+searchTerm,Toast.LENGTH_SHORT).show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(host)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                MyApiCall myapicall = retrofit.create(MyApiCall.class);
                Call<DataModel> call = myapicall.makeHttpRequest(searchTerm, subscriptionKey);
                call.enqueue(new Callback<DataModel>() {
                    @Override
                    public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                        //checking response
                        if (response.code() != 200) {
                            Toast.makeText(getApplicationContext(), "Check Connection  " + response.code(), Toast.LENGTH_SHORT).show();
                        } else {
                            DataModel data = response.body();
                            List<DataModel.NewsArticle> newsArticles = data.getValue();
                            for (int i = 0; i < 30 && i < newsArticles.size(); i++) {
                                {
                                    // Access each news article object and its properties
                                    DataModel.NewsArticle article = newsArticles.get(i);
                                    String articleName = article.getName();
                                    String articledesc = article.getDescription();
                                    DataModel.Image img = article.getImage();
                                    List<DataModel.NewsArticle.Provider> source = article.getProvider();

                                   StringBuilder newssource= new StringBuilder("");
                                   for(int j=0;j<2&&j<source.size();j++) {
                                       DataModel.NewsArticle.Provider provide= source.get(j);
                                      newssource.append(provide.getName());
                                   }
                                    String articleUrl=article.getUrl();
                                    String date=article.getDatePublished();
                                    try{
                                    if(img.getThumbnail()!=null) {
                                        DataModel.Thumbnail thumb = img.getThumbnail();
                                        String imgurl = thumb.getContentUrl();
                                        int width = thumb.getWidth();
                                        int height = thumb.getHeight();
                                        addview(articleName, articledesc, imgurl, width, height,articleUrl,date,newssource);
                                    }}
                                    catch (Exception e){
                                     {
                                        String imgurl = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAPFBMVEXxURuAzCgAre/7vAn////xTxfyWyuHzzbyXjD7wCUcsfD8wB+J0Dohs/DxTRLyWSj0Zz2Q00Yxt/H8xDNPT4sKAAABEUlEQVR4nO3PWxIBURAFwcbMYLzZ/159uis4osnaQEVWpZqX6dN+EysGJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQk7CicU11vXxIeUi33Qfg4x6rp1yPsH2H/CPtH2D/C/tV6SfUctq9jrNrGWgfhaReLkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQkJCQ8J+Fb80SdsZMgA+nAAAAAElFTkSuQmCC";
                                        int width = 0;
                                        int height = 200;
                                        addview(articleName, articledesc, imgurl, width, height, articleUrl, date,newssource);
                                    }
                                }
                                }
                            }
                        }
                        prgd.cancel();
                    }

                    private void addview( String articleName,String articledesc, String imgurl, int width,int height,String articleurl,String date,StringBuilder newssource) {
                        linearlayout=findViewById(R.id.layout);
                        View addnews = getLayoutInflater().inflate(R.layout.newsview, null, false);
                        TextView textview = (TextView) addnews.findViewById(R.id.titleview);
                        TextView title = (TextView) addnews.findViewById(R.id.textdesc);
                        TextView dateview =(TextView)addnews.findViewById(R.id.dateview);
                        TextView sourceview =(TextView)addnews.findViewById(R.id.provider);
                        sourceview.setText("Source: "+newssource);
                        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
                        Date dateformat = null;
                        try {
                            dateformat = originalFormat.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                        String newString = newFormat.format(dateformat);
                        dateview.setText(newString);
                        textview.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                        ImageView imageView = (ImageView)addnews.findViewById(R.id.imageView);
                        linearlayout.addView(addnews);
                        String articleUrl=articleurl;
                        textview.setText(articleName);
                        title.setText(articledesc);
                         if(imgurl==null)
                         {
                             imgurl="https://img-prod-cms-rt-microsoft-com.akamaized.net/cms/api/am/imageFileData/RE1Mu3b?ver=5c31";
                         }
                         else
                        Glide.with(MainActivity.this)
                                .load(imgurl)
                                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .centerCrop()
                                .into(imageView);
                        addnews.setOnClickListener(new View.OnClickListener() {
                            Uri uri;
                            @Override
                            public void onClick(View view) {
                                uri = Uri.parse(articleUrl);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<DataModel> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });





    }



    }

