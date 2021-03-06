package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.img_unavailable)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownTextView = (TextView) findViewById(R.id.also_known_tv);
        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        TextView originTextView = (TextView) findViewById(R.id.origin_tv);
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);

        /*
            If alsoKnownAs or origin are empty, set string to detail_error_message to denote information is absent.
        */
        String alsoKnownAs = sandwich.getAlsoKnownAs().isEmpty() ? getString(R.string.detail_error_message) :
                Arrays.toString(sandwich.getAlsoKnownAs().toArray()).replaceAll("\\[|\\]","");
        String origin = sandwich.getPlaceOfOrigin().equals("") ? getString(R.string.detail_error_message) : sandwich.getPlaceOfOrigin();

        String ingredients = Arrays.toString(sandwich.getIngredients().toArray()).replaceAll("\\[|\\]","");
        alsoKnownTextView.setText(alsoKnownAs);
        ingredientsTextView.setText(ingredients);
        originTextView.setText(origin);
        descriptionTextView.setText(sandwich.getDescription());
    }
}
