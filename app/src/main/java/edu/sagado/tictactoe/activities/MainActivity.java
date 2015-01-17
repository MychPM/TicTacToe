package edu.sagado.tictactoe.activities;

import edu.sagado.tictactoe.R;
import edu.sagado.tictactoe.bdHelpers.DataBaseManager;
import edu.sagado.tictactoe.utils.Constants;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static edu.sagado.tictactoe.utils.Constants.AI_PARAM_NAME;
import static edu.sagado.tictactoe.utils.Constants.GOD_AI;
import static edu.sagado.tictactoe.utils.Constants.NOTSOSTRONG_AI;
import static edu.sagado.tictactoe.utils.Constants.STRONG_AI;
import static edu.sagado.tictactoe.utils.Constants.WEAK_AI;

/**
 * First view to be opened.
 * Contains the button for starting a new game
 * and the settings menu. For now the only
 * setting available is the one related to
 * the GameAI level.
 * @author 5agado
 *
 */
public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private DataBaseManager manager;
    private Cursor cursor;
    SimpleCursorAdapter adapter;
	private RadioGroup radioGameAI;
    private EditText name;
    private ImageButton startNewGame;
    private ImageButton search;
    private ListView showPlayers;
    String[] from;
    int[] to;
//activityP.putExtra("CN_ID",l);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        manager = new DataBaseManager(this);
        startNewGame = (ImageButton) findViewById(R.id.iBplay);
        startNewGame.setOnClickListener(this);
        showPlayers = (ListView)findViewById(R.id.lVshowP);
        //radioGameAI = (RadioGroup)findViewById(R.id.radioGameAI);
        name = (EditText)findViewById(R.id.etNameP);
        search = (ImageButton) findViewById(R.id.iBsearch);
        search.setOnClickListener(this);
        from = new String[]{manager.CN_ID, manager.CN_NAME};
        to = new int[] {android.R.id.text1,android.R.id.text2};
        final  Intent activityP = new Intent(getApplicationContext(), PlayActivity.class);
        int aiLevel = getIntent().getIntExtra(AI_PARAM_NAME, WEAK_AI);
        int aiLevelR;
        switch (aiLevel){
            case  WEAK_AI:
                //aiLevelR=0;
                activityP.putExtra(Constants.AI_PARAM_NAME, Constants.WEAK_AI);
                break;
            case NOTSOSTRONG_AI:
                //aiLevelR=1;
                activityP.putExtra(Constants.AI_PARAM_NAME, Constants.NOTSOSTRONG_AI);
                break;
            case STRONG_AI:
                activityP.putExtra(Constants.AI_PARAM_NAME, Constants.STRONG_AI);
                break;
            case GOD_AI:
                activityP.putExtra(Constants.AI_PARAM_NAME, Constants.GOD_AI);
                break;
            default:
                activityP.putExtra(Constants.AI_PARAM_NAME, Constants.WEAK_AI);
                break;
        }


        showPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long oso=showPlayers.getItemIdAtPosition(i);
                //Toast.makeText(getApplicationContext(),""+ oso, Toast.LENGTH_LONG).show();

                activityP.putExtra("CN_ID",oso);

                startActivity(activityP);
            }
        });
    }
	
	/**
	 * Action executed when the Play button is clicked.
	 * Here we create a new intent with an extra that 
	 * defines the GameAI level. 
	 * Default case is equal to a weak AI
	 * @param view
	 */

	
	/**
	 * Action executed when the Settings button is clicked
	 * @param view
	 */
	/*public void settings(View view){
		radioGameAI.setVisibility((radioGameAI.getVisibility() == View.VISIBLE)? 
				View.INVISIBLE : View.VISIBLE);
	}*/

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iBplay){
            name.setVisibility((name.getVisibility()== View.VISIBLE)? View.INVISIBLE : View.VISIBLE);
            search.setVisibility((search.getVisibility()== View.VISIBLE)? View.INVISIBLE : View.VISIBLE);
            showPlayers.setVisibility((showPlayers.getVisibility()== View.VISIBLE)? View.INVISIBLE : View.VISIBLE);
            cursor = manager.cargarPlayers();
            adapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item,cursor,from,to,0);
            showPlayers.setAdapter(adapter);
        }
        else if(view.getId()== R.id.iBsearch){
            Cursor c = manager.buscarPlayer(name.getText().toString());
            adapter.changeCursor(c);


        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent = new Intent();
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        switch (id){
            case R.id.play:
                intent.setClass(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.info:
                //cambia a pantalla acerca de
                intent.setClass(MainActivity.this, About.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.empty:
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("CERRAR",true);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_settings:
                intent.setClass(MainActivity.this, Settings.class );
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
