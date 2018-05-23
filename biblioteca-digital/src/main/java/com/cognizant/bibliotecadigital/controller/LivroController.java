
package com.cognizant.bibliotecadigital.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.StatusLivro;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.LivroService;
import com.cognizant.bibliotecadigital.service.PapelService;
import com.cognizant.bibliotecadigital.service.UnidadeLivroService;
import com.cognizant.bibliotecadigital.service.UsuarioService;

@Controller
public class LivroController {

	private static final Logger logger = LoggerFactory.getLogger(LivroController.class);
	//Serviços chamados
	@Autowired
	private LivroService livroService;
	@Autowired
	private PapelService papelService;
	@Autowired
	private UnidadeLivroService unidadeLivroService;
	@Autowired
	private UsuarioService usuarioService;

  /* ********************************************************************************
	 * (ADMIN-ONLY PAGE)
	 * Faz o mapeamento da barra de pesquisa de livros (por título, autor ou descrição)
	 * Se a pesquisa não conter valor algum, serão trazidos todos os livros cadastrados
	 * Caso tenha valor, será feita uma query no banco de dados, buscando algum livro
	 * que contenha o valor informado
	 **********************************************************************************/
	@GetMapping("/livros")
	public ModelAndView findAll(@RequestParam(value = "q", required = false, defaultValue = "") String query) {
		ModelAndView mav = new ModelAndView("/livro/livroPesquisa");

		if (query.equals("")) {
			mav.addObject("livros", livroService.findAll());
		} else {
			mav.addObject("livros", livroService.search(query));
		}
		
		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			usuario = usuarioService.findByEmail(email).orElse(null);
		}
		
		boolean isAdmin = usuario.getPapeis().contains(papelService.findByNome("ROLE_ADMIN").get());
		mav.addObject("isAdmin", isAdmin);

		return mav;
	}
  
	/* **************************************************
	 * Faz o mapeamento da página de edição do livro
	 * A página é populada à partir do ID do livro
	 ****************************************************/
	@GetMapping("/livros/edit/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {
		ModelAndView mv = new ModelAndView("/livro/livroEditar");
		mv.addObject("livro", livroService.findById(id).get());
		
		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			usuario = usuarioService.findByEmail(email).orElse(null);
		}
		
		boolean isAdmin = usuario.getPapeis().contains(papelService.findByNome("ROLE_ADMIN").get());
		mv.addObject("isAdmin", isAdmin);

		return mv;
	}

  /* **************************************************
	 * Faz o mapeamento da página de cadastro de livros
	 ****************************************************/
	@GetMapping("/livros/new")
	public ModelAndView create() {
		ModelAndView mv = new ModelAndView("/livro/livroCadastro");

		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			usuario = usuarioService.findByEmail(email).orElse(null);
		}
		
		boolean isAdmin = usuario.getPapeis().contains(papelService.findByNome("ROLE_ADMIN").get());
		mv.addObject("isAdmin", isAdmin);
		
		mv.addObject("livro", new Livro());
		return mv;
	}

  /* ***************************************************************
	 * Faz o cadastro do livro no banco de dados
	 * São feitas validações antes dos dados serem inseridos no BD
	 * caso haja erro no formulário, o usuário terá que corrigí-las
	 * (a maior parte das validações são feitas na View e no Model)
	 *****************************************************************/
	@PostMapping("/livros/create")
	public ModelAndView save(@Valid @ModelAttribute("livro") Livro livro, BindingResult bindingRes,
			RedirectAttributes redAttributes) {

		if (bindingRes.hasErrors()) {
			logger.info("Validation errors while submitting form!");
			ModelAndView mv = new ModelAndView("/livro/livroCadastro");
			return mv;
		}
        
		try {
			livro.setStatusLivro(StatusLivro.SEM_EMPRESTIMO);
			Livro salvo = livroService.save(livro);
			unidadeLivroService.save(new UnidadeLivro(0L, null, livroService.findById(salvo.getId()).get()));

			redAttributes.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
			logger.info("Success submitting form!");

			ModelAndView mv = new ModelAndView("redirect:/livros");
			return mv;
		} catch (Exception e) {
			System.out.println("Error= " + e);
			ModelAndView mv = new ModelAndView("/livro/livroCadastro");
			mv.addObject("ErrorKey", "ISBN já cadastrado!");
			mv.addObject("key_warning_cond", "true");
			return mv;
		}
	}

  /* *****************************************
	 * Faz o mapeamento para a edição do livro
	 *******************************************/
	@PostMapping("/livros/update")
	public ModelAndView update(@ModelAttribute Livro livro) {
  livro.setStatusLivro(StatusLivro.SEM_EMPRESTIMO);
		livroService.save(livro);

		ModelAndView mv = new ModelAndView("redirect:/livros");

		return mv;
	}

  /* ******************************************
	 * Faz o mapeamento para a exclusão do livro
	 ********************************************/
	@PostMapping("/livros/deletarLivro")
	public ModelAndView deletar(@RequestParam("id") long id) {
		livroService.deleteById(id);
		ModelAndView redirect = new ModelAndView("redirect:/livros");
		return redirect;
	}

  /* *****************************************************************
	 * Faz o mapeamento para a inserção de relatório de avarias no livro
	 *******************************************************************/
	@PostMapping("/livro/unidade/edit")
	public ModelAndView mudarAvarias(@RequestParam("id") long id, @RequestParam("livroId") long livroId,
			@RequestParam("avarias") String avarias) {
		UnidadeLivro unidade = new UnidadeLivro(id, avarias, livroService.findById(livroId).get());
		unidadeLivroService.save(unidade);

		return new ModelAndView("redirect:/livros/edit/" + unidade.getLivro().getId());
	}

  /* *******************************************************
	 * Faz o mapeamento para a exclusão de unidades do livro
	 *********************************************************/
	@PostMapping("/livros/unidade/deletar")
	public ModelAndView deletarUnidade(@RequestParam("unidadeId") long unidadeId,
			@RequestParam("livroId") long livroId) {
		livroService.deleteById(unidadeId);
		return new ModelAndView("redirect:/livros/edit/" + livroId);
	}

  /* *****************************************************
	 * Faz o mapeamento para a adição de unidades do livro
	 *******************************************************/
	@PostMapping("/livros/unidade/create")
	public ModelAndView adicionarUnidade(@ModelAttribute UnidadeLivro unidade) {
		unidadeLivroService.save(unidade);
		return new ModelAndView("redirect:/livros/edit/" + unidade.getLivro().getId());
	}
}


