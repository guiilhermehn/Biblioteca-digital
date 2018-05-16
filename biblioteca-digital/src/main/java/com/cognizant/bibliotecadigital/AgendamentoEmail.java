package com.cognizant.bibliotecadigital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cognizant.bibliotecadigital.controller.EmprestimoController;

@Component
public class AgendamentoEmail {
	
		private final long SEGUNDO = 1000;
		private final long MINUTO = SEGUNDO * 60; 
		private final long HORA  = MINUTO * 60;
		private final String ZONE_TIME = "America/Sao_Paulo";
		
		@Autowired
		EmprestimoController empController;
		
		@Scheduled(cron = "* * 10 * * * ", zone = ZONE_TIME)
		public void LembreteEmail(){
			
		
		empController.prazoDevolucaoEmail();
		
	}
}
