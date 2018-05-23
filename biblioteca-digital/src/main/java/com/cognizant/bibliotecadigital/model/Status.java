package com.cognizant.bibliotecadigital.model;

public enum Status {


	EM_ESPERA, AGUARDANDO, FINALIZADO, EM_ANALISE,ATIVO;
	
	@Override
	public String toString() {
		if (this == EM_ESPERA ) {
			return "Em Espera";
		}
		if (this == AGUARDANDO ) {
			return "Aguardando";
		}	
		if (this == FINALIZADO ) {
			return "Finalizado";
		}	
		if (this == EM_ANALISE ) {
			return "Em Análise";
		}
		if (this == ATIVO ) {
			return "Ativo";
		}	
		return "Não encontrado";
	}

}
