package cosidasu.sookpoiler;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.github.zagum.expandicon.ExpandIconView;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.shrikanthravi.chatview.data.Message;
import com.shrikanthravi.chatview.widget.ChatView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatBot extends AppCompatActivity {

    HorizontalScrollView moreHSV;
    ExpandIconView expandIconView;
    MaterialRippleLayout galleryMRL;
    int imagePickerRequestCode=10;
    int SELECT_VIDEO=11;
    int CAMERA_REQUEST=12;
    ChatView chatView;
    ImageView sendIcon;
    EditText messageET;
    boolean switchbool=true;
    boolean more = false;
    List<Uri> mSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        getSupportActionBar().setElevation(200);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x7f040027));
        getSupportActionBar().setLogo(R.drawable.sookpoiler);

        final ConversationService myConversationService =
                new ConversationService(
                        "2018-12-13",
                        getString(R.string.username),
                        getString(R.string.password)
                );

        chatView = findViewById(R.id.chatView);
        messageET = findViewById(R.id.messageET1);
        messageET.requestFocus();

        //Initialization start
        moreHSV = findViewById(R.id.moreLL1);
        expandIconView = findViewById(R.id.expandIconView1);
        expandIconView.setState(1,false);
        galleryMRL = findViewById(R.id.galleryMRL1);
        mSelected  = new ArrayList<>();

        MessageRequest request = new MessageRequest.Builder()
                .inputText("")
                .build();

        myConversationService
                .message(getString(R.string.workspace), request)
                .enqueue(new ServiceCallback<MessageResponse>() {
                    @Override
                    public void onResponse(MessageResponse response) {
                        final String outputText = response.getText().get(0);
                        System.out.print(outputText);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message message1 = new Message();
                                message1.setBody(outputText);
                                message1.setType(Message.LeftSimpleMessage);
                                message1.setTime(getTime());
                                message1.setUserName("눈송이");
                                message1.setUserIcon(Uri.parse("android.resource://cosidasu.sookpoiler/drawable/it"));
                                chatView.addMessage(message1);
                            }
                        });

                        if(response.getIntents().get(0).getIntent()
                                .endsWith("RequestQuote")) {
                            // More code here
                        }
                    }
                    @Override
                    public void onFailure(Exception e) {}
                });

        //Send button click listener
        chatView.setOnClickSendButtonListener(new ChatView.OnClickSendButtonListener() {
            @Override
            public void onSendButtonClick(String body) {

                Message message = new Message();
                message.setBody(body);
                message.setType(Message.RightSimpleMessage);
                message.setTime(getTime());
                message.setUserName("나");
                message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                chatView.addMessage(message);

                messageET.setText("");
                MessageRequest request = new MessageRequest.Builder()
                        .inputText(body)
                        .build();

                myConversationService
                        .message(getString(R.string.workspace), request)
                        .enqueue(new ServiceCallback<MessageResponse>() {
                            @Override
                            public void onResponse(MessageResponse response) {
                                final String outputText = response.getText().get(0);
                                System.out.print(outputText);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Message message1 = new Message();
                                        message1.setBody(outputText);
                                        message1.setType(Message.LeftSimpleMessage);
                                        message1.setTime(getTime());
                                        message1.setUserName("눈송이");
                                        message1.setUserIcon(Uri.parse("android.resource://cosidasu.sookpoiler/drawable/it"));
                                        chatView.addMessage(message1);
                                    }
                                });

                                if(response.getIntents().get(0).getIntent()
                                        .endsWith("RequestQuote")) {
                                    // More code here
                                }
                            }
                            @Override
                            public void onFailure(Exception e) {}
                        });
            }
        });
    }

    public String getTime(){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        String time = mdformat.format(calendar.getTime());
        return time;
    }



    public static String getRandomText() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(30);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    /*

    galleryMRL.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Matisse.from(ChatActivity.this)
                    .choose(MimeType.allOf())
                    .countable(true)
                    .maxSelectable(9)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new PicassoEngine())
                    .forResult(imagePickerRequestCode);
        }
    });*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Image Selection result
        if (requestCode == imagePickerRequestCode && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);

            if(switchbool) {
                if (mSelected.size() == 1) {
                    Message message = new Message();
                    message.setBody(messageET.getText().toString().trim());
                    message.setType(Message.RightSingleImage);
                    message.setTime(getTime());
                    message.setUserName("Groot");
                    message.setImageList(mSelected);
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                    chatView.addMessage(message);
                    switchbool=false;
                } else {

                    Message message = new Message();
                    message.setBody(messageET.getText().toString().trim());
                    message.setType(Message.RightMultipleImages);
                    message.setTime(getTime());
                    message.setUserName("Groot");
                    message.setImageList(mSelected);
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                    chatView.addMessage(message);
                    switchbool=false;
                }
            }
            else{

                if (mSelected.size() == 1) {
                    Message message = new Message();
                    message.setBody(messageET.getText().toString().trim());
                    message.setType(Message.LeftSingleImage);
                    message.setTime(getTime());
                    message.setUserName("Hodor");
                    message.setImageList(mSelected);
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                    chatView.addMessage(message);
                    switchbool=true;
                } else {

                    Message message = new Message();
                    message.setBody(messageET.getText().toString().trim());
                    message.setType(Message.LeftMultipleImages);
                    message.setTime(getTime());
                    message.setUserName("Hodor");
                    message.setImageList(mSelected);
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                    chatView.addMessage(message);
                    switchbool=true;
                }

            }
        }
        else {

            //Video Selection result
            if (requestCode == SELECT_VIDEO && resultCode == RESULT_OK) {

                if (switchbool) {
                    Message message = new Message();
                    message.setType(Message.RightVideo);
                    message.setTime(getTime());
                    message.setUserName("Groot");
                    message.setVideoUri(Uri.parse(getPath(data.getData())));
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                    chatView.addMessage(message);
                    switchbool = false;
                } else {
                    Message message = new Message();

                    message.setType(Message.LeftVideo);
                    message.setTime(getTime());
                    message.setUserName("Hodor");
                    message.setVideoUri(Uri.parse(getPath(data.getData())));
                    message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                    chatView.addMessage(message);
                    switchbool = true;
                }
            }
            else{

                //Image Capture result
                if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                    if (switchbool) {
                        Message message = new Message();
                        message.setType(Message.RightSingleImage);
                        message.setTime(getTime());
                        message.setUserName("Groot");
                        mSelected.clear();
                        File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                        //Uri of camera image
                        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                        mSelected.add(uri);
                        message.setImageList(mSelected);
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/groot"));
                        chatView.addMessage(message);
                        switchbool = false;
                    } else {
                        Message message = new Message();

                        message.setType(Message.LeftSingleImage);
                        message.setTime(getTime());
                        message.setUserName("Hodor");
                        mSelected.clear();
                        File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                        //Uri of camera image
                        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                        mSelected.add(uri);
                        message.setImageList(mSelected);
                        message.setUserIcon(Uri.parse("android.resource://com.shrikanthravi.chatviewlibrary/drawable/hodor"));
                        chatView.addMessage(message);
                        switchbool = true;
                    }
                }
            }
        }
    }


    public String getPath(Uri uri) {
        System.out.println("getpath "+uri.toString());
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }
}
