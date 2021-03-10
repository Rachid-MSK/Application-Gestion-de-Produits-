package org.sid.web;

import javax.validation.Valid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProduitController {
	@Autowired
	private ProduitRepository produitRepository;
	
	@GetMapping("/user/index")
	public String index(Model model,@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="motCle", defaultValue = "") String mc) {
		Page<Produit> pageProduits= produitRepository.findByDesignationContains(mc, PageRequest.of(page, 5));
		model.addAttribute("listeProduits", pageProduits.getContent());
		model.addAttribute("pages", new int[pageProduits.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "produits";
 		 
	}
	@GetMapping("/admin/delete")
	public String delete(Long id, int page, String motCle) {
		produitRepository.deleteById(id);
		return "redirect:/user/index?page="+page+"&motCle="+motCle;
	}
	@GetMapping("/admin/formProduit")
	public String form(Model model) {
		model.addAttribute("produit", new Produit());
		return "FormProduit";
	}
	@PostMapping("/admin/save")
	public String save(@Valid Produit produit, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) return "formProduit";
		produitRepository.save(produit);
		return "redirect:/user/index";
	}
	@GetMapping("/admin/edit")
	public String edit(Model model, Long id) {
		Produit produit= produitRepository.findById(id).get();
		model.addAttribute("produit", produit);
		return "EditProduit";
	}
	@GetMapping("/")
	public String def() {
 		return "redirect:/user/index";
	}
	@GetMapping("/403")
	public String notAutorized() {
 		return "403";
	}
	@GetMapping("/login")
	public String login() {
 		return "login";
	}

}
