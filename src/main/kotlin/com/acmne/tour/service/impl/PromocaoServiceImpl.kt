package com.acmne.tour.service.impl

import com.acmne.tour.TourApplication
import com.acmne.tour.model.Promocao
import com.acmne.tour.service.PromocaoService
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class PromocaoServiceImpl: PromocaoService {
    companion object{
        val initialPromocoes = arrayOf(
                Promocao(1, "Maravilhosa viagem a Cabcun", "Cancun", true, 7, 4200.99),
                Promocao(2, "Viagem Radical com Rapel e escalada", "Nova Zelandia", false, 12, 1200.0),
                Promocao(3, "Viagem espiritual", "Tailândia", false, 17, 1500.0)
        )
    }

    var promocoes =
            ConcurrentHashMap<Long, Promocao>(initialPromocoes.associateBy(Promocao::id))

    override fun getById(id: Long): Promocao? {
        return promocoes[id]
    }

    override fun create(promocao: Promocao) {
        promocoes[promocao.id] = promocao
    }

    override fun delete(id: Long) {
        promocoes.remove(id)
    }

    override fun update(id: Long, promocao: Promocao) {
        delete(id)
        create(promocao)
    }

    override fun searchByLocal(localFilter: String): List<Promocao> =
        promocoes.filter {
            it.value.local.contains(localFilter, true)
        }.map (Map.Entry<Long, Promocao>::value).toList()

}