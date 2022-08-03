package com.sharkbyteslab.kuiper.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.sharkbyteslab.kuiper.MainActivity
import com.sharkbyteslab.kuiper.Models.UserModel
import com.sharkbyteslab.kuiper.R
import com.sharkbyteslab.kuiper.databinding.ActivityProfileBinding
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var storage : FirebaseStorage
    private lateinit var selectedImg : Uri
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val builder = AlertDialog.Builder(this)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        Log.d("User Ok", auth.uid.toString())

        binding.userImage.setOnClickListener{

            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type="image/*"
            startActivityForResult(intent, 1)


            binding.continueBtn.setOnClickListener{


                Log.e("clicked", "Continue");

                if(binding.profileUsername.text!!.isEmpty())
                {
                    Toast.makeText(this, "Enter Username",Toast.LENGTH_SHORT).show()
                }
                else if(selectedImg.equals(Uri.EMPTY))
                {
                    Toast.makeText(this, "Please upload profile Image.",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    builder.setMessage("Almost Done")
                    builder.setTitle("Updating Profile")
                    builder.setCancelable(false)

                    dialog = builder.create()
                    dialog.show()
                    uploadData();

                }

            }

        }

    }

    private fun uploadData()
    {

        val reference = storage.reference.child("Profile").child(Date().time.toString())
        reference.putFile(selectedImg).addOnCompleteListener{
            if(it.isSuccessful)
            {
                reference.downloadUrl.addOnSuccessListener { task->
                    uploadInfo(task.toString())
                }
            }
            else
            {
                dialog.dismiss()
                Toast.makeText(this, "Error : "+it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadInfo(imgUrl : String)
    {

        val user = UserModel(auth.uid.toString(), binding.profileUsername.text.toString(), auth.currentUser!!.phoneNumber.toString(), imgUrl)
        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile Updated Successfully.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data!=null)
        {
            if(data.data!=null)
            {
                selectedImg=data.data!!
                binding.userImage.setImageURI(selectedImg)
            }
        }

    }

}