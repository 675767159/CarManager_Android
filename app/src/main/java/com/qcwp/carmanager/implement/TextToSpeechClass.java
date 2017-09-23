package com.qcwp.carmanager.implement;


import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.qcwp.carmanager.utils.Print;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by qyh on 2017/9/18.
 *
 * @email:675767159@qq.com
 */

public class TextToSpeechClass implements android.speech.tts.TextToSpeech.OnInitListener {

    private final TextToSpeech mTextToSpeech;//TTS对象
    private final ConcurrentLinkedQueue<String> mBufferedMessages;//消息队列
    private Context mContext;
    private boolean mIsReady;//标识符

    public TextToSpeechClass(Context context){
        this.mContext=context;//获取上下文
        this.mBufferedMessages=new ConcurrentLinkedQueue<String>();//实例化队列
        this.mTextToSpeech=new TextToSpeech(this.mContext,this);//实例化TTS
    }

    //初始化TTS引擎
    @Override
    public void onInit(int status) {
        Print.d("TextToSpeechDemo",String.valueOf(status));
        if(status== TextToSpeech.SUCCESS){
            int result = this.mTextToSpeech.setLanguage(Locale.CHINA);//设置识别语音为中文
            synchronized (this){
                this.mIsReady=true;//设置标识符为true
                for(String bufferedMessage : this.mBufferedMessages){
                    speakText(bufferedMessage);//读语音
                }
                this.mBufferedMessages.clear();//读完后清空队列
            }
        }
    }
    //释放资源
    public void release(){
        synchronized (this){
            this.mTextToSpeech.shutdown();
            this.mIsReady=false;
        }
    }

    //更新消息队列，或者读语音
    public void notifyNewMessage(String lanaugh){
        String message=lanaugh;
        synchronized (this){
            if(this.mIsReady){
                speakText(message);
            }else{
                this.mBufferedMessages.add(message);
            }
        }
    }

    //读语音处理
    private void speakText(String message){
        Print.d("liyuanjinglyj",message);
        HashMap<String,String> params=new HashMap<String,String >();
        params.put(TextToSpeech.Engine.KEY_PARAM_STREAM,"STREAM_NOTIFICATION");//设置播放类型（音频流类型）
        this.mTextToSpeech.speak(message, TextToSpeech.QUEUE_ADD, params);//将这个发音任务添加当前任务之后
        this.mTextToSpeech.playSilence(100, TextToSpeech.QUEUE_ADD,params);//间隔多长时间
    }
}
