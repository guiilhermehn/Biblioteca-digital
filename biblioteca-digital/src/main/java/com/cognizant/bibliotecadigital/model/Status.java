
package com.cognizant.bibliotecadigital.model;

public enum Status {


	EM_ESPERA, AGUARDANDO, FINALIZADO, EM_ANALISE,ATIVO;
	
	@Override
	public String toString() {
		if (this == EM_ESPERA ) {
			return "Em espera";
		}
		if (this == AGUARDANDO ) {
			return "Aguardando";
		}	
		if (this == FINALIZADO ) {
			return "Finalizado";
		}	
		if (this == EM_ANALISE ) {
			return "Em análise";
		}
		if (this == ATIVO ) {
			return "Ativo";
		}	
		return "Não encontrado";
	}

}

