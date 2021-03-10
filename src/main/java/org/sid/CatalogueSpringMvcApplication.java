package org.sid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogueSpringMvcApplication implements CommandLineRunner{
	@Autowired
	private ProduitRepository produitRepository;

	public static void main(String[] args) {
		SpringApplication.run(CatalogueSpringMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		produitRepository.save(new Produit(null, "LENOVO", 8000, 14));
		produitRepository.save(new Produit(null, "HP LapTop", 6800, 6));
		produitRepository.save(new Produit(null, "DELL LapTop", 12000, 24));
		produitRepository.findAll().forEach(p->{
			System.out.println("****");
			System.out.println(p.getDesignation());
			System.out.println(p.getPrix());
			System.out.println(p.getQuantite());
		});
		
	}

}
