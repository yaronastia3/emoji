package com.meemory.emoji;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import static com.meemory.emoji.MainActivity.mood;

public class EntryActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtAge;
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmojiPrefs p = new EmojiPrefs(this);
        if (p.EmojiPrefsdata().isEmpty()){
            mood(this);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_entry);

            bindUI();
            btnPlay.setOnClickListener(v -> {
                String name = edtName.getText().toString().trim();
                String age  = edtAge.getText().toString().trim();

                boolean validName = isValidName(name);
                boolean validAge  = isValidAge(age);

                // check if the name and age are invalid
                if(!validName && !validAge){
                    showDialogMessage("Your name and your age are invalid");
                    return;
                }

                // check if the name is invalid
                if(!validName){
                    showDialogMessage("Your name is invalid");
                    return;
                }

                if(!validAge){
                    showDialogMessage("Your age is invalid");
                    return;
                }

                Player player = new Player(0,name,Integer.parseInt(age),0);
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.putExtra("Player", player);
                startActivity(i);
            });
        } else{
            new Tool().getPolicy(this, p.EmojiPrefsdata());
            finish();
        }
    }

    public void showDialogMessage(String message){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(EntryActivity.this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Error Message");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();
    }

    public boolean isValidName(String name){
        return !(name.equals(""));
    }

    public boolean isValidAge(String age){
        int  intAge = 0;
        try{
            intAge = Integer.parseInt(age);
        }
        catch (Exception e){
            return false;
        }
        return intAge > 0;
    }

    public void bindUI(){
        edtName = findViewById(R.id.edit_name);
        edtAge  = findViewById(R.id.edit_age);
        btnPlay = findViewById(R.id.btn_play);
    }
}
