package edu.sagado.tictactoe.bdHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by MychPM on 12/01/2015.
 */
public class DataBaseManager {
    public static final String TABLE_NAME_P = "players";
    public static final String TABLE_NAME_S = "scores";
    public  static final String CN_ID = "_id";
    public  static final String CN_ID_P = "_id_p";
    public  static final String CN_ID_R = "_id_p_r";
    public static final  String CN_NAME = "nombre";
    public static final String CN_NIVEL = "nivel";
    public static final String CN_WINS = "ganados";
    public static final String CN_LOSTS = "perdidos";
    public static final String CN_TIES = "empatados";

    public static final String DROP_TABLEP= "drop table if exist "+TABLE_NAME_P;

    public static final String CREATE_TABLE_PLAYERS = "create table " +TABLE_NAME_P+ " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NAME + " text not null);";
    public static final String DROP_TABLE= "drop table if exist "+TABLE_NAME_S;
    public static final String CREATE_TABLE_SCORES = "create table " +TABLE_NAME_S+ " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_ID_P + " integer,"
            + CN_ID_R + " integer,"
            + CN_NIVEL + "integer,"
            + CN_WINS + " integer not null,"
            + CN_LOSTS + " integer not null,"
            + CN_TIES + " integer not null);";

    private DbHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context){

        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    private ContentValues generarContentValuesP( String nombre){
        ContentValues valores = new ContentValues();

        valores.put(CN_NAME, nombre);

        return valores;
    }

    private ContentValues generarContentValuesS(Integer player1, Integer player2, Integer nivel, Integer ganados, Integer perdidos, Integer empatados){
        ContentValues valores = new ContentValues();
        valores.put(CN_ID_P, player1);
        valores.put(CN_ID_R, player2);
        valores.put(CN_NIVEL, nivel);
        valores.put(CN_WINS, ganados);
        valores.put(CN_LOSTS, perdidos);
        valores.put(CN_TIES, empatados);


        return valores;
    }

    public long insertarP (String nombre){
        return db.insert(TABLE_NAME_P, null, generarContentValuesP(nombre));

    }

    public void insertarS (Integer player1, Integer player2, Integer nivel, Integer ganados, Integer perdidos, Integer empatados){
        db.insert(TABLE_NAME_S,null,generarContentValuesS(player1,player2,nivel,ganados,perdidos,empatados));
    }

    public boolean eliminarP(long idPlayer){
        return db.delete(TABLE_NAME_P,CN_ID + "=" + idPlayer, null)>0;
    }

    public void eliminarMultipleP (String nom1,String nom2){
        db.delete(TABLE_NAME_P,CN_NAME + "IN (?,?)",new String[]{nom1,nom2});
    }

    public void modificarScores(String player, String playerR, String levelJ, Integer player1, Integer player2, Integer nivel, Integer ganados, Integer perdidos, Integer empatados ){
        db.update(TABLE_NAME_S,generarContentValuesS(player1, player2, nivel, ganados, perdidos,empatados),CN_ID_P+"=? AND " + CN_ID_R +"=? AND " + CN_NIVEL + "=?",new String[]{player, playerR, levelJ} );
    }

    public Cursor cargarPlayers(){
        String[] columnas = new String[]{CN_ID,CN_NAME};
        return db.query(TABLE_NAME_P,columnas,null,null,null,null,null);
    }

    public Cursor cargarScores(){
        String[] columnas = new String[]{CN_ID,CN_ID_P,CN_ID_R,CN_NIVEL,CN_WINS, CN_LOSTS,CN_TIES};
        return db.query(TABLE_NAME_S,columnas,null,null,null,null,null);
    }

    public Cursor ScoresByPlayers(String player, String playerR, String levelJ){
        String[] columnas = new String[]{CN_ID,CN_ID_P,CN_ID_R,CN_NIVEL,CN_WINS,CN_LOSTS,CN_TIES};
        return db.query(TABLE_NAME_S, columnas,CN_ID_P+"=? AND " + CN_ID_R +"=? AND " + CN_NIVEL + "=?",new String[]{player, playerR, levelJ},null,null,null );
    }

    public boolean resScores(String player, String playerR, String levelJ){
        if(ScoresByPlayers(player,playerR,levelJ)==null)
            return true;
        else
            return false;
    }

    public Cursor buscarPlayer(String nombre) {

        String[] columnas = new String[]{CN_ID,CN_NAME};
        return db.query(TABLE_NAME_P,columnas,CN_NAME + " LIKE ?", new String[]{"%"+nombre+"%"},null,null,null);

    }

    public Cursor buscarPalyerById(String id){
        String[] columnas = new String[]{CN_ID,CN_NAME};
        return db.query(TABLE_NAME_P,columnas,CN_ID + "=?", new String[]{id},null,null,null);
    }
    public boolean resBuscar(String id){
        if(buscarPalyerById(id)==null)
            return false;
        else
            return true;
    }

}
