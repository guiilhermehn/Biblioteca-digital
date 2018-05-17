package com.cognizant.bibliotecadigital.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.cognizant.bibliotecadigital.service.LivroService;
import com.cognizant.bibliotecadigital.service.StorageService;
import com.cognizant.bibliotecadigital.service.UnidadeLivroService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LivroController {

	private static final Logger logger = LoggerFactory.getLogger(LivroController.class);
	@Autowired
	private LivroService livroService;
	@Autowired
	private UnidadeLivroService unidadeLivroService;

	/*
	 * @GetMapping("/livros") public ModelAndView findAll() { ModelAndView mv =
	 * new ModelAndView("/livro/livroPesquisa"); mv.addObject("livros",
	 * livroService.findAll());
	 * 
	 * return mv; }
	 */

	@GetMapping("/livros")
	public ModelAndView findAll(@RequestParam(value = "q", required = false, defaultValue = "") String query) {
		ModelAndView mav = new ModelAndView("/livro/livroPesquisa");

		if (query.equals("")) {
			mav.addObject("livros", livroService.findAll());
		} else {
			mav.addObject("livros", livroService.search(query));
		}

		return mav;
	}
    
    @GetMapping({"/livros/file/{id}", "/livros/{id}/file"}) 
	@ResponseBody
	public ResponseEntity<Resource> show_file(@PathVariable("id") Long id) {
		Livro livro = livroService.findById(id).get();
		
		if (livro.getFoto() != null || !livro.getFoto().isEmpty()) {
			Resource resource = StorageService.getInstance().load(livro.getFoto());
			if (resource.exists() || resource.isReadable()) {
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + livro.getFoto() + "\"")
						.body(resource);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping({"/livros/edit/{id}", "/livros/{id}/edit"})
	public ModelAndView edit(@PathVariable("id") long id) {
		ModelAndView mv = new ModelAndView("/livro/livroEditar");
		mv.addObject("livro", livroService.findById(id).get());

		return mv;
	}

	@GetMapping("/livros/new")
	public ModelAndView create() {
		ModelAndView mv = new ModelAndView("/livro/livroCadastro");

		mv.addObject("livro", new Livro());
		return mv;
	}

	@PostMapping("/livros/create")
	public ModelAndView save(@Valid @ModelAttribute("livro") Livro livro, BindingResult bindingRes,
			RedirectAttributes redAttributes) {

		if (bindingRes.hasErrors()) {
			logger.info("Validation errors while submitting form!");
			ModelAndView mv = new ModelAndView("/livro/livroCadastro");
			return mv;
		}
        
        if (livro.getFile() != null && livro.getFile().getSize() != 0) {
			String filename = StorageService.getInstance().store(livro.getFile());
			if (filename.isEmpty()) {
				redAttributes.addFlashAttribute("mensagem", "Houve um erro no processo de upload de '" + livro.getFile().getOriginalFilename() + "'.");
				return new ModelAndView("redirect:/livros/new");
			}
			livro.setFoto(filename);
 		}
        
		try {
			Livro salvo = livroService.save(livro);
			unidadeLivroService.save(new UnidadeLivro(0L, null, livroService.findById(salvo.getId()).get()));
			livro.setStatusLivro(StatusLivro.SEM_EMPRESTIMO);

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

	@PostMapping("/livros/update")
	public ModelAndView update(@ModelAttribute Livro livro, @RequestParam(value = "apagaFoto", required = false, defaultValue = "false")  boolean apagaFoto,
			BindingResult bindingRes, RedirectAttributes redAttributes) {

		// livroService.findByIsbn13(livro.getIsbn13());
        if (bindingRes.hasErrors()) {
			logger.info("Validation errors while submitting form!");
			ModelAndView mv = new ModelAndView("/livro/livroCadastro");
			return mv;
		}
		logger.info("Apagar Foto? " + apagaFoto);
		if(apagaFoto && livro.getFoto() != null && !livro.getFoto().isEmpty()) {
			StorageService.getInstance().delete(livro.getFoto());
			livro.setFoto(null);
		}
		
		if (livro.getFile() != null && livro.getFile().getSize() != 0) {
			String filename = StorageService.getInstance().store(livro.getFile());
			if (filename.isEmpty()) {
				redAttributes.addFlashAttribute("mensagem", "Houve um erro no processo de upload de '" + livro.getFile().getOriginalFilename() + "'.");
				return new ModelAndView("redirect:/livros/new");
			}
			if (livro.getFoto() != null && !livro.getFoto().isEmpty()) {
				StorageService.getInstance().delete(livro.getFoto());
			}
			livro.setFoto(filename);
		}
        
		livroService.save(livro);

		ModelAndView mv = new ModelAndView("redirect:/livros");

		return mv;

	}

	@PostMapping("/livros/deletarLivro")
	public ModelAndView deletar(@RequestParam("id") long id) {
        Livro livro = livroService.findById(id).orElse(null);
        if (livro != null && livro.getFoto() != null && !livro.getFoto().isEmpty()) {
            StorageService.getInstance().delete(livro.getFoto());
        }
        
		livroService.deleteById(id);
		ModelAndView redirect = new ModelAndView("redirect:/livros");
		return redirect;
	}

	@PostMapping("/livro/unidade/edit")
	public ModelAndView mudarAvarias(@RequestParam("id") long id, @RequestParam("livroId") long livroId,
			@RequestParam("avarias") String avarias) {
		UnidadeLivro unidade = new UnidadeLivro(id, avarias, livroService.findById(livroId).get());
		unidadeLivroService.save(unidade);

		return new ModelAndView("redirect:/livros/edit/" + unidade.getLivro().getId());
	}

	@PostMapping("/livros/unidade/deletar")
	public ModelAndView deletarUnidade(@RequestParam("unidadeId") long unidadeId,
			@RequestParam("livroId") long livroId) {
		livroService.deleteById(unidadeId);
		return new ModelAndView("redirect:/livros/edit/" + livroId);
	}

	@PostMapping("/livros/unidade/create")
	public ModelAndView adicionarUnidade(@ModelAttribute UnidadeLivro unidade) {
		unidadeLivroService.save(unidade);
		return new ModelAndView("redirect:/livros/edit/" + unidade.getLivro().getId());
	}
}
