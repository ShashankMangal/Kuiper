package com.sharkbyteslab.kuiper.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sharkbyteslab.kuiper.Models.MessageModel
import com.sharkbyteslab.kuiper.databinding.ActivityChatBinding
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatBinding
    private lateinit var database : FirebaseDatabase
    private lateinit var senderUid : String
    private lateinit var receiverUid : String
    private lateinit var senderRoom : String
    private lateinit var receiverRoom : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!

        senderRoom = senderUid+receiverUid
        receiverRoom = receiverUid+senderUid

        database = FirebaseDatabase.getInstance()

        binding.chatSendbtn.setOnClickListener{

            if(binding.chatMessageBox.text.isEmpty())
            {

            }
            else
            {
                val message = MessageModel(binding.chatMessageBox.text.toString(), senderUid, Date().time)
                val randomKey = database.reference.push().key

                database.reference.child("chats")
                    .child(senderRoom).child("message").child(randomKey!!).setValue(message)
                    .addOnSuccessListener {
                        database.reference.child("chats").child(receiverRoom).child("message").child(randomKey).setValue(message)
                            .addOnSuccessListener {

                                binding.chatMessageBox.text = null
                                Toast.makeText(this, "Message Sent!",Toast.LENGTH_SHORT).show()

                            }
                    }
            }

        }

    }
}