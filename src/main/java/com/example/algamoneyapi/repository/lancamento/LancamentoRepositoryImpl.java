package com.example.algamoneyapi.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.example.algamoneyapi.model.Lancamento;
import com.example.algamoneyapi.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;//Importa o entityManager para trabalhar com a consulta
	
	
	@Override
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

		/**Aqui seria o WHERE do SQL*/
		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate [] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);

		return query.getResultList();
	}
	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {

		List<Predicate> predicates = new ArrayList<Predicate>();

		/**!StringUtils.isEmpty(lancamentoFilter.getDescricao())  é o mesmo que fazer dentro do if o :  lancamentoFilter.getDataVencimentoDe() != null
		 * Usar o StringUtils do pacote org.springframework.util.StringUtils */
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {

			predicates.add(builder.like(
					/**Sem metaModel ficaria como a linha abaixo onde eu teria que escrever root.get("descricao") e poderia digitar errado*/
					//builder.lower(root.get("descricao")), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"
					builder.lower(root.get("descricao")), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"

					));
		}

		if(lancamentoFilter.getDataVencimentoDe() != null) {

			predicates.add(builder.greaterThanOrEqualTo(
					root.get("dataVencimento"), lancamentoFilter.getDataVencimentoDe()

					));
		}

		if(lancamentoFilter.getDataVencimentoAte() != null) {

			predicates.add(builder.lessThanOrEqualTo(
					root.get("dataVencimento"), lancamentoFilter.getDataVencimentoAte()
					));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
