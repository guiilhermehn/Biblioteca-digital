package com.cognizant.bibliotecadigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.StatusLivro;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.EmprestimoService;
import com.cognizant.bibliotecadigital.service.LivroService;
import com.cognizant.bibliotecadigital.service.PapelService;
import com.cognizant.bibliotecadigital.service.ReservaService;
import com.cognizant.bibliotecadigital.service.UsuarioService;

@Controller
@RequestMapping
public class PesquisaController {
	//Serviços chamados
	@Autowired
	private LivroService livroService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private EmprestimoService emprestadoService;
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private PapelService papelService;

    // TODO rever rotas? Usar "" para index.html?
	/* ********************************************************************************
	 * Faz o mapeamento da barra de pesquisa de livros (por título, autor ou descrição)
	 * Se a pesquisa não conter valor algum, serão trazidos todos os livros cadastrados
	 * Caso tenha valor, será feita uma query no banco de dados, buscando algum livro
	 * que contenha o valor informado
	 **********************************************************************************/

	@GetMapping("/consulta" )

	public ModelAndView index(@RequestParam(value = "q", required = false, defaultValue = "") String query) {
		ModelAndView mav = new ModelAndView("consulta/consulta");

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

	/* ********************************************************
	 * Faz o mapeamento da página de detalhes do livro
	 * A página é populada à partir do ID do livro
	 * Se o livro não estiver emprestado, o botão de empréstimo
	 * é habilitado, e o de reserva fica desabilitado
	 * Se o livro estiver emprestado, mas não tiver reserva,
	 * o botão de reseva é habilitado
	 * Se o livro estiver emprestado e reservado, nenhum botão
	 * é habilitado
	 ***********************************************************/
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
		
		List<UnidadeLivro>unidadesLivros = livro.getUnidadeLivros();
		
		for (UnidadeLivro unidadeLivro : unidadesLivros) {
			if (reservaService.countReservaAguardandoPorUnidadeId(unidadeLivro.getId())
					&& emprestadoService.isEmprestado(unidadeLivro.getId())
					&& !livro.getStatusLivro().equals(StatusLivro.EM_ANALISE)
					&& emprestadoService.countEmprestimoPorUsuarioId(usuario.getId())
					) {
				unidadeLivro.setEmprestado(false);
			} else {
				unidadeLivro.setEmprestado(true);
			}
		}

		if (!livro.getStatusLivro().equals(StatusLivro.SEM_EMPRESTIMO)
				&& emprestadoService.countEmprestimoPorUsuarioId(usuario.getId())
				&& reservaService.countReservasPorIdLivro(id, usuario.getId())
				&& reservaService.countReservaAguardandoPorUnidadeId(livro.getId())) {
			livro.setHabilita(false);
		} else {
			livro.setHabilita(true);
		}

		mav.addObject("livro", livro);
		
		boolean isAdmin = usuario.getPapeis().contains(papelService.findByNome("ROLE_ADMIN").get());
		mav.addObject("isAdmin", isAdmin);

		return mav;
	}

}
