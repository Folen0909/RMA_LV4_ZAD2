package hr.ferit.rma_lv4_zad2.data.api

import hr.ferit.rma_lv4_zad2.data.model.User
import io.reactivex.Single

interface ApiService {
    fun getUsers(): Single<List<User>>
}