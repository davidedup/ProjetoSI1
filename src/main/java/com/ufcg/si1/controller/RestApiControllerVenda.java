package com.ufcg.si1.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.si1.model.Venda;
import com.ufcg.si1.model.VendaItem;
import com.ufcg.si1.service.VendaService;
import com.ufcg.si1.service.VendaServiceImpl;
import com.ufcg.si1.util.CustomErrorType;


@RestController
@RequestMapping("/venda")
@CrossOrigin
public class RestApiControllerVenda {
	
	private VendaService vendaService =  new VendaServiceImpl();
	
	@RequestMapping(value = "/compra", method = RequestMethod.POST)
	public ResponseEntity<?> cadastraVenda(@RequestBody List<VendaItem> produtosVendidos, String dataDaCompra){
		this.vendaService.cadastraVenda(produtosVendidos, dataDaCompra);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/vendas", method = RequestMethod.GET)
	public ResponseEntity<List<Venda>> listarVendas(){
		List<Venda> vendas = this.vendaService.findAllLotes();		
		return new ResponseEntity<List<Venda>>(vendas, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/caixa", method = RequestMethod.GET)
	public ResponseEntity<BigDecimal> calculaTotalDeVendas(){
		BigDecimal caixa = this.vendaService.calculaTotalDeVendas();
		return new ResponseEntity<BigDecimal>(caixa, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/cancela", method = RequestMethod.POST)
	public ResponseEntity<?> cancelaVenda(@RequestBody long id){
		Venda venda = this.vendaService.cancelaVenda(id);
		
		if(venda == null) {
			return new ResponseEntity<>(new CustomErrorType("Venda com id: " + id + ". Não foi efetuada e não pode ser cancelada"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>("Venda com id: " + id + "apagada", HttpStatus.CREATED);
	}
	
}