package com.acmne.tour.service.impl

import com.acmne.tour.TourApplication
import com.acmne.tour.model.Promocao
import com.acmne.tour.repository.PromocaoRepository
import com.acmne.tour.service.PromocaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class PromocaoServiceImpl(val promocaoRepository: PromocaoRepository): PromocaoService {

    override fun getById(id: Long): Promocao? {
        return promocaoRepository.findById(id).orElseGet(null)
    }

    @CacheEvict("promocoes", allEntries = true)
    override fun create(promocao: Promocao) {
        promocaoRepository.save(promocao)
    }

    @CacheEvict("promocoes", allEntries = true)
    override fun delete(id: Long) {
        promocaoRepository.deleteById(id)
    }

    @CacheEvict("promocoes", allEntries = true)
    override fun update(id: Long, promocao: Promocao) {
        create(promocao)
    }

    override fun searchByLocal(localFilter: String): List<Promocao> =
       listOf()

    @Cacheable("promocoes")
    override fun getAll(start: Int, size: Int): List<Promocao> {
        val pages: Pageable = PageRequest.of(start, size, Sort.by("local").ascending())
        return promocaoRepository.findAll(pages).toList()
    }

    override fun count(): Long =
        promocaoRepository.count()

    override fun getAllSortedByLocal(): List<Promocao> =
        this.promocaoRepository.findAll(Sort.by("local").descending()).toList()

    override fun getAllByPrecoMenorQuer9000(): List<Promocao> {
        return this.promocaoRepository.findByPrecoMenorQue(9000.0, 6)
    }


}