package br.com.ucsal.persistencia;

import br.com.ucsal.config.SingletonManager;
import br.com.ucsal.model.Produto;

public class PersistenciaFactory {
	public enum TipoPersistencia {
		MEMORIA, HSQLDB
	}
	
	private static TipoPersistencia tipoPersistenciaAtual = TipoPersistencia.HSQLDB;
	
	public static void setTipoPersistencia(TipoPersistencia tipo) {
		tipoPersistenciaAtual = tipo;
	}
	
	public static ProdutoRepository<Produto, Integer> createProdutoRepository() {
		switch (tipoPersistenciaAtual) {
			case MEMORIA:
				return SingletonManager.getInstance()
					.getSingleton(MemoriaProdutoRepository.class);
			case HSQLDB:
				return new HSQLProdutoRepository();
			default:
				throw new IllegalStateException("Tipo de persistência não suportado");
		}
	}
}
