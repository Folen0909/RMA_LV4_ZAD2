package hr.ferit.rma_lv4_zad2.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.rma_lv4_zad2.R
import hr.ferit.rma_lv4_zad2.data.api.ApiHelper
import hr.ferit.rma_lv4_zad2.data.api.ApiServiceImpl
import hr.ferit.rma_lv4_zad2.data.model.User
import hr.ferit.rma_lv4_zad2.databinding.ActivityMainBinding
import hr.ferit.rma_lv4_zad2.ui.base.ViewModelFactory
import hr.ferit.rma_lv4_zad2.ui.main.adapter.MainAdapter
import hr.ferit.rma_lv4_zad2.ui.main.viewmodel.MainViewModel
import hr.ferit.rma_lv4_zad2.utils.Status

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
        setContentView(mainBinding.root)
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        mainBinding.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    mainBinding.recyclerView.context,
                        (mainBinding.recyclerView.layoutManager as LinearLayoutManager).orientation
                )
        )
        mainBinding.recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    mainBinding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    mainBinding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    mainBinding.progressBar.visibility = View.VISIBLE
                    mainBinding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    mainBinding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}