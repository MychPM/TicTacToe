package edu.sagado.tictactoe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.sagado.tictactoe.R;
import edu.sagado.tictactoe.bdHelpers.DataBaseManager;
import edu.sagado.tictactoe.utils.Constants;


public class Settings extends Activity {
    private DataBaseManager manager;
    private EditText name;
    private ImageButton save;
    private ImageButton sendlevel;
    private RadioGroup radioGameAI;
    private String newNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        manager = new DataBaseManager(this);
        //manager.insertar("ana","328123");
        radioGameAI = (RadioGroup)findViewById(R.id.radioGameAI);
        name = (EditText)findViewById(R.id.etname);
        save = (ImageButton) findViewById(R.id.iBsave);
        sendlevel = (ImageButton) findViewById(R.id.iBsaveLevel);
    }

    public void Nuevo(View view){
        name.setVisibility((name.getVisibility()== View.VISIBLE)? View.INVISIBLE: View.VISIBLE);
        save.setVisibility((save.getVisibility()== View.VISIBLE)? View.INVISIBLE: View.VISIBLE);

    }

    public void sendLevel(View view){
        int gameAI = radioGameAI.getCheckedRadioButtonId();
        Intent intent = new Intent(this, MainActivity.class);
        switch (gameAI) {
            case R.id.weakAI:
                intent.putExtra(Constants.AI_PARAM_NAME, Constants.WEAK_AI);
                break;
            case R.id.notSoStrongAI:
                intent.putExtra(Constants.AI_PARAM_NAME, Constants.NOTSOSTRONG_AI);
                break;
            case R.id.strongAI:
                intent.putExtra(Constants.AI_PARAM_NAME, Constants.STRONG_AI);
                break;
            case R.id.godAI:
                intent.putExtra(Constants.AI_PARAM_NAME, Constants.GOD_AI);
                break;
            default:
                intent.putExtra(Constants.AI_PARAM_NAME, Constants.WEAK_AI);
                break;
        }
        startActivity(intent);
    }

    public void Save (View view){
        newNombre = name.getText().toString();
        manager.insertarP(newNombre);
        Toast.makeText(getApplicationContext(), "Jugador registrado correctamente", Toast.LENGTH_LONG).show();
        name.setText("");
        name.setVisibility((name.getVisibility()== View.INVISIBLE)? View.VISIBLE: View.INVISIBLE);
        save.setVisibility((save.getVisibility()== View.INVISIBLE)? View.VISIBLE: View.INVISIBLE);
    }

    public void settings(View view){
        radioGameAI.setVisibility((radioGameAI.getVisibility() == View.VISIBLE)?
                View.INVISIBLE : View.VISIBLE);
        sendlevel.setVisibility((sendlevel.getVisibility() == View.VISIBLE)? View.INVISIBLE : View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
