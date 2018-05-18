package com.cognizant.bibliotecadigital.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.StatusLivro;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.EmprestimoService;
import com.cognizant.bibliotecadigital.service.LivroService;
import com.cognizant.bibliotecadigital.service.ReservaService;
import com.cognizant.bibliotecadigital.service.UsuarioService;

@Controller
@RequestMapping
public class PesquisaController {

	@Autowired
	private LivroService livroService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EmprestimoService emprestadoService;
	@Autowired
	private ReservaService reservaService;

	@GetMapping({ "", "/consulta" })
	public ModelAndView index(@RequestParam(value = "q", required = false, defaultValue = "") String query) {
		ModelAndView mav = new ModelAndView("consulta/consulta");

		if (query.equals("")) {
			mav.addObject("livros", livroService.findAll());
		} else {
			mav.addObject("livros", livroService.search(query));
		}

		return mav;
	}

	@GetMapping("/consulta/{id}")
	public ModelAndView detail(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("consulta/detalhes");

		Livro livro = livroService.findById(id).get();

		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			usuario = usuarioService.findByEmail(email).orElse(null);
		}

		livro.getUnidadeLivros().forEach(unidade -> {
			if (reservaService.countReservaAguardandoPorUnidadeId(unidade.getId())
					&& emprestadoService.isEmprestado(livro.getId())
					&& !livro.getStatusLivro().equals(StatusLivro.EM_ANALISE)) {
				unidade.setEmprestado(false);
			} else {
				unidade.setEmprestado(true);
			}
		});

		if (livro.getStatusLivro().equals(StatusLivro.COM_EMPRESTIMO)
				&& emprestadoService.countEmprestimoPorUsuarioId(usuario.getId())
				&& reservaService.countReservasPorIdLivro(id, usuario.getId())) {
			livro.setHabilita(false);
		} else {
			livro.setHabilita(true);
		}

		mav.addObject("livro", livro);

		return mav;
	}

	@PostMapping("/listaDesejos")
	public ModelAndView wishList(@RequestParam("id") Long id) {

		Livro livro = livroService.findById(id).get();
		
		
		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			usuario = usuarioService.findByEmail(email).orElse(null);
		}
		
		
			livro.getIdsListaDesejos().add(usuario.getId());
		
		livroService.save(livro);

		return new ModelAndView("");

	}

}
