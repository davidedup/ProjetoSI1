package com.ufcg.si1.service;

import java.util.Iterator;

import com.ufcg.si1.model.Lote;
import com.ufcg.si1.model.DTO.LoteDTO;

public interface LoteService {

	public Iterable<Lote> findAllLotes();

	public Lote findById(long id);

	public void updateProduto(Lote user);

	public void deleteLoteById(long id);

	public int size();

	public Iterator<Lote> getIterator();

	public Lote saveLote(Lote lote);

	public Lote criarLote(long produtoId, LoteDTO loteDTO);

}
