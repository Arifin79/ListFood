package com.example.berita.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.berita.R;
import com.example.berita.db.MakananHelper;
import com.example.berita.db.MinumanHelper;
import com.example.berita.model.makanan.MealsItem;
import com.example.berita.model.minuman.DrinksItem;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    public static String KEY_DETAIL_DATA = "detail_data";
    public static String KEY_JENIS_DATA = "jenis_data";
    public String jenisData;
    private String title, description, poster;
    private int id;
    private Menu menuItem = null;
    private Boolean isFavorite = false;
    private MealsItem makanan;
    private DrinksItem minuman;
    private MakananHelper makananHelper;
    private MinumanHelper minumanHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView DetailTitle = findViewById(R.id.txt_detail_title);
        TextView DetailDescription = findViewById(R.id.txt_detail_content);
        ImageView imgDetailPoster = findViewById(R.id.img_detail_poster);



        jenisData = getIntent().getStringExtra(KEY_JENIS_DATA);

        if (jenisData.equals("meals")) {
            makananHelper = MakananHelper.getInstance(getApplicationContext());
            try {
                makananHelper.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            makanan = getIntent().getParcelableExtra(KEY_DETAIL_DATA);
            id = makanan.getIdCategory();
            title = makanan.getStrCategory();
            description = makanan.getStrCategoryDescription();
            poster = makanan.getStrCategoryThumb();
            setTitle(title);
        } else if (jenisData.equals("drinks")) {
            minumanHelper = MinumanHelper.getInstance(getApplicationContext());
            try {
                minumanHelper.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            minuman = getIntent().getParcelableExtra(KEY_DETAIL_DATA);
            id = minuman.getIdDrink();
            title = minuman.getStrDrink();
            poster = minuman.getStrDrinkThumb();
            setTitle(title);
        }

        DetailTitle.setText(title);
        DetailDescription.setText(description);
        Glide.with(this).load(poster).into(imgDetailPoster);

        favoriteState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_to_favorite:
                if (isFavorite) {
                    removeFromFavorite();
                } else {
                    addToFavorite();
                }

                isFavorite = !isFavorite;
                setFavorite();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFavorite() {
        if (isFavorite) {
            menuItem.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        } else {
            menuItem.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }
    }

    private void addToFavorite() {
        if (jenisData.equals("meals")) {
            long result = makananHelper.insertMakanan(makanan);

            if (!(result > 0)) {
                Toast.makeText(DetailActivity.this, R.string.msg_failed_Favorite, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailActivity.this, R.string.msg_success_Favorite, Toast.LENGTH_SHORT).show();
            }
        } else if (jenisData.equals("drinks")) {
            long result = minumanHelper.insertMinuman(minuman);

            if (!(result > 0)) {
                Toast.makeText(DetailActivity.this, R.string.msg_failed_Favorite, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailActivity.this, R.string.msg_success_Favorite, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void removeFromFavorite() {
        if (jenisData.equals("meals")) {
            long result = makananHelper.deleteMakanan(id);

            if (!(result > 0)) {
                Toast.makeText(DetailActivity.this, R.string.failed_delete_favorite, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailActivity.this, R.string.success_delete_favorite, Toast.LENGTH_SHORT).show();
            }
        } else if (jenisData.equals("drinks")) {
            long result = minumanHelper.deleteMinuman(id);

            if (!(result > 0)) {
                Toast.makeText(DetailActivity.this, R.string.failed_delete_favorite, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailActivity.this, R.string.success_delete_favorite, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        menuItem = menu;
        setFavorite();
        return true;
    }

    private void favoriteState() {

        if (jenisData.equals("meals")) {

            MealsItem mealsItem = makananHelper.getMakananById(id);

            if (mealsItem != null) {
                Log.d("detail", "favoriteState: data favorite ditemukan");
                Log.d("detail", "favoriteState: " + mealsItem);

                List<MealsItem> mealsItemList = new ArrayList<>();
                mealsItemList.add(0, mealsItem);

                if (mealsItemList.isEmpty()) {
                    isFavorite = false;
                    Log.d("detail", "favoriteState: data favorite tidak ditemukan");
                } else {
                    isFavorite = true;
                }
            } else {
                isFavorite = false;
                Log.d("detail", "favoriteState: data favorite tidak ditemukan");
            }

        } else if (jenisData.equals("drinks")) {

            DrinksItem drinksItem = minumanHelper.getMinumanById(id);

            if (drinksItem != null) {
                Log.d("detail", "favoriteState: data favorite ditemukan");
                Log.d("detail", "favoriteState: " + drinksItem);

                List<DrinksItem> drinksItemList = new ArrayList<>();
                drinksItemList.add(0, drinksItem);

                if (drinksItemList.isEmpty()) {
                    isFavorite = false;
                    Log.d("detail", "favoriteState: data favorite tidak ditemukan");
                } else {
                    isFavorite = true;
                }
            } else {
                isFavorite = false;
                Log.d("detail", "favoriteState: data favorite tidak ditemukan");
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (jenisData.equals("meals")) {
            makananHelper.close();
        } else if (jenisData.equals("drinks")) {
            minumanHelper.close();
        }
    }
}
