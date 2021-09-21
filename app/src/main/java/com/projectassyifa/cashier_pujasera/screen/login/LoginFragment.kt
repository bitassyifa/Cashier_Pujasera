package com.projectassyifa.cashier_pujasera.screen.login

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.container.MyApplication
import com.projectassyifa.cashier_pujasera.data.login.model.UserLoginModel
import com.projectassyifa.cashier_pujasera.data.login.viewmodel.UserLoginVM
import com.projectassyifa.cashier_pujasera.data.merchant.adapter.AdapterMerchant
import com.projectassyifa.cashier_pujasera.data.merchant.vm.MerchantVM
import com.projectassyifa.cashier_pujasera.screen.alert.LoginSuccess
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


class LoginFragment : Fragment(), View.OnClickListener {

    var dataLogin : SharedPreferences? = null
    lateinit var navController : NavController
    var pujasera : String? = null
    var merchant : String? = null
    var server_pujasera : String? = null
    @Inject
    lateinit var userLoginVM: UserLoginVM


    @Inject
    lateinit var merchantVM: MerchantVM
    lateinit var adapterMerchant: AdapterMerchant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        dataLogin = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pjs = resources.getStringArray(R.array.pujasera)

        // access the spinner
        val spinner =  view.findViewById<Spinner>(R.id.pilih_pjs)
//
//        if (spinner != null) {
//            val adapter = this.context?.let {
//                ArrayAdapter(
//                    it,
//                    android.R.layout.simple_spinner_item, pjs)
//            }
//            spinner.adapter = adapter
//
//            spinner.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>,
//                                            view: View, position: Int, id: Long) {
//                    pujasera = pjs[position]
//
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                }
//            }
//        }

        //list merchant
        merchantVM.data_merchant?.observe(viewLifecycleOwner, Observer {
            adapterMerchant = AdapterMerchant(it,this.requireContext())
            spinner.adapter = adapterMerchant
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    pujasera = it[position].db
                    merchant = it[position].nama_merchant
                    println("MERCHANT LOGIN $pujasera")
                    println("MERCHANT LOGIN $merchant")

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        })
        merchantVM.merchant(requireContext())

        val s_pjs = resources.getStringArray(R.array.server_pujasera)

        // access the spinner
        val spinner_server =  view.findViewById<Spinner>(R.id.pilih_server)

        if (spinner_server != null) {
            val adapter = this.context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item, s_pjs)
            }
            spinner_server.adapter = adapter

            spinner_server.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    server_pujasera= s_pjs[position]
                    println("SERVER $server_pujasera")

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }


        //login validation
        if (dataLogin?.contains(getString(R.string.username))!! && dataLogin?.contains(getString(R.string.login_method_key))!!)
        {
            //jika ada sesion langsung ke home
            view.findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
        }
        navController = Navigation.findNavController(view)
        btn_login.setOnClickListener(this)
        userLoginVM.userLogin.observe(
            viewLifecycleOwner,androidx.lifecycle.Observer {
                if (it.status != true) {
                    Toast.makeText(
                        this.context,
                        "Username or password salah",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    Toast.makeText(this.context, "Login Success", Toast.LENGTH_SHORT).show()
//                    val loading = LoginSuccess(context as Activity)
//                    loading.startLoading()
//                    val handler = Handler()
//                    handler.postDelayed(object : Runnable {
//                        override fun run() {
//                            loading.isDismiss()
//                        }
//
//                    }, 3000)
                    userLoginVM.userLoginResponse.observe(viewLifecycleOwner, Observer {
                        if (it != null){
                            with(dataLogin?.edit()) {
                                this?.putString(getString(R.string.username), it.email)
                                this?.putString(getString(R.string.id_pegawai), it.id_pegawai)
                                this?.putString(getString(R.string.nama_pegawai), it.nama_pegawai)
//                                this?.putString(getString(R.string.nip), it.nip)
                                this?.putString(getString(R.string.foto), it.foto)
//                                this?.putString(getString(R.string.no_tlp), it.no_telp)
//                                this?.putString(getString(R.string.unit), it.unit)
//                                this?.putString(getString(R.string.bidang), it.bidang)
//                                this?.putString(getString(R.string.divisi), it.divisi)
//                                this?.putString(getString(R.string.alamat_tinggal), it.alamat_tinggal)
//                                this?.putString(getString(R.string.tgl_lahir), it.tgl_lahir)
//                                this?.putString(getString(R.string.agent), it.agent)
//                                this?.putString(getString(R.string.tanggal_mulai_tugas), it.tanggal_mulai_tugas)
//                                this?.putString(getString(R.string.status_aktif), it.status_aktif)
//                                this?.putString(getString(R.string.status_pegawai), it.status_pegawai)
//                                this?.putString(getString(R.string.fungsional_01), it.fungsional_01)
//                                this?.putString(getString(R.string.struktural), it.struktural)
//                                this?.putString(getString(R.string.atasan_langsung), it.atasan_langsung)
                                this?.putString(getString(R.string.email), it.email)
//                                this?.putString(getString(R.string.pjs), pujasera)
                                this?.putString(getString(R.string.merchant), it.db)
                                this?.putString(getString(R.string.server_pjs), it.server)
                                this?.putString(
                                    getString(R.string.login_method_key),
                                    "appLogin"
                                )
                                this?.commit()
                            }
                            navController.navigate(R.id.action_loginFragment_to_homeActivity)
                        }
                    })
                }
            }
        )
    }

    override fun onClick(v: View?) {
        when(v) {
            btn_login -> {
                val userLoginModel = UserLoginModel(
                    username = email.text.toString(),
                    password=  password.text.toString(),
                    server = server_pujasera.toString(),
                    db = pujasera.toString()

                )
                if (email.toString() == ""  && email.text.toString() == ""
                ){
                    Toast.makeText(this.context, "Isi semua form", Toast.LENGTH_SHORT).show()
                } else {
                    println("INI DATA ${userLoginModel.username},${userLoginModel.password}")
                    userLoginVM.loginUser(userLoginModel, requireContext())
                }
            }
        }
    }

}
