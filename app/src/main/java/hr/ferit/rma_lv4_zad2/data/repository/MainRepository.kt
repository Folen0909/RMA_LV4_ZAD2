package hr.ferit.rma_lv4_zad2.data.repository

import hr.ferit.rma_lv4_zad2.data.api.ApiHelper
import hr.ferit.rma_lv4_zad2.data.model.User
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUsers(): Single<List<User>> {
        return apiHelper.getUsers()
    }
}