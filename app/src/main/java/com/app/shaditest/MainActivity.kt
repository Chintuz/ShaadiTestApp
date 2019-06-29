package com.app.shaditest

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.shaditest.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


public class MainActivity : AppCompatActivity() {

    private var disposable = CompositeDisposable()

    private lateinit var adapter: MainAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        adapter = MainAdapter()
        binding.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        getData()

        viewModel.errorData.observe(this, Observer {
            hideProgressDialog()
            showRetry(it.message)
        })

        viewModel.responseData.observe(this, Observer {
            hideProgressDialog()
            adapter.updateList(it.results)
        })

        adapter.isListEmpty.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (adapter.isListEmpty.get()) {
                    getData()
                }
                adapter.isListEmpty.set(false)
            }
        })


    }

    private fun getData() {
        if (NetworkUtils().isNetworkConnected(this)) {
            showProgressDialog()
            disposable.add(viewModel.getData())
        } else {
            showRetry(Const.NETWORK_ERROR_MESSAGE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null) {
            disposable.dispose()
        }
    }

    val progressDialog by lazy {
        ProgressDialog(this)
    }

    private fun showProgressDialog() {
        progressDialog.setMessage("Fetching data..")
        progressDialog.show()
    }

    private fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    private fun showRetry(message: String) {
        Snackbar.make(list, message, Snackbar.LENGTH_LONG)
            .setAction("Retry") { getData() }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .show()
    }
}
