package com.example.hangeunmarket.ui.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hangeunmarket.databinding.FragmentMypageBinding
import com.example.hangeunmarket.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //Firebase database reference
    private lateinit var database: DatabaseReference

    //Firebase Authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        //Firebase init
        database = Firebase.database.reference
        auth = Firebase.auth

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //현재 사용자의 정보 업데이트
        getUserInfo() //sharedPreference에서 정보 얻어오기

        //signOut
        binding.tvLogOut.setOnClickListener {
            auth.signOut() //로그아웃
            val intent = Intent(this@MyPageFragment.activity,LoginActivity::class.java)
            activity?.finish() //현재 엑티비티 종료
            startActivity(intent) //시작화면으로 돌아가기
        }
    }



    //sharedPreference로부터 사용자 정보 얻어오기
    private fun getUserInfo(){
        val sharedPref = activity?.getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
        sharedPref?.apply {
            binding.tvMyEmail.text = getString("userEmail","이메일 정보 없음")
            binding.tvMyName.text = getString("userName","이름 정보 없음")
            binding.tvMySchool.text = getString("userSchool","학교 정보 없음")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}