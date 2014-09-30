package br.com.fiap.dao ;

import java.util.ArrayList ;
import java.util.List ;

import br.com.fiap.vo.JogoVO ;
import android.content.Context ;
import android.database.Cursor ;
import android.database.sqlite.SQLiteDatabase ;
import android.database.sqlite.SQLiteOpenHelper ;
import android.database.sqlite.SQLiteStatement ;
import android.util.Log ;

public class JogoDAO
{

	private static final String DATABASE_NAME = "checkmegasenadb.db" ;
	private static final int DATABASE_VERSION = 1 ;
	private static final String TABLE_NAME = "JOGO" ;

	private Context context ;
	private SQLiteDatabase db ;

	private SQLiteStatement insertStmt ;
	private static final String INSERT = "insert into " + TABLE_NAME
			+ " (nome, numeros) values (?,?)" ;

	public JogoDAO( Context context )
	{
		this.context = context ;
		OpenHelper openHelper = new OpenHelper( this.context ) ;
		this.db = openHelper.getWritableDatabase( ) ;
		this.insertStmt = this.db.compileStatement( INSERT ) ;
	}

	public long insert( JogoVO jogo )
	{
		this.insertStmt.bindString( 1, jogo.getNome( ) ) ;
		this.insertStmt.bindString( 2, jogo.getNumeros( ) ) ;
		return this.insertStmt.executeInsert( ) ;
	}

	public JogoVO selecionarPorNome( String nome )
	{
		String[ ] campos = new String[ ]
		{ "id", "nome", "numeros" } ;
		String[ ] args = new String[ ]
		{ nome } ;
		Cursor c = db.query( TABLE_NAME, campos, "nome=?", args, null, null, null ) ;
		if (c.moveToFirst( ))
		{
			JogoVO jogo = new JogoVO( ) ;
			jogo.setId( c.getInt( 0 ) ) ;
			jogo.setNome( c.getString( 1 ) ) ;
			jogo.setNumeros( c.getString( 2 ) ) ;
			return jogo ;
		}
		else
		{
			return null ;
		}
	}

	public JogoVO selecionarPorNumeros( String numeros )
	{
		String[ ] campos = new String[ ]
		{ "id", "nome", "numeros" } ;
		String[ ] args = new String[ ]
		{ numeros } ;
		Cursor c = db.query( TABLE_NAME, campos, "numeros=?", args, null, null, null ) ;
		if (c.moveToFirst( ))
		{
			JogoVO jogo = new JogoVO( ) ;
			jogo.setId( c.getInt( 0 ) ) ;
			jogo.setNome( c.getString( 1 ) ) ;
			jogo.setNumeros( c.getString( 2 ) ) ;
			return jogo ;
		}
		else
		{
			return null ;
		}
	}

	public void excluirPorId( int id )
	{
		String[ ] args = new String[ ]
		{ String.valueOf( id ) } ;
		this.db.delete( TABLE_NAME, "id=?", args ) ;
	}

	public void deleteAll( )
	{
		this.db.delete( TABLE_NAME, null, null ) ;
	}

	public List<JogoVO> selectAll( )
	{
		List<JogoVO> list = new ArrayList<JogoVO>( ) ;
		Cursor cursor = this.db.query( TABLE_NAME, new String[ ]
		{ "id", "nome", "numeros" }, null, null, null, null, "id" ) ;

		if (cursor.moveToFirst( ))
		{
			do
			{
				JogoVO jogo = new JogoVO( ) ;
				jogo.setId( cursor.getInt( 0 ) ) ;
				jogo.setNome( cursor.getString( 1 ) ) ;
				jogo.setNumeros( cursor.getString( 2 ) ) ;
				list.add( jogo ) ;
			}
			while (cursor.moveToNext( )) ;
		}

		if (cursor != null && !cursor.isClosed( ))
		{
			cursor.close( ) ;
		}
		return list ;
	}

	public void encerrarDB( )
	{
		this.db.close( ) ;
	}

	private static class OpenHelper extends SQLiteOpenHelper
	{
		public OpenHelper( Context context )
		{
			super( context, DATABASE_NAME, null, DATABASE_VERSION ) ;
		}

		@Override
		public void onCreate( SQLiteDatabase db )
		{
			db.execSQL( "CREATE TABLE " + TABLE_NAME
					+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, numeros TEXT)" ) ;

		}

		@Override
		public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
		{
			Log.w( "Example", "***Updating database, this will drop table and recreate." ) ;
			db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME ) ;

		}
	}

}
