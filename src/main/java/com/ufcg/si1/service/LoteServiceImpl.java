package com.ufcg.si1.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ufcg.si1.model.ItemVenda;
import com.ufcg.si1.model.Lote;
import com.ufcg.si1.model.Produto;
import com.ufcg.si1.model.VendaItem;
import com.ufcg.si1.model.DTO.LoteDTO;
import com.ufcg.si1.repositories.LotesRepository;
import com.ufcg.si1.repositories.ProdutosRepository;
import com.ufcg.si1.util.Util;

import org.aspectj.weaver.patterns.ITokenSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loteService")
public class LoteServiceImpl implements LoteService {

	@Autowired
	private LotesRepository lotesRepository;
	private ProdutosRepository produtoRepository;

	@Override
	public Lote saveLote(Lote lote) {
		Lote savedLote = lotesRepository.save(lote);

		return savedLote;
	}

	@Override
	public Lote findById(long id) {
		Lote lote = this.lotesRepository.getLoteById(id);
		return lote;
	}

	/**
	 * Faz o update do lote, lote é recebido como parâmetro
	 * já com o novo produto como atributo
	 *
	 * @param lote lote a ser atualizado
	 */
	@Override
	public void updateProduto(Lote lote) {
		this.lotesRepository.save(lote);
	}

	@Override
	public void deleteLoteById(long id) {
		Lote lote = this.findById(id);
		this.lotesRepository.delete(lote);
	}

	@Override
	public List<Lote> findAllLotes() {
		Iterable<Lote> lotes = this.lotesRepository.findAll();
		List<Lote> lotesList = Util.toList(lotes);
		
		return lotesList;
	}

	@Override
	public int size() {
		List<Lote> lotes = this.findAllLotes();
		int size = lotes.size();
		
		return size;
	}

	@Override
	public Iterator<Lote> getIterator() {
		Iterable<Lote> lotes = this.findAllLotes();
		Iterator<Lote> iterator = lotes.iterator();
		
		return iterator;
	}

	@Override
	public Lote criarLote(long produtoId, LoteDTO loteDTO) {
		Lote loteParaSalvar = null;
		Produto produto = this.produtoRepository.findOne(produtoId);
		
		if(produto != null){
			int numeroDeItens = loteDTO.getNumeroDeItens();
			String validade = loteDTO.getDataDeValidade();
			loteParaSalvar = new Lote(produtoId, produto, numeroDeItens, validade);
			this.lotesRepository.save(loteParaSalvar);
		}
		
		return loteParaSalvar;
	}

	//TODO: excluir do mais perto de vencer e vender de varios lotes, caso um nao tenha o suficiente pegar de outro
	@Override
	public void atualizaQuantProduto(List<VendaItem> produtosVendidos) {
		List<Lote> lotes =  this.findAllLotes();
		
		for (VendaItem vendaItem : produtosVendidos) {
			Produto produto = vendaItem.getProduto();
			int quantidade =  vendaItem.getQuantidade();
			
			for (Lote lote : lotes) {
				if(lote.getProduto().equals(produto)) {
					lote.setNumeroDeItens( lote.getNumeroDeItens() - quantidade);
				}
			}

		}	
	}
	
}
