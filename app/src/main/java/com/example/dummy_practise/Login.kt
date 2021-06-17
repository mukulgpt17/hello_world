package com.example.dummy_practise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 */

class Login : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth= FirebaseAuth.getInstance()
        if  (auth.currentUser!=null)
        {
            findNavController().navigate(LoginDirections.actionLoginToAfterLogin())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()

        val current_user=auth.currentUser
        if (current_user !=null)
        {
            findNavController().navigate(LoginDirections.actionLoginToAfterLogin())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login.setOnClickListener{

            emal_id_login.error=null
            pass_code_login.error=null

            val email_val=email_login.text.toString()
            val pass_val=pass_login.text.toString()

            val corr=isValid(email_val,pass_val);

            if (corr)
            {
                auth.signInWithEmailAndPassword(email_val, pass_val)
                    .addOnCompleteListener(requireActivity())
                {task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                       findNavController().navigate(LoginDirections.actionLoginToRegister())
                    } else {
                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
                    }

                }
            }

        }

        register_login.setOnClickListener {
            findNavController().navigate(LoginDirections.actionLoginToRegister())

        }


    }

    private  fun isValid(email:String,pass:String):Boolean
    {
        if (email.isBlank())
        {
            emal_id_login.error="Email id is Empty";
            return false
        }
        if (pass.isBlank())
        {
            pass_code_login.error="PassCode is Empty"
            return false
        }

        return true
    }


}