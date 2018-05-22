package com.cognizant.bibliotecadigital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cognizant.bibliotecadigital.controller.EmprestimoController;

@Component
public class AgendamentoEmail {
	
		private final String ZONE_TIME = "America/Sao_Paulo";
		
		@Autowired
		EmprestimoController empController;
		
		@Scheduled(cron = "* * 10 * * * ", zone = ZONE_TIME)
		@Scheduled(cron = "0 0 09 * * *", zone = ZONE_TIME)
		public void LembreteEmail(){
			
		
		empController.prazoDevolucaoEmail();
		
	}
}
