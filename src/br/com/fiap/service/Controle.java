package br.com.fiap.service ;

import java.util.List ;

import br.com.fiap.dao.JogoDAO ;
import br.com.fiap.vo.JogoVO ;
import android.content.Context ;

public class Controle
{

	private Context context ;

	public Controle( Context context )
	{
		this.context = context ;
	}

	public void salvarJogo( JogoVO jogo )
	{
		JogoDAO dao = new JogoDAO( context ) ;
		dao.insert( jogo ) ;
		dao.encerrarDB( ) ;
	}

	public JogoVO obterJogoPorNome( String nome )
	{
		JogoDAO dao = new JogoDAO( context ) ;
		JogoVO jogo = dao.selecionarPorNome( nome ) ;
		dao.encerrarDB( ) ;
		return jogo ;
	}

	public JogoVO obterJogoPorNumeros( String numeros )
	{
		JogoDAO dao = new JogoDAO( context ) ;
		JogoVO jogo = dao.selecionarPorNumeros( numeros ) ;
		dao.encerrarDB( ) ;
		return jogo ;
	}

	public List<JogoVO> obterTodosJogos( )
	{
		JogoDAO dao = new JogoDAO( context ) ;
		List<JogoVO> jogos = dao.selectAll( ) ;
		dao.encerrarDB( ) ;
		return jogos ;
	}

	public void excluirJogoPorId( int id )
	{
		JogoDAO dao = new JogoDAO( context ) ;
		dao.excluirPorId( id ) ;
		dao.encerrarDB( ) ;
		return ;
	}

}
