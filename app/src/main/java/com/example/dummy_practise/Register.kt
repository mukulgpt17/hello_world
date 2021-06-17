package com.example.dummy_practise

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Register.newInstance] factory method to
 * create an instance of this fragment.
 */
class Register : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_email.error=null
        text_pass.error=null
        save_regis.setOnClickListener {
            val email_val=tex_em.text.toString()
            val pass_val=tex_ps.text.toString()

            val ck=checkValid(email_val,pass_val)

            if (ck)
            {
                auth.createUserWithEmailAndPassword(email_val,pass_val).addOnCompleteListener(requireActivity()) {task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        findNavController().navigate(RegisterDirections.actionRegisterToAfterLogin())
                    } else {
                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }

            }


        }

    }

    private fun checkValid(email:String,pass_code:String):Boolean
    {
        if (email.isBlank())
        {
            text_email.error="Email is Blank"
            return false
        }

        if (pass_code.isBlank()) {
            text_pass.error = "PassCode is Blank"
            return false
        }
        return true;
    }



}