package com.acmne.tour.controller

import com.acmne.tour.exception.PromocaoNotFoundException
import com.acmne.tour.model.ErrorMessage
import com.acmne.tour.model.Promocao
import com.acmne.tour.service.PromocaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping(value = ["/promocoes"])
class PromocaoController {

    @Autowired
    lateinit var promocaoService: PromocaoService

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Any> {
        var promocao = promocaoService.getById(id)
        return if(promocao != null)
            return ResponseEntity(promocao,  HttpStatus.OK)
        else
            return ResponseEntity(ErrorMessage("Promoção não localizada", "promocao ${id} nao localizada"), HttpStatus.NOT_FOUND)
    };

    @PostMapping()
    fun create(@RequestBody promocao: Promocao): ResponseEntity<Map<String, String>> {
        promocaoService.create(promocao)
        val map = mapOf("message" to "OK")
        return ResponseEntity(map, HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        var status = HttpStatus.NOT_FOUND
        if(promocaoService.getById(id) != null) {
            status = HttpStatus.ACCEPTED
            promocaoService.delete(id)
        }
        return ResponseEntity(Unit, status)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody promocao: Promocao): ResponseEntity<Unit>  {
        var status = HttpStatus.NOT_FOUND
        if(promocaoService.getById(id) != null) {
            promocaoService.update(id, promocao)
            status = HttpStatus.ACCEPTED
        }
        return ResponseEntity(Unit, status)
    }

    @GetMapping()
    fun GetAll(@RequestParam(required = false, defaultValue = "1") start: Int,
               @RequestParam(required = false, defaultValue = "3") size: Int
    )
        : ResponseEntity<List<Promocao>> {
        val list = this.promocaoService.getAll(start, size)
        val status = if (list.isEmpty()) HttpStatus.NOT_FOUND else HttpStatus.OK
        return  ResponseEntity(list, status)
    }

    @GetMapping("/count")
    fun count(): ResponseEntity<Map<String, Long>> =
        ResponseEntity.ok().body(mapOf("count" to this.promocaoService.count()))


    @GetMapping("/ordenados")
    fun ordenados() = this.promocaoService.getAllSortedByLocal()

    @GetMapping("/menorQue9000")
    fun getAllMenores(): List<Promocao> =
        this.promocaoService.getAllByPrecoMenorQuer9000()



}