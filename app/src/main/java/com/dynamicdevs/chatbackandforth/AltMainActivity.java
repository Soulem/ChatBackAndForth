package com.dynamicdevs.chatbackandforth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dynamicdevs.chatbackandforth.databinding.AltActivityMainBinding;
import static com.dynamicdevs.chatbackandforth.utl.Constants.OUTPUT_KEY;

public class AltMainActivity extends AppCompatActivity {
    private AltActivityMainBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AltActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        ImageView iv = findViewById(R.id.image_Frame);
        Glide.with(AltMainActivity.this)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(R.drawable.blue_image_drawable)
                .into(iv);

        Intent returnedIntent = getIntent();
        if (null == returnedIntent.getStringExtra(OUTPUT_KEY)) {
            readFromSharedPref();
        }
        else{
            binding.outputTextView.setText(returnedIntent.getStringExtra(OUTPUT_KEY));
        }

        binding.switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putString(OUTPUT_KEY, binding.outputTextView.getText().toString()).apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(OUTPUT_KEY, binding.outputTextView.getText().toString());
                startActivity(intent);
            }});

        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putString(OUTPUT_KEY, binding.outputTextView.getText().toString()).apply();
                String currOutput = binding.outputTextView.getText().toString();
                String str = "Red User Sent: " + binding.inputEditTextView.getText().toString().trim();
                currOutput += '\n' + str;
                binding.outputTextView.setText(currOutput);
                binding.inputEditTextView.setText("");
            }});
    }

    @Override
    protected void onStop(){
        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        super.onStop();
    }

    private void readFromSharedPref(){
        binding.outputTextView.setText(sharedPreferences.getString(OUTPUT_KEY, ""));
    }
}
